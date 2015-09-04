package org.glom.ui;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.glom.Credentials;
import org.glom.SqlUtils;
import org.glom.libglom.Document;
import org.glom.libglom.Logger;
import org.glom.libglom.layout.LayoutGroup;
import org.glom.libglom.layout.LayoutItem;
import org.glom.libglom.layout.LayoutItemField;
import org.jooq.SQLDialect;

import javax.swing.table.AbstractTableModel;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by murrayc on 9/2/15.
 */
public class TableModel extends AbstractTableModel {

    private final Document document;
    private final String tableName;
    private final List<LayoutItem> layoutItems;
    private final ComboPooledDataSource dataSource;
    private List<LayoutItemField> fieldsToGet;
    private ResultSet rs;
    private final int rowCount;

    public TableModel(final Document document, final ComboPooledDataSource dataSource, final String tableName) {
        this.document = document;
        this.dataSource = dataSource;
        this.tableName = tableName;
        this.layoutItems = buildLayoutItems();

        final String queryCount = SqlUtils.buildSqlCountSelectWithWhereClause(tableName,
                fieldsToGet, document.getSqlDialect());
        this.rowCount = getResultSizeOfSQLQuery(queryCount, dataSource);

        final String query =  SqlUtils.buildSqlSelectWithWhereClause(tableName,
                fieldsToGet, null, null, document.getSqlDialect());

        this.rs = null;
        try {
            //Change the timeout, because it otherwise takes ages to fail sometimes when the details are not setup.
            //This is more than enough.
            DriverManager.setLoginTimeout(5);

            // Setup and execute the count query. Special care needs to be take to ensure that the results will be based
            // on a cursor so that large amounts of memory are not consumed when the query retrieve a large amount of
            // data. Here's the relevant PostgreSQL documentation:
            // http://jdbc.postgresql.org/documentation/83/query.html#query-with-cursor

            // TODO Test execution time of this query with when the number of rows in the table is large (say >
            // 1,000,000). Test memory usage at the same time (see the todo item in getTableData()).
            this.rs = SqlUtils.executeQuery(dataSource, query);
            rs.first();
        } catch (final SQLException e) {
            Logger.log("Error calculating number of rows in the query.", e);
        }
    }

    private List<LayoutItem> buildLayoutItems() {
        final List<LayoutItem> items = new ArrayList<>();
        fieldsToGet = new ArrayList<>();

        final List<LayoutGroup> listGroups = document.getDataLayoutGroups("list", this.tableName);
        for(final LayoutGroup group : listGroups) {
            for(final LayoutItem item : group.getItems()) {
                items.add(item);

                if(item instanceof LayoutItemField) {
                    fieldsToGet.add((LayoutItemField)item);
                }
            }
        }

        return items;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return fieldsToGet.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rs == null) {
            return null;
        }

        try {
            //ResultSet rows are 1-indexed.
            //0 means before the first row.
            if(!rs.absolute(rowIndex + 1)) {
                return null;
            }

            return rs.getString(columnIndex + 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        final LayoutItem item = fieldsToGet.get(columnIndex);
        if (item == null) {
            return null;
        }

        return item.getTitle("");
    }

    private int getResultSizeOfSQLQuery(final String query, final ComboPooledDataSource cpds) {

        ResultSet rs = null;
        try {
            //Change the timeout, because it otherwise takes ages to fail sometimes when the details are not setup.
            //This is more than enough.
            DriverManager.setLoginTimeout(5);

            // Setup and execute the count query. Special care needs to be take to ensure that the results will be based
            // on a cursor so that large amounts of memory are not consumed when the query retrieve a large amount of
            // data. Here's the relevant PostgreSQL documentation:
            // http://jdbc.postgresql.org/documentation/83/query.html#query-with-cursor

            // TODO Test execution time of this query with when the number of rows in the table is large (say >
            // 1,000,000). Test memory usage at the same time (see the todo item in getTableData()).
            rs = SqlUtils.executeQuery(cpds, query);

            // get the number of rows in the query
            rs.next();
            return rs.getInt(1);

        } catch (final SQLException e) {
            Logger.log("Error calculating number of rows in the query.", e);
            return -1;
        } finally {
            // cleanup everything that has been used
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (final Exception e) {
                Logger.log("Error closing database resources. Subsequent database queries may not work.", e);
            }
        }
    }
}
