package com.example.hasartakipjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class SorguController {

    public TextField plakasorgugiris;
    public Label hatalabel;
    public ListView<String> lv_kazalar;

    //KazaFile ve Listeleme sınıflarının metodlarına ulaşmak için gerekli nesneler oluşturulur.
    private Listeleme listeleme;
    private final KazaFile kazaFile;

    public SorguController() {
        this.listeleme = new Listeleme();
        this.kazaFile = new KazaFile(listeleme);
    }

    //Tüm kaza bilgileri kaza_verileri.txt dosyasından okunur.
    @FXML
    public void initialize() throws IOException {
        this.listeleme = new Listeleme();
        kazaFile.dosyadanOku();
    }

    ObservableList<String> kazaItems = FXCollections.observableArrayList();

    //Girilen plaka bilgisi için gerekli kontroller yapılır ve plakaIleGoster metoduna plaka bilgisi gönderilir.
    @FXML
    public void onSorguButtonClick () {
        kazaItems.clear();
        String plaka = plakasorgugiris.getText();

        if(plakasorgugiris.getText().matches("")) {
            hatalabel.setText("Lütfen Geçerli Bir Plaka Giriniz!");
        }else if(!plakaFormatKontrol(plaka)) {
            hatalabel.setText("Plaka '34 XXX 123' formatında girilmeli!");
        }else {
            plakaIleGoster(plaka);
        }

    }

    //Alınan plaka bilgisine göre kazaFile'dan alınan veriler ListView içerisine eklenir.
    //Olmadığı durumlarda hata mesajı verilir.
    private void plakaIleGoster(String plaka) {
        List<Kaza> kazalar = kazaFile.plakaIleSorgula(plaka);

        for(Kaza kaza:kazalar) {
            kazaItems.add(kaza.toString());
        }

        lv_kazalar.setItems(kazaItems);

        if(kazalar.isEmpty()) {
            hatalabel.setText("Bu plaka kayıtlarda bulunamadı!");
        }
    }

    @FXML
    protected void onBackButtonClick () throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        Stage firstStage = new Stage();
        firstStage.setTitle("Hasar Takip Sistemi");
        firstStage.setScene(new Scene(root));
        firstStage.show();

        Stage currentStage = (Stage) plakasorgugiris.getScene().getWindow();
        currentStage.close();

    }

    //Kullanının girdiği plaka, Türkiye plaka formatlarına uygun olup olmadığının kontrolü yapılır.
    public static boolean plakaFormatKontrol(String plaka) {
        String[] parcalar = plaka.split(" ");

        if(parcalar.length<2 || parcalar.length>3) {
            return false;
        }

        try{
            int sehirKodu=Integer.parseInt(parcalar[0]);
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

        String sayiGrubu = parcalar.length == 3 ? parcalar[2] : "";
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
}