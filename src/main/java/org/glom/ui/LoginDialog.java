package org.glom.ui;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.glom.Credentials;
import org.glom.SqlUtils;
import org.glom.libglom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by murrayc on 9/2/15.
 */
public class LoginDialog extends JDialog {
    private final Document document;
    private final JTextField textFieldUsername;
    private final JTextField textFieldPassword;

    LoginDialog(final JFrame owner, final Document document) {
        super(owner, "Login");
        this.document = document;

        JPanel pane = new JPanel(new GridBagLayout());
        getContentPane().add(pane);

        JLabel label = new JLabel("Username:");
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        c.gridx = 0;
        c.gridy = 0;
        pane.add(label, c);
        label.setVisible(true);

        textFieldUsername = new JTextField();
        c.gridx = 1;
        c.gridy = 0;
        pane.add(textFieldUsername, c);

        label = new JLabel("Password:");
        c.gridx = 0;
        c.gridy = 1;
        pane.add(label, c);

        textFieldPassword = new JPasswordField();
        c.gridx = 1;
        c.gridy = 1;
        pane.add(textFieldPassword, c);


        JButton buttonConnect = new JButton("Connect");
        c.gridx = 1;
        c.gridy = 2;
        pane.add(buttonConnect, c);
        buttonConnect.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                attemptLogin();
            }
        });

        //pane.setVisible(true);
        pack();
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

        setVisible(false); //Let the user of this dialog respond.
    }
}
