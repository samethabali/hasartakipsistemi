package com.example.hasartakipjava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static com.example.hasartakipjava.HelloController.ref;

public class LoginController implements PlakaKontrol{
    public TextField tcgiris;
    public TextField plakagiris;
    public Label hatalabel;

    public LoginController() {}

    private static final Map<String, String> aracBilgileriMap = new HashMap<>();

    //Giriş Yap butonuna basıldığında kullanıcıdan alınan verilerin formatları kontrol edilerek aracBilgileriMap içerisine yerleştirilir.
    //Formaatların uymadığı durumlarda ekrana hata yazısı verilir.
    //Kullanıcının hasar detaylarını ekleyebileceği ekrana geçiş yapılır.
    public void onLoginButtonClick() throws IOException {
        String tc = tcgiris.getText();
        String plaka = plakagiris.getText();

        //tcgiris ve plakagiris entry'leri boş ise
        if(tcgiris.getText().matches("") || plakagiris.getText().matches("")) {
            hatalabel.setText("Gerekli Alanları Doldurun!");
        //tc nümerik değil veya 11 haneden farklı ise
        }else if(!isNumeric(tc) || tc.length()!=11) {
            hatalabel.setText("Eksik veya yanlış bir TC numarası girdiniz!");
        //plaka formatına uygun değil ise
        }else if(!plakaDogruMu(plaka)) {
            hatalabel.setText("Plaka '34 XXX 123' formatında girilmeli!");
        //yukarıdaki koşullara uymadığı durumlarda else ile Hasar Ekle sayfasına geçiş
        }else {
            hatalabel.setText("Başarılı Giriş");
            aracBilgileriMap.put(tc, plaka);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(ref));
            Parent root = loader.load();

            Stage firstStage = new Stage();

            firstStage.setTitle("Hasar Ekle");
            firstStage.setScene(new Scene(root));
            String css = getClass().getResource("/com/example/hasartakipjava/styles/style.css").toExternalForm();
            firstStage.show();
            Stage currentStage = (Stage) tcgiris.getScene().getWindow();
            currentStage.close();
        }
    }

    //Kullanıcı Geri butonuna bastığında ana sayfaya döner.
    @FXML
    protected void onBackButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Stage firstStage = new Stage();
        firstStage.setTitle("Hasar Takip Sistemi");
        firstStage.setScene(new Scene(root));
        String css = getClass().getResource("/com/example/hasartakipjava/styles/style.css").toExternalForm();
        firstStage.show();
        Stage currentStage = (Stage) tcgiris.getScene().getWindow();
        currentStage.close();
    }

    //tc stringinin nümerik olup olmadığının kontrolü yapılır.
    private boolean isNumeric(String tc) {
        try{
            Double.parseDouble(tc);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }

    //Kullanının girdiği plaka, Türkiye plaka formatlarına uygun olup olmadığının kontrolü yapılır.
    //Interface sınıfında tanımlanan metot ile sağlanır.
    public boolean plakaDogruMu(String plaka) {
        String[] parcalar = plaka.split(" ");
        if(parcalar.length<2 || parcalar.length>3) {
            return false;
        }

        try{
            int sehirKodu = Integer.parseInt(parcalar[0]);
            if(sehirKodu<1 || sehirKodu>81) {
                return false;
            }
        }catch(NumberFormatException e) {
            return false;
        }

        String harfGrubu = parcalar[1];
        if(harfGrubu.isEmpty() || harfGrubu.length()>3) {
            return false;
        }
        for(char c:harfGrubu.toCharArray()) {
            if(!Character.isUpperCase(c)) {
                return false;
            }
        }

        String sayiGrubu = parcalar.length==3 ? parcalar[2] : "";
        if(!sayiGrubu.isEmpty()) {
            if(sayiGrubu.length()<2 || sayiGrubu.length()>4) {
                return false;
            }
            for(char c:sayiGrubu.toCharArray()) {
                if(!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Map<String, String> getAracBilgileriMap() {
        return aracBilgileriMap;
    }
}