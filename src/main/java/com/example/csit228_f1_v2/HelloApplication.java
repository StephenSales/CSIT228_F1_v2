package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Text txtWelcome = new Text("Welcome to CIT");
        txtWelcome.setFont(Font.font("Chiller", FontWeight.EXTRA_BOLD, 69));
        txtWelcome.setFill(Color.RED);
//        grid.setAlignment();
        grid.setPadding(new Insets(20));
//        grid.
        txtWelcome.setTextAlignment(TextAlignment.CENTER);
        grid.add(txtWelcome, 0, 0, 3, 1);

        Label lbUsername = new Label("Username: ");
        lbUsername.setTextFill(Color.LIGHTSKYBLUE);
        lbUsername.setFont(Font.font(30));
        grid.add(lbUsername, 0, 1);

        TextField tfUsername = new TextField();
        grid.add(tfUsername, 1, 1);
        tfUsername.setFont(Font.font(30));
//        tfUsername.setMaxWidth(150);

        Label lbEmail = new Label("Email");
        lbEmail.setFont(Font.font(30));
        lbEmail.setTextFill(Color.CHARTREUSE);
        grid.add(lbEmail, 0, 3);

        TextField tfEmail = new TextField();
        grid.add(tfEmail, 1, 3);
        tfEmail.setFont(Font.font(30));

        Label lbPassword = new Label("Password");
        lbPassword.setFont(Font.font(30));
        lbPassword.setTextFill(Color.MAROON);
        grid.add(lbPassword, 0, 2);

        PasswordField pfPassword = new PasswordField();
        pfPassword.setFont(Font.font(30));
        grid.add(pfPassword, 1, 2);

        TextField tmpPassword = new TextField(pfPassword.getText());
        tmpPassword.setFont(Font.font(30));
        grid.add(tmpPassword, 1, 2);
        tmpPassword.setVisible(false);

        ToggleButton btnShow = new ToggleButton("( )");
//        btnShow.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                if (btnShow.isSelected()) {
//                    tmpPassword.setText(pfPassword.getText());
//                    tmpPassword.setVisible(true);
//                } else {
//                    tmpPassword.setVisible(false);
//                    pfPassword.setText(tmpPassword.getText());
//                }
//            }
//        });
        btnShow.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tmpPassword.setText(pfPassword.getText());
                tmpPassword.setVisible(true);
            }
        });

        EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tmpPassword.setVisible(false);
                pfPassword.setText(tmpPassword.getText());
            }
        };

        btnShow.setOnMouseReleased(release);
        btnShow.setOnMouseExited(release);
        grid.add(btnShow, 2,2);

        Button btnLogin = new Button("Log In");
        btnLogin.setFont(Font.font(40));
        grid.add(btnLogin, 0, 5, 1, 1);

        Button btnReg = new Button("Create an Account");
        btnReg.setFont(Font.font(40));
        grid.add(btnReg, 1, 5, 1, 1);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try (Connection c = MySQLConnection.getConnection(); Statement statement = c.createStatement();) {
                    String query = "SELECT * FROM new_users";
                    ResultSet res = statement.executeQuery(query);
                    while (res.next()) {
                        int id = res.getInt("id");
                        String name = res.getString("name");
                        String email = res.getString("email");
                        String pass = res.getString("password");
                        if (tfUsername.getText() != name || tfEmail.getText() != email || pfPassword.getText() != pass) {
                            System.out.println("Login unsuccessful");
                        } else {
                            System.out.println("Hello");
                            try {
                                Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                                Scene s = new Scene(p);
                                stage.setScene(s);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnReg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Hello");
                try {
                    Parent p = FXMLLoader.load(getClass().getResource("registrar.fxml"));
                    Scene s = new Scene(p);
                    stage.setScene(s);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(grid, 700, 500, Color.BLACK);
        stage.setScene(scene);
        scene.setFill(Color.CORNFLOWERBLUE);
        stage.show();
        txtWelcome.minWidth(grid.getWidth());
    }

    public static void main(String[] args) {
        launch();
    }
}