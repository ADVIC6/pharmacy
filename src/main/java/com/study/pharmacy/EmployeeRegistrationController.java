package com.study.pharmacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class EmployeeRegistrationController {
    @FXML
    private Button btn1;
    @FXML
    private void backWindow(ActionEvent event) throws IOException {
        WindowController.backWindow();
    }
}
