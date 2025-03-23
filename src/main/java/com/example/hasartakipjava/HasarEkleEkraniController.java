package com.example.hasartakipjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

public class HasarEkleEkraniController extends Listeleme{
    public HasarEkleEkraniController() {}

    //KazaFile ve Listeleme sınıflarının metodlarına ulaşmak için gerekli nesneler oluşturulur.
    private final Listeleme listeleme = new Listeleme();
    private final KazaFile kazaFile = new KazaFile(listeleme);

    @FXML
    public ListView<String> lv_hasarliparca;
    @FXML
    private Button bt_arti,bt_kaydet;
    @FXML
    private ChoiceBox<String> cb_kazaNedeni, cb_hasarliParca;
    @FXML
    private DatePicker dp_tarih;
    @FXML
    private Label lb_hasarliParca, lb_kazaNedeni, lb_kazaTarihi, lb_maliyet,lb_tl,lb_uyari;
    @FXML
    private TextField tf_maliyet;
    private final ObservableList<String> items = FXCollections.observableArrayList();
    private String tc;
    private String plaka;

    @FXML
    public void initialize() {
        //cb_kazaNedeni ve cb_hasarliParca choicebox'larının seçenekleri atanır ve listview görünümüne eklenir.
        cb_kazaNedeni.getItems().addAll("Kaza Nedeni Seçiniz", "Çarpışma", "Çarpma", "Devrilme", "Yoldan çıkma", "Park halinde çarpma", "Sabit nesneye çarpma", "Zincirleme kaza", "Hareket halindeki araçtan düşen cisim", "Diğer");
        cb_kazaNedeni.setValue("Kaza Nedeni Seçiniz");

        cb_hasarliParca.getItems().addAll("Hasarlı Parça Seçiniz", "Kaporta", "Farlar", "Arka Lambalar", "Camlar", "Motor Parçaları", "Süspansiyon Sistemi", "Fren Sistemi", "Egzoz Sistemi", "Direksiyon Sistemi", "Lastikler ve Jantlar", "Elektrik Aksamı", "İç Aksam", "Yakıt Sistemi", "Vites Sistemi");

        cb_hasarliParca.setValue("Hasarlı Parça Seçiniz");
        lv_hasarliparca.setItems(items);

        //HashMap tekniği kullanılarak aracBilgileriMap oluşturulur ve LoginController sınıfından aldığımız tc ve plaka bilgileri içine yerleştirilir.
        Map<String, String> aracBilgileriMap = LoginController.getAracBilgileriMap();

        //isEmpty ile Map'in kontrolü yapılır
        //Boş değilse aracBilgileriMap key'lerine value ataması yapılır.
        if(!aracBilgileriMap.isEmpty()) {
            String firstTc = aracBilgileriMap.keySet().iterator().next();
            tc = firstTc;
            plaka = aracBilgileriMap.get(firstTc);
        }
    }

    @FXML
    public void onAddChoiceBox() {
        //Kullanıcının hasarlı parça seçimi uygun mu kontrolü yapılır, eğer uygunsa items içine eklenir.
        //Uygun olmadığı durumda bir hata mesajı ekrana yansıtılır
        String selected = cb_hasarliParca.getValue();
        if(selected == null || items.contains(selected) || selected.equals("Hasarlı Parça Seçiniz")) {
            lb_uyari.setText("Lütfen geçerli parça seçiniz!");
        }else {
            items.add(selected);
            lb_uyari.setText("");
        }
    }

    @FXML
    public void onKaydetButtonClick() throws IOException {
        //Kaydet butonuna basıldığında kullanıcıdan alınan girişler uygun formatlara dönüştürülerek tarih, neden, maliyet Stringlerine atanır.
        //Kullanıcının seçtiği hasarlı parçalar String ifadeler tutan bir dizi içinde sıralanır.
        LocalDate secilenTarih = dp_tarih.getValue();
        String tarih = (secilenTarih != null) ? secilenTarih.toString() : null;

        String neden = cb_kazaNedeni.getValue();
        String maliyet = tf_maliyet.getText();
        maliyet = maliyet + " TL";
        String[] parcalarDizisi = items.toArray(new String[0]);

        //Kullanıcıdan alınan girişlerin null veya default kontrolleri yapılır.
        //Uygun değilse ekrana hata mesajı verilir.
        //Uygun olduğu durumda gerekli listeleme ve kaydetme fonksiyonları çağrılır ve ana sayfaya tekrar dönülür.
        if(tarih ==null || neden == null || neden.equals("Kaza Nedeni Seçiniz") || maliyet.equals(" TL")) {
            lb_uyari.setText("Lütfen tüm alanları doldurunuz!");
        }else {
            listeleme.ekleKaza(tc, plaka, tarih, neden, Arrays.asList(parcalarDizisi), maliyet);
            kazaFile.dosyayaYaz();

            //Kullanıcı girişleri doğruysa ekrana "Başarıyla Kaydedildi!" message box verilir.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bilgilendirme");
            alert.setHeaderText(null);
            alert.setContentText("Başarıyla Kaydedildi!");
            alert.showAndWait();

            //Ana sayfaya tekrar dönmek için FXMLLoader sınıfı çağrısı
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage firstStage = new Stage();
            firstStage.setTitle("Hasar Takip Sistemi");
            firstStage.setScene(new Scene(root));
            String css = getClass().getResource("/com/example/hasartakipjava/styles/style.css").toExternalForm();
            firstStage.show();

            Stage currentStage = (Stage) bt_arti.getScene().getWindow();
            currentStage.close();
        }
    }
}