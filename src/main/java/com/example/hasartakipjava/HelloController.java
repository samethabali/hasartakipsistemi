package com.example.hasartakipjava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {
    //Hasar Ekle ve Servis İşlemleri butonları aynı Login sayfasını paylaştığı için kod tekrarını azaltmak adına ref değişkeninde fxml dosyasının adı tutulur.
    //Uygun fonksiyonlar içinde ref değişkeni ile ulaşılacak fxml dosyasının adı belirlenir.
    public static String ref = "";
    public Button hasareklebuton;
    public Button hasarsorgulabuton;

    //Kullanıcı 'Hasar Ekle' butonuna tıkladığında çalışacak olan metot.
    //Kullanıcıyı hasar-ekle-ekrani.fxml'e götürecektir.
    @FXML
    protected void onHasarEkleButtonClick() throws IOException {
        ref="hasar-ekle-ekrani.fxml";
        sayfaYukle("login.fxml","Hasar Ekle");
    }

    //Kullanıcı 'Hasar Sorgula' butonuna tıkladığında çalışacak olan metot.
    //Kullanıcıyı sorgu.fxml'e götürecektir.
    @FXML
    protected void onHasarSorgulaButtonClick() throws IOException {
        sayfaYukle("sorgu.fxml","Hasar Sorgula");
    }

    //Kullanıcı 'Servis İşlemleri' butonuna tıkladığında çalışacak olan metot.
    //Kullanıcıyı servis_islemleri.fxml'e götürecektir.
    @FXML
    protected void onServisIslemleriButtonClick() throws IOException {
        ref="servis-islemleri.fxml";
        sayfaYukle("login.fxml","Servis İşlemleri");
    }

    //Bu metot ile kod tekrarını önleme amaçlanarak ...ButtonClick metotlarının yönlendireceği .fxml sayfalarına erişim sağlanacaktır.
    private void sayfaYukle(String sayfaad, String sayfabaslik) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sayfaad));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(sayfabaslik);
        stage.setScene(new Scene(root));
        String css = getClass().getResource("/com/example/hasartakipjava/styles/style.css").toExternalForm();
        stage.show();

        Stage currentStage = (Stage) hasareklebuton.getScene().getWindow();
        currentStage.close();
    }
}