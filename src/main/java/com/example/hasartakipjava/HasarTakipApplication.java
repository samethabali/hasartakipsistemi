package com.example.hasartakipjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HasarTakipApplication extends Application {
    //main metodunu da içinde barındıran sınıf
    //Application sınıfından extend edilir.
    @Override
    public void start(Stage stage) throws IOException {
        //start metodu ile gerekli scene ve stage'ler başlatılır.
        FXMLLoader fxmlLoader = new FXMLLoader(com.example.hasartakipjava.HasarTakipApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        //Tüm ekranlarımızda aynı stili korumak ve tekrarı azaltmak adına CSS yöntemini kullandık.
        String css = getClass().getResource("/com/example/hasartakipjava/styles/style.css").toExternalForm();
        stage.setTitle("Hasar Takip Sistemi");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}