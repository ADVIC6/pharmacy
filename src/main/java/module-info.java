module com.study.pharmacy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.study.pharmacy to javafx.fxml;
    exports com.study.pharmacy;
}