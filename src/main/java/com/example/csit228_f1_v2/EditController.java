package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditController {
    public Button btnSubmit;
    public TextArea newTaskText;
    public TextField newTaskDate;
    public static Scene prev;
    public void onSubmitClicked(ActionEvent event) {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE todolist SET text = ?, date = ? WHERE listid = ?"
             )) {
            String text = newTaskText.getText();
            String date = newTaskDate.getText();
            int tid = currUser.tid;
            statement.setString(1, text);
            statement.setString(2, date);
            statement.setInt(3, tid);
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
