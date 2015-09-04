package org.glom.ui;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.glom.Credentials;
import org.glom.SqlUtils;
import org.glom.libglom.Document;

import java.sql.SQLException;

/**
 * Created by murrayc on 9/2/15.
 */
public class LoginDialog extends Dialog<Pair<String, String>> {
    private final Document document;
    private final TextField textFieldUsername;
    private final TextField textFieldPassword;

    LoginDialog(final Document document) {
        super();
        this.document = document;

        setTitle("Connect to Server");
        setHeaderText("Please enter the connection details for your database server.");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10); //TODO: Based on what HIG?
        grid.setPadding(new Insets(20, 150, 10, 10)); //TODO: Based on what HIG?


        Label label = new Label("Username:");
        grid.add(label, 0, 0);

        textFieldUsername = new TextField();
        grid.add(textFieldUsername, 1, 0);

        label = new Label("Password:");
        grid.add(label, 0, 1);

        textFieldPassword = new PasswordField();
        grid.add(textFieldPassword, 1, 1);

        getDialogPane().setContent(grid);


        final ButtonType loginButtonType = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        textFieldUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        loginButton.addEventFilter(ActionEvent.ACTION, ae -> {
            attemptLogin();
        });

        // Request focus on the username field by default.
        Platform.runLater(() -> textFieldUsername.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        // (though the caller doesn't use this so far.)
        setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(textFieldUsername.getText(), textFieldPassword.getText());
            }
            return null;
        });
    }

    private void attemptLogin() {
        final String username = textFieldUsername.getText();
        final String password = textFieldPassword.getText();
        ComboPooledDataSource dataSource = null;
        try {
            dataSource = SqlUtils.tryUsernameAndPassword(document, username, password);
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        Credentials credentials = null;
        Document document = null;
        if (dataSource != null) {
            credentials = new Credentials(this.document, username, password, dataSource);
            document = this.document;
        }

        final Application app = Application.getInstance();
        app.setCredentials(credentials);
        app.setDocument(document);

        //setVisible(false); //Let the user of this dialog respond.
        //return credentials != null;
    }
}
