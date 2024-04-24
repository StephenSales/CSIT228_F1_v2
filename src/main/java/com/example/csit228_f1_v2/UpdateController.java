package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateController {
    public Button btnSubmit;
    public GridPane pnUpdate;
    public TextField tfName;
    public TextField tfEmail;
    public PasswordField pfPass;
    public static Scene prev;
    public void onSubmitClicked(ActionEvent event) {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE new_users SET name = ?, email = ?, password = ? WHERE userid = ?"
             )) {
            String name = tfName.getText();
            String email = tfEmail.getText();
            String pass = pfPass.getText();
            int id = currUser.uid;
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, pass);
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(prev);
        stage.show();
    }
}
