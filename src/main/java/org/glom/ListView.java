package org.glom;

import org.glom.libglom.Document;
import org.glom.libglom.Logger;
import org.glom.libglom.layout.LayoutGroup;
import org.glom.libglom.layout.LayoutItem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by murrayc on 9/1/15.
 */
public class ListView {

    private static class TableModel extends AbstractTableModel {

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
            System.out.println("in");
            final LayoutItem item = layoutItems.get(columnIndex);
            if (item == null) {
                return null;
            }

            return item.getTitle("");
        }
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        final InputStream inputStream = ListView.class.getClassLoader().getResourceAsStream("example_music_collection.glom");

        final Document document = new Document();
        final boolean retval = document.load(inputStream);
        if (!retval) {
            Logger.log("Document.load() failed.");
            return;
        }

        final String title = "Glom: " + document.getDatabaseTitle("") + ": List";
        frame.setTitle(title);

        final String tableName = document.getDefaultTable();
        //final String tableTitle = document.getTableTitle(tableName, "");

        final TableModel model = new TableModel(document, tableName);
        final JTable table = new JTable(model);

        final JScrollPane scrollpane = new JScrollPane(table);
        frame.getContentPane().add(scrollpane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
