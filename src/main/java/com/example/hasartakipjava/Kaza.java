package com.example.hasartakipjava;

import java.util.List;

class Kaza {
    //kaza_verileri.txt dosyasının kullanacağı veriler bu sınıfta tutulur ve gerekli sınıflarda nesne aracılığyla erişim sağlanır.
    private String tarih;
    private String neden;
    private List<String> hasarliParcalar;
    private String maliyet;

    public Kaza(String tarih, String neden, List<String> hasarliParcalar, String maliyet) {
        this.tarih = tarih;
        this.neden = neden;
        this.hasarliParcalar = hasarliParcalar;
        this.maliyet = maliyet;
    }
    //kaza_verileri.txt dosyasına kaydedilecek veriler toString metodu override edilerek gerekli format düzenlenmesi yapılır.
    @Override
    public String toString() {
        return tarih + ", " + neden + ", " + maliyet + ", "  + (hasarliParcalar != null ? String.join(", ", hasarliParcalar) : "Yok") ;
    }
}