package org.glom;

import org.glom.libglom.Document;
import org.glom.libglom.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.InputStream;

/**
 * Created by murrayc on 9/1/15.
 */
public class ListView {

    private static class TableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getColumnCount() {
            return 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
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

        final TableModel model = new TableModel();
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
