package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RealHomeController {
    public Button btnAdd;
    public Button btnLogout;
    public Button btnUpdate;
    public Button btnDelete;
    public static Scene prev;
    public TextArea taTask;
    public TextField tfDate;
    public VBox vbDisplay;

    public void onStart() {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM todolist WHERE uid = ?"
             )) {
            statement.setInt(1, currUser.uid);
            ResultSet res = statement.executeQuery();
            int i = 1;
            vbDisplay.getChildren().clear();
            while (res.next()) {
                String text = res.getString("text");
                String date = res.getString("date");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("task-card.fxml"));
                GridPane taskCard = fxmlLoader.load();

                TaskCardController taskCardController = fxmlLoader.getController();

                taskCardController.setTaskCardValues(String.valueOf(i), text, date);
                vbDisplay.getChildren().add(taskCard);
                System.out.println("here");
                i++;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onAddClicked() {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO todolist (uid, text, date) values (?,?,?)"
             )) {
            int uid = currUser.uid;
            String text = taTask.getText();
            String date = tfDate.getText();
            statement.setInt(1, uid);
            statement.setString(2, text);
            statement.setString(3, date);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
            onStart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLogoutClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(prev);
        stage.show();
    }

    public void onUpdateClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent p = null;
        try {
            p = FXMLLoader.load(getClass().getResource("update.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene s = new Scene(p);
        UpdateController.prev = ((Node) event.getSource()).getScene();
        stage.setScene(s);
        stage.show();
    }

    public void onDeleteClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(prev);
        stage.show();
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM new_users WHERE userid = ?"
             )) {
            statement.setInt(1, currUser.uid);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM todolist WHERE uid = ?"
             )) {
            statement.setInt(1, currUser.uid);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
