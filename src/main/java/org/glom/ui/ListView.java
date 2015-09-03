package org.glom.ui;

import org.glom.libglom.Document;
import org.glom.libglom.Logger;

import javax.swing.*;
import java.io.InputStream;

/**
 * Created by murrayc on 9/1/15.
 */
public class ListView extends JFrame {

    ListView(final Document document) {
        super();

        //Set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final String title = "Glom: " + document.getDatabaseTitle("") + ": List";
        setTitle(title);

        final String tableName = document.getDefaultTable();
        //final String tableTitle = document.getTableTitle(tableName, "");

        final TableModel model = new TableModel(document, tableName);
        final JTable table = new JTable(model);

        final JScrollPane scrollpane = new JScrollPane(table);
        getContentPane().add(scrollpane);

        //Display the window.
        pack();
    }

}
