package com.example.hasartakipjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ServisIslemleriController {
    //Araç şeması üzerinde önceki servis işlemlerinden boyalı veya değişen bilgisine göre parça işlemlerini görüntüleyebilmek için
    //her parçanın sarı ve kırmızı halleri ImageView olarak eklenir.
    @FXML
    private Button btServisIslemiEkle;
    @FXML
    private Label lbServis;
    @FXML
    private ListView<String> lvServisGecmisi;
    @FXML
    private ImageView ontamponsari,solarkakapisari, kaputsari, tavansari, bagajsari, arkatamponsari, soloncamurluksari;
    @FXML
    private ImageView solonkapisari, solarkacamurluksari, sagarkacamurluksari, sagarkakapisari, sagonkapisari, sagoncamurluksari;
    @FXML
    private ImageView ontamponkirmizi, kaputkirmizi, tavankirmizi, bagajkirmizi, arkatamponkirmizi, soloncamurlukkirmizi;
    @FXML
    private ImageView solonkapikirmizi, solarkacamurlukkirmizi, sagarkacamurlukkirmizi, sagarkakapikirmizi, sagonkapikirmizi, sagoncamurlukkirmizi, solarkakapikirmizi;
    private List<ImageView> yellowParts;
    private List<ImageView> redParts;
    private final Listeleme listeleme;
    private final ServisFile servisFile;
    String tc;
    String plaka;

    //ServisFile ve Listeleme sınıflarının metodlarına ulaşmak için gerekli nesneler oluşturulur.
    public ServisIslemleriController() {
        this.listeleme = new Listeleme();
        this.servisFile = new ServisFile(listeleme);
    }

    //Login ekranından alınan tc ve plaka bilgileriyle eşleşen servis geçmişleri servis_verileri.txt dosyasından okunur.
    //Eşleşen bilgiler ListView içerisinde görüntülenir.
    //Araç şeması üzerinde parça durumuna göre eşleşen renkteki parça görünür hale (visible) getirilir.
    @FXML
    public void initialize() throws IOException {
        initializeParts();
        Map<String, String> aracBilgileriMap = LoginController.getAracBilgileriMap();
        servisFile.dosyadanOku();
        ObservableList<String> servisItems = FXCollections.observableArrayList();
        if(!aracBilgileriMap.isEmpty()) {
            String firstTc = aracBilgileriMap.keySet().iterator().next();
            tc = firstTc;
            plaka = aracBilgileriMap.get(firstTc);
        }

        List<ParcaSecimi> secilenParcalar = servisFile.plakaIleSorgula(plaka);

        for(ParcaSecimi parcaSecimi :secilenParcalar) {
            String secilenParcalarSirali = String.join("\n", parcaSecimi.getSecilenParcalar());
            servisItems.add(secilenParcalarSirali);

            int j=0;
            for(int i:parcaSecimi.getResimliSecilenParcalar()) {
                if(i==1) {
                    redParts.get(j).setVisible(true);
                    yellowParts.get(j).setVisible(false);
                }else if(i==2 && !redParts.get(j).isVisible()) {
                    yellowParts.get(j).setVisible(true);
                }
                j+=1;
            }
        }
        lvServisGecmisi.setItems(servisItems);
    }

    //Bu metod her bir parçanın sarı ve kırmızı hallerini ayrı listeler içerisinde tutar.
    private void initializeParts() {
        yellowParts = List.of(
                ontamponsari, kaputsari, tavansari, bagajsari, arkatamponsari, soloncamurluksari, solonkapisari, solarkacamurluksari,
                sagarkacamurluksari, sagarkakapisari, sagonkapisari, sagoncamurluksari, solarkakapisari
        );
        redParts = List.of(
                ontamponkirmizi, kaputkirmizi, tavankirmizi, bagajkirmizi, arkatamponkirmizi, soloncamurlukkirmizi, solonkapikirmizi, solarkacamurlukkirmizi,
                sagarkacamurlukkirmizi, sagarkakapikirmizi, sagonkapikirmizi, sagoncamurlukkirmizi, solarkakapikirmizi
        );
    }

    //Kullanıcı Servis İşlemleri Ekle butonuna bastığında Servis İşlemleri Ekle sayfasına gider.
    @FXML
    protected void onServisIslemiEkleButtonClick() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("servis-islemleri-ekle.fxml"));
        Parent root = loader.load();

        Stage firstStage = new Stage();
        firstStage.setTitle("Servis İşlemleri Ekle");
        firstStage.setScene(new Scene(root));
        String css = getClass().getResource("/com/example/hasartakipjava/styles/style.css").toExternalForm();
        firstStage.show();

        Stage currentStage = (Stage) lbServis.getScene().getWindow();
        currentStage.close();
    }

    //Kullanıcı Geri butonuna bastığında servis işlemleri sayfasına döner.
    @FXML
    protected void onBackButtonClick() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        Stage firstStage = new Stage();
        firstStage.setTitle("Servis İşlemleri");
        firstStage.setScene(new Scene(root));
        String css = getClass().getResource("/com/example/hasartakipjava/styles/style.css").toExternalForm();
        firstStage.show();

        Stage currentStage = (Stage) lbServis.getScene().getWindow();
        currentStage.close();
    }
}