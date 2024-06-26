package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RegistrarController {
    public Button btnSubmit;
    public GridPane pnReg;
    public TextField tfName;
    public TextField tfEmail;
    public PasswordField pfPass;
    public static Scene prev;
    public void onSubmitClicked(ActionEvent event) {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO new_users (name, email, password) values (?,?,?)"
             )) {
            String name = tfName.getText();
            String email = tfEmail.getText();
            String pass = pfPass.getText();
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, pass);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(prev);
        stage.show();
    }
}
