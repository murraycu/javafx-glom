package org.glom.ui;

import org.glom.libglom.Document;
import org.glom.libglom.layout.LayoutGroup;
import org.glom.libglom.layout.LayoutItem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by murrayc on 9/2/15.
 */
public class TableModel extends AbstractTableModel {

    private final Document document;
    private final String tableName;
    private final List<LayoutItem> layoutItems;

    public TableModel(final Document document, final String tableName) {
        this.document = document;
        this.tableName = tableName;
        this.layoutItems = buildLayoutItems();
    }

    private List<LayoutItem> buildLayoutItems() {
        final List<LayoutItem> items = new ArrayList<>();
        final List<LayoutGroup> listGroups = document.getDataLayoutGroups("list", this.tableName);
        for(final LayoutGroup group : listGroups) {
            for(final LayoutItem item : group.getItems()) {
                items.add(item);
            }
        }

        return items;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return layoutItems.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    @Override
    public String getColumnName(int columnIndex) {
        final LayoutItem item = layoutItems.get(columnIndex);
        if (item == null) {
            return null;
        }

        return item.getTitle("");
    }
}
