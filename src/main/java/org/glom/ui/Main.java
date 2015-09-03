package org.glom.ui;

import org.glom.libglom.Document;
import org.glom.libglom.Logger;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.io.InputStream;

/**
 * Created by murrayc on 9/2/15.
 */
public class Main {
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Application.getInstance().showLogin();
            }
        });
    }
}
