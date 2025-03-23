package com.example.hasartakipjava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KazaFile extends  FileManager {
    //Listeleme sınıfının metodlarına ulaşmak için listeleme nesnesi oluşturulur ve parametre olarak verililr.
    private final Listeleme listeleme;

    public KazaFile(Listeleme listeleme) {
        this.listeleme = listeleme;
    }

    //Bu metod kaza_verileri.txt dosyasına HashMap içerisinde tutulan kaza verilerini yazdırır.
    public void dosyayaYaz() {
        Map<String, Map<String, List<Kaza>>> aracKazalari = listeleme.getAracKazalari();

        //("kaza_verileri.txt", true) kullanımındaki ", true" ile dosya append modda açılır.
        //Böylece verilerin kaybolmasının önüne geçilmiş oldu.
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("kaza_verileri.txt", true))) {
            for(var tcEntry:aracKazalari.entrySet()) {
                String tc = tcEntry.getKey();
                for(var plakaEntry:tcEntry.getValue().entrySet()) {
                    String plaka = plakaEntry.getKey();
                    for(Kaza kaza:plakaEntry.getValue()) {
                        writer.write(tc + ", " + plaka + ", " + kaza.toString());
                        writer.newLine();
                    }
                }
            }
            //Konsolda veri kontrolünün izlenmesi
            System.out.println("Veriler başarıyla dosyaya yazıldı!");
        }catch(IOException e) {
            System.err.println("Dosyaya yazma sırasında hata oluştu: " + e.getMessage());
        }
    }

    //Bu metod kaza_verileri.txt dosyasında tutulan kaza verilerini listeleme nesnesinin ekleKaza metoduna gönderir.
    //Dosyadan okuma yapılır.
    public void dosyadanOku() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader("kaza_verileri.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                //line String'ine dosyadan bir satırdaki veriler aktarılır.
                //Boş olmadığı sürece verileri ", " ile bölerek parts adlı String[]'de tutulur.
                //İndislerine sırasıyla gerekli veriler aktarılır.
                String[] parts = line.split(", ");

                String tc = parts[0];
                String plaka = parts[1];
                String tarih = parts[2];
                String neden = parts[3];
                String maliyet = parts[4];

                List<String> hasarliParcalar = new ArrayList<>();
                for(int i = 5; i < parts.length; i++) {
                    hasarliParcalar.add(parts[i]);
                }

                listeleme.ekleKaza(tc, plaka, tarih, neden, hasarliParcalar, maliyet);
            }
        }
    }

    //kaza_verileri.txt dosyasındaki veriler ile sorgu ekranından alınan plaka girişi eşleşmesi kontrol edilir.
    //Eşleşen kaza bilgileri döndürülür.
    public List<Kaza> plakaIleSorgula(String plaka) {
        List<Kaza> eslesenKazalar = new ArrayList<>();
        Map<String, Map<String, List<Kaza>>> aracKazalari = listeleme.getAracKazalari();

        for(Map<String, List<Kaza>> plakaMap : aracKazalari.values()) {
            if(plakaMap.containsKey(plaka)) {
                eslesenKazalar.addAll(plakaMap.get(plaka));
            }
        }
        return eslesenKazalar;
    }
}