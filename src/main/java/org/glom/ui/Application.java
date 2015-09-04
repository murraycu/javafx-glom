package org.glom.ui;

import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.glom.Credentials;
import org.glom.libglom.Document;
import org.glom.libglom.Logger;

import javax.swing.*;
import java.io.InputStream;
/**
 * Created by murrayc on 9/3/15.
 */
public class Application extends javafx.application.Application {
    private static Application ourInstance;

    private Document document;
    private Credentials credentials;

    public static Application getInstance() {
        return ourInstance;
    }

    public Application() {
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        ourInstance = this;
        showLogin(primaryStage);
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

    public void showLogin(final Stage primaryStage) {
        final InputStream inputStream = Application.class.getClassLoader().getResourceAsStream("example_music_collection.glom");

        document = new Document();
        final boolean retval = document.load(inputStream);
        if (!retval) {
            Logger.log("Document.load() failed.");
            return;
        }

        //Show the login dialog until it succeeds:
        final Application app = Application.getInstance();
        final LoginDialog loginDialog = new LoginDialog(document);
        loginDialog.show();
        loginDialog.resultProperty().addListener(observable -> {
            if (app.getCredentials() == null) {
                //Ask again:
                loginDialog.show();
            } else {
                //Show the data now that we know we have a connection:
                showListView(primaryStage);
            }
        });
    }

    private void showListView(final Stage primaryStage) {
        final String title = "Glom: " + document.getDatabaseTitle("") + ": List";
        primaryStage.setTitle("Glom");

        final Scene scene = new ListView(document, getCredentials().getConnection());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
