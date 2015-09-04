package org.glom.ui;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javafx.embed.swing.SwingNode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import org.glom.libglom.Document;

import javax.swing.*;

/**
 * Created by murrayc on 9/1/15.
 */
public class ListView extends Scene {
    private StackPane root;

    ListView(final Document document, final ComboPooledDataSource dataSource) {
        super(new StackPane());
        root = (StackPane)getRoot();

        final SwingNode swingNode = new SwingNode();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Set up the window.
                final String tableName = document.getDefaultTable();
                //final String tableTitle = document.getTableTitle(tableName, "");

                final TableModel model = new TableModel(document, dataSource, tableName);
                final JTable table = new JTable(model);

                //final JScrollPane scrollpane = new JScrollPane(table);
                //getContentPane().add(scrollpane);

                //Display the window.
                //pack();
                swingNode.setContent(table);
            }
        });

        root.getChildren().add(swingNode);
    }
}
