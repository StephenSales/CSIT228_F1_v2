package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RealHomeController {
    public Button btnAdd;

    public Label lblEmpty;

    public void onAddClicked() {
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {
            String selectQuery = "SELECT * FROM todolist WHERE uid = ";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            if (resultSet) {
                lblEmpty.setText("");
            }
            while(resultSet.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
