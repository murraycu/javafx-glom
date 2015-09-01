package org.glom;

import org.glom.libglom.Document;
import org.glom.libglom.Logger;

import javax.swing.*;
import java.io.InputStream;

/**
 * Created by murrayc on 9/1/15.
 */
public class ListView {

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Glom: List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        final InputStream inputStream = ListView.class.getClassLoader().getResourceAsStream("example_music_collection.glom");

        final Document document = new Document();
        final boolean retval = document.load(inputStream);
        if (!retval) {
            Logger.log("Document.load() failed.");
            return;
        }

        JLabel label = new JLabel(document.getDatabaseTitle(""));
        frame.getContentPane().add(label);

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
