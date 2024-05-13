package com.study.pharmacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EnterController {
    @FXML
    private TextField tf;
    @FXML
    private PasswordField pf;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;

    @FXML
    private void login(ActionEvent event) throws SQLException {
        UserService.login(tf.getText(), pf.getText());
    }

    @FXML
    private void openRegistWindow(ActionEvent event) throws SQLException, IOException {
        WindowController.openWindow(new Stage(), "employee-regist.fxml", "| Регистрация сотрудника");
    }
}