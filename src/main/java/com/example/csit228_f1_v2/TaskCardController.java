package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskCardController {
    public Label taskID;
    public Label taskText;
    public Label taskDate;
    public int tid;
    public void setTaskCardValues(String tID, String text, String date, int tid){
        taskID.setText(tID);
        taskText.setText(text);
        taskDate.setText(date);
        this.tid = tid;
    }

    public void onTaskDeleteClicked() {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM todolist WHERE listid = ?"
             )) {
            statement.setInt(1, tid);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTaskEditClicked(ActionEvent event) {
        currUser.tid = tid;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent p = null;
        try {
            p = FXMLLoader.load(getClass().getResource("edit.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene s = new Scene(p);
        EditController.prev = ((Node) event.getSource()).getScene();
        stage.setScene(s);
        stage.show();
    }
}
