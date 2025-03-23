package com.example.hasartakipjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServisIslemleriEkleController {

    //Araç şeması üzerinde önceki servis işlemlerinden boyalı veya değişen bilgisine göre parça işlemleri ekleyebilmek için
    //her bir parçanın sarı ve kırmızı halleri ImageView olarak eklenir.
    public ImageView carImageView;
    public ListView<String> lvservisislem;
    private final ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    private Button btArti;
    @FXML
    private Button btKaydet;
    @FXML
    private ChoiceBox<String> cbParcaSecimi;
    @FXML
    private Label lbAciklama;
    @FXML
    private Label lbHata;
    @FXML
    private TextField tfIslemAciklamasi;
    @FXML
    private ImageView ontampon, kaput, tavan, bagaj, arkatampon, soloncamurluk, solonkapi, solarkacamurluk;
    @FXML
    private ImageView sagarkacamurluk, sagarkakapi, sagonkapi, sagoncamurluk, solarkakapi,solarkakapisari;
    @FXML
    private ImageView ontamponsari, kaputsari, tavansari, bagajsari, arkatamponsari, soloncamurluksari;
    @FXML
    private ImageView solonkapisari, solarkacamurluksari, sagarkacamurluksari, sagarkakapisari, sagonkapisari, sagoncamurluksari;
    @FXML
    private ImageView ontamponkirmizi, kaputkirmizi, tavankirmizi, bagajkirmizi, arkatamponkirmizi, soloncamurlukkirmizi;
    @FXML
    private ImageView solonkapikirmizi, solarkacamurlukkirmizi, sagarkacamurlukkirmizi, sagarkakapikirmizi, sagonkapikirmizi, sagoncamurlukkirmizi, solarkakapikirmizi;
    @FXML
    private ChoiceBox<String> optionChoiceBox;
    private String selectedOption = "";
    private String tc;
    private String plaka;

    private List<ImageView> originalParts;
    private List<ImageView> yellowParts;
    private List<ImageView> redParts;

    //ServisFile ve Listeleme sınıflarının metodlarına ulaşmak için gerekli nesneler oluşturulur.
    private final Listeleme listeleme = new Listeleme();
    private final ServisFile servisFile = new ServisFile(listeleme);

    @FXML
    public void initialize() {
        //ChoiceBox seçenekleri atanır ve liste görünümüne eklenir.
        cbParcaSecimi.getItems().addAll("Yağ Değişimi", "Hava Filtresi Değişimi", "Fren Balataları Değişimi", "Lastik Değişimi ve Rotasyon", "Motor Arızası Tespiti ve Onarımı", "Elektrik Sistemi Kontrolü", "Akü Değişimi", "Klima Gazı Dolumu", "Şanzıman Yağı Değişimi", "Amortisör Değişimi", "Radyatör Temizliği ve Antifriz Dolumu", "Far Ayarı", "Egzoz Emisyon Testi", "Döşeme ve İç Temizlik", "Cam Kırığı Tamiri veya Değişimi", "Rot Balans Ayarı", "Direksiyon Sistemi Bakımı", "Yakıt Sistemi Temizliği", "Yedek Parça Değişimi");
        cbParcaSecimi.setValue("Parça Seçiniz");
        tfIslemAciklamasi.setPromptText("Açıklama giriniz");
        lvservisislem.setItems(items);

        optionChoiceBox.getItems().addAll("Orijinal ","Boyalı", "Değişen");
        optionChoiceBox.setValue("Orijinal");
        optionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedOption = newValue;
        });

        //LoginController sınıfından alınan aracBilgileriMap içerisine tc ve plaka yerleştirilir.
        Map<String, String> aracBilgileriMap = LoginController.getAracBilgileriMap();

        if(!aracBilgileriMap.isEmpty()) {
            String firstTc = aracBilgileriMap.keySet().iterator().next();
            tc = firstTc;
            plaka = aracBilgileriMap.get(firstTc);
        }

        initializeParts();

        //Eklediğimiz ImageView'lar için tıklanabilirlik özelliği aktif edilir.
        for(ImageView part:originalParts) {
            part.setOnMouseClicked(this::onImageClick);
        }
        for(ImageView part:yellowParts) {
            part.setOnMouseClicked(this::onImageClick);
        }
        for(ImageView part:redParts) {
            part.setOnMouseClicked(this::onImageClick);
        }
    }

    //Bu metod her bir parçanın sarı ve kırmızı hallerini ayrı listeler içerisinde tutar.
    private void initializeParts() {
        originalParts = List.of(
                ontampon, kaput, tavan, bagaj, arkatampon, soloncamurluk, solonkapi, solarkacamurluk,
                sagarkacamurluk, sagarkakapi, sagonkapi, sagoncamurluk, solarkakapi
        );
        yellowParts = List.of(
                ontamponsari, kaputsari, tavansari, bagajsari, arkatamponsari, soloncamurluksari, solonkapisari, solarkacamurluksari,
                sagarkacamurluksari, sagarkakapisari, sagonkapisari, sagoncamurluksari, solarkakapisari
        );
        redParts = List.of(
                ontamponkirmizi, kaputkirmizi, tavankirmizi, bagajkirmizi, arkatamponkirmizi, soloncamurlukkirmizi, solonkapikirmizi, solarkacamurlukkirmizi,
                sagarkacamurlukkirmizi, sagarkakapikirmizi, sagonkapikirmizi, sagoncamurlukkirmizi, solarkakapikirmizi
        );
    }

    //Kullanıcı, araç şeması üzerindeki ImageView'lara tıkladığında bu metod çağrılır.
    //partName ile tıklanan parçanın Id'si alınır. index değişkeni ile de indis numarası alınır.
    //colorName, seçilen işleme göree kırmızı veya sarı olarak ayarlanacak şekilde boş olarak tanımlanır.
    private void onImageClick(MouseEvent event){
        ImageView clickedPart = (ImageView) event.getSource();
        String partName=clickedPart.getId();
        String colorName = "";
        int index;
        try{
            if(selectedOption.equals("Değişen")) {
                if(yellowParts.contains(clickedPart)) { index=yellowParts.indexOf(clickedPart);}
                else if(redParts.contains(clickedPart)) { index=redParts.indexOf(clickedPart);}
                else { index=originalParts.indexOf(clickedPart);}
                colorName = "kirmizi";
                setVisibility(partName, colorName, index);
            }else if(selectedOption.equals("Boyalı")) {
                if(yellowParts.contains(clickedPart)) { index=yellowParts.indexOf(clickedPart);}
                else if(redParts.contains(clickedPart)) { index=redParts.indexOf(clickedPart);}
                else { index=originalParts.indexOf(clickedPart);}
                colorName = "sari";
                setVisibility(partName, colorName, index);
            }else {
                if(yellowParts.contains(clickedPart)) { index=yellowParts.indexOf(clickedPart);}
                else if(redParts.contains(clickedPart)) { index=redParts.indexOf(clickedPart);}
                else { index=originalParts.indexOf(clickedPart);}
                setVisibility(partName, colorName, index);
            }
        }catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //Bu metod ile ilk koşulda gönderilen colorName ve index değişkenlerine göre seçilen parçanın eşleşen rengindeki ImageVİew görünür hale getirilir.
    private void setVisibility(String partName, String colorName,int index) throws NoSuchFieldException, IllegalAccessException {
        if(partName!=null && colorName.equals("sari")) {
            ImageView imageView = yellowParts.get(index);
            imageView.setVisible(true);
            imageView = redParts.get(index);
            imageView.setVisible(false);
        }else if(partName != null && colorName.equals("kirmizi")) {
            ImageView imageView = yellowParts.get(index);
            imageView.setVisible(false);
            imageView = redParts.get(index);
            imageView.setVisible(true);
        }else if(partName!=null && colorName.isEmpty()) {
            ImageView imageView = yellowParts.get(index);
            imageView.setVisible(false);
            imageView = redParts.get(index);
            imageView.setVisible(false);
        }
    }

    //Kullanıcının servis işlemi eklemek için yaptığı parça seçiminin uygun olup olmadığının kontrolü yapılır.
    //Uygunsa ListView için oluşturulan items içine eklenir.
    //Parça daha önce eklenmiş veya seçim yapılmmaışsa ekrana hata mesajı verilir.
    //Uygun olmadığı durumda bir hata mesajı ekrana yansıtılır.
    @FXML
    public void onAddServisIslemi() {
        String selected = cbParcaSecimi.getValue();
        String aciklama = tfIslemAciklamasi.getText();
        if(selected.equals("Parça Seçiniz") || aciklama.isEmpty()) {
            lbHata.setText("Lütfen gerekli alanları doldurunuz!");
        }else {
            String formattedItem = selected + ": " + aciklama;
            if(items.contains(formattedItem)) {
                lbHata.setText("Bu parça daha önce eklenmiş");
            }else {
                items.add(formattedItem);
                lbHata.setText("");
            }
        }
    }

    //Bu butona basıldığındaa ListView içerisinin boş olup olmadığı kontrol edilir.
    //Boş olmadığı durumda gerekli listeleme ve servisFile dosyalarına gönderilir.
    //Ekrana başarılı bir şekilde kaydedildiğinin bilgisi gönderilir.
    //Servis İşlemleri ekranına dönülür.
    public void onIslemKaydetButtonClick() throws IOException {
        if(lvservisislem.getItems()==null) {
            lbHata.setText("En az bir parça seçimi yapın!");
        }else {
            lbHata.setText("Başarılı Giriş");
            List<String> servisParcalar = new ArrayList<>();
            ObservableList<String> servisParcaListesi = lvservisislem.getItems();

            if(servisParcaListesi != null && !servisParcaListesi.isEmpty()) {
                servisParcalar.addAll(servisParcaListesi); // Listeyi tamamen ekle
            }
            List<Integer> resimliParcalar = getresimliSecilenParcalar(yellowParts,redParts);
            listeleme.ekleParcaSecimi(tc,plaka,servisParcalar,resimliParcalar);
            servisFile.dosyayaYaz();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bilgilendirme");
            alert.setHeaderText(null);
            alert.setContentText("Başarıyla Kaydedildi!");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("servis-islemleri.fxml"));
            Parent root = loader.load();

            Stage firstStage = new Stage();
            firstStage.setTitle("Servis İşlemleri");
            firstStage.setScene(new Scene(root));
            String css = getClass().getResource("/com/example/hasartakipjava/styles/style.css").toExternalForm();
            firstStage.show();

            Stage currentStage = (Stage) btArti.getScene().getWindow();
            currentStage.close();
        }
    }

    //Araç şemasından seçilen parçaların renk bilgilerini Integer tipinde değerler tutan bir liste içerisine ekler.
    //Orijinal: 0, Değişen(kırmızı): 1, Boyalı(sarı):2 değerlerini temsil eder.
    public ArrayList<Integer> getresimliSecilenParcalar(List<ImageView> yellowParts, List<ImageView> redParts) {
        ArrayList<Integer> resimliParcalar = new ArrayList<>();
        for(int i=0; i<=12; i++) {
            if(redParts.get(i).isVisible()) {resimliParcalar.add(1);}
            else if(yellowParts.get(i).isVisible()) {resimliParcalar.add(2);}
            else {resimliParcalar.add(0);}
        }
        return resimliParcalar;
    }
}