package org.glom.ui;

import org.glom.Credentials;
import org.glom.libglom.Document;
import org.glom.libglom.Logger;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;

/**
 * Created by murrayc on 9/3/15.
 */
public class Application {
    private static final Application ourInstance = new Application();

    private Document document;
    private Credentials credentials;

    public static Application getInstance() {
        return ourInstance;
    }

    private Application() {
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void showLogin() {
        final InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("example_music_collection.glom");

        final Document document = new Document();
        final boolean retval = document.load(inputStream);
        if (!retval) {
            Logger.log("Document.load() failed.");
            return;
        }

        //Show the login dialog until it succeeds:
        //TODO: Let the user cancel.
        final Application app = Application.getInstance();
        final LoginDialog loginDialog = new LoginDialog(new JFrame(), document);
        loginDialog.setVisible(true);
        loginDialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                if (app.getCredentials() == null) {
                    //Ask again:
                    loginDialog.setVisible(true);
                } else {
                    loginDialog.dispose();

                    //Show the data now that we know we have a connection:
                    showListView();
                }
            }
        });
    }

    private void showListView() {
        final ListView listView = new ListView(getDocument(), getCredentials().getConnection());
        listView.setVisible(true);
    }
}
