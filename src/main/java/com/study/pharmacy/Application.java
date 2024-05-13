package com.study.pharmacy;

import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        WindowController.currentWindow = stage;
        WindowController.openWindow(stage, "enter.fxml", "| Вход в систему");
    }

    public static void main(String[] args) {
        launch();
    }
}