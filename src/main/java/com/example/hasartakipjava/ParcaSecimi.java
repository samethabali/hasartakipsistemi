package com.example.hasartakipjava;

import java.util.List;

class ParcaSecimi {
    private List<String> secilenParcalar;
    private List<Integer> resimliSecilenParcalar;

    public ParcaSecimi(List<String> secilenParcalar, List<Integer> resimliSecilenParcalar) {
        this.secilenParcalar = secilenParcalar;
        this.resimliSecilenParcalar = resimliSecilenParcalar;
    }

    //Gerekli Getter ve Setter metodları oluşturulur.
    public List<String> getSecilenParcalar() {
        return secilenParcalar;
    }

    public void setSecilenParcalar(List<String> secilenParcalar) {
        this.secilenParcalar = secilenParcalar;
    }

    public List<Integer> getResimliSecilenParcalar() {
        return resimliSecilenParcalar;
    }

    public void setResimliSecilenParcalar(List<Integer> resimliSecilenParcalar) {
        this.resimliSecilenParcalar = resimliSecilenParcalar;
    }

    //servis_verileri.txt dosyasına kaydedilecek veriler toString metodu override edilerek gerekli format düzenlenmesi yapılır.
    @Override
    public String toString() {
        return secilenParcalar + ", " + resimliSecilenParcalar;
    }
}