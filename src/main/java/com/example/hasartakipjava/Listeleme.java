package com.example.hasartakipjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Listeleme {
    //Kaza verileri ve servis verileri tc ve plaka key-value'ları ile eşleşecek şekilde HashMap yöntemi ile yönetilir.
    private Map<String, Map<String, List<Kaza>>> aracKazalari;
    private Map<String, Map<String, List<ParcaSecimi>>> parcaSecimleri;

    //aracKazalari ve parcaSecimleri adında HashMap'ler oluşturulur.
    public Listeleme() {
        this.aracKazalari = new HashMap<>();
        this.parcaSecimleri = new HashMap<>();
    }

    //Bu metot ile Kaza sınıfından alınan veriler aracKazalari HashMap'ine yerleştirilir.
    public void ekleKaza(String tc, String plaka, String tarih, String neden, List<String> hasarliParcalar, String maliyet) {
        Kaza kaza = new Kaza(tarih, neden, hasarliParcalar, maliyet);

        aracKazalari.putIfAbsent(tc, new HashMap<>());
        Map<String, List<Kaza>> plakaMap = aracKazalari.get(tc);

        plakaMap.putIfAbsent(plaka, new ArrayList<>());
        plakaMap.get(plaka).add(kaza);
    }

    //Bu metot ile ParcaSecimi sınıfından alınan veriler parcaSecimleri HashMap'ine yerleştirilir.
    public void ekleParcaSecimi(String tc, String plaka, List<String> secilenParcalar, List<Integer> resimliSecilenParcalar) {
        ParcaSecimi parcaSecimi = new ParcaSecimi(secilenParcalar, resimliSecilenParcalar);
        parcaSecimleri.putIfAbsent(tc, new HashMap<>());
        Map<String, List<ParcaSecimi>> plakaMap = parcaSecimleri.get(tc);

        plakaMap.putIfAbsent(plaka, new ArrayList<>());
        plakaMap.get(plaka).add(parcaSecimi);
    }

    //Getter ve Setter metotları
    public Map<String, Map<String, List<Kaza>>> getAracKazalari() {
        return aracKazalari;
    }

    public  Map<String, Map<String, List<ParcaSecimi>>> getParcaSecimleri() {
        return parcaSecimleri;
    }
}