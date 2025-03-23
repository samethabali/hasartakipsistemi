module com.example.hasartakipjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hasartakipjava to javafx.fxml;
    exports com.example.hasartakipjava;
}