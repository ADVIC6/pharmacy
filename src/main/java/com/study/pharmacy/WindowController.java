package com.study.pharmacy;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * WindowController - сущность для межоконного взаимодействия в системе.
 */
public class WindowController {
    public static Stage pastWindow;
    public static Stage currentWindow;

    public static void openWindow(Stage stage, String pathToFxmlFile, String title) throws IOException {
        /*
         * openWindow - открывает Windows окно.
         * параметры:    stage - объект окна
         *               text - путь до файла fxml с описанием интерфейса
         *               title - заголовок окна
         * */

        // Сохраняем объект прошлого окна и закрываем его
        WindowController.pastWindow = currentWindow;
        pastWindow.close();

        // Определяем новое текущее окно
        WindowController.currentWindow = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(pathToFxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void backWindow() throws IOException {
        Stage temp = currentWindow;
        WindowController.currentWindow = pastWindow;
        WindowController.pastWindow = temp;
        pastWindow.close();
        currentWindow.show();
    }
}
