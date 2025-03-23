package com.example.hasartakipjava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServisFile extends FileManager {
    //Listeleme sınıfının metodlarına ulaşmak için listeleme nesnesi oluşturulur.
    private final Listeleme listeleme;

    public ServisFile(Listeleme listeleme) {
        this.listeleme = listeleme;
    }

    //Bu metod servis_verileri.txt dosyasına HashMap içerisinde tutulan servis verilerini yazdırır.
    public void dosyayaYaz() {
        Map<String, Map<String, List<ParcaSecimi>>> servisgecmis = listeleme.getParcaSecimleri();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("servis_verileri.txt",true))) {
            for(var tcEntry:servisgecmis.entrySet()) {
                String tc = tcEntry.getKey();
                for(var plakaEntry:tcEntry.getValue().entrySet()) {
                    String plaka = plakaEntry.getKey();
                    for(ParcaSecimi parcaSecimi:plakaEntry.getValue()) {
                        writer.write(tc + ", " + plaka + ", " + parcaSecimi.toString());
                        writer.newLine();
                    }
                }
            }
            System.out.println("Veriler başarıyla dosyaya yazıldı!");
        }catch (IOException e) {
            System.err.println("Dosyaya yazma sırasında hata oluştu: " + e.getMessage());
        }
    }

    //Bu metod servis_verileri.txt dosyasında tutulan servis verilerini listeleme nesnesinin ekleParcaSecimi metoduna gönderir.
    //Dosyadan okuma yapılır.
    @Override
    public void dosyadanOku() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader("servis_verileri.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");

                if(parts.length<2) {
                    System.err.println("Geçersiz satır: " + line);
                    continue;
                }

                String tc = parts[0];
                String plaka = parts[1];

                // Parça isimleri ve resimli parça ID'lerini ayırır.
                List<String> parcalar = new ArrayList<>();
                List<Integer> resimliParcalar = new ArrayList<>();

                for(int i=2; i<parts.length; i++) {
                    parts[i] = parts[i].replace("[", "").replace("]", "");
                    try{
                        resimliParcalar.add(Integer.parseInt(parts[i]));
                    }catch (NumberFormatException e) {
                        parcalar.add(parts[i]);
                    }
                }
                listeleme.ekleParcaSecimi(tc, plaka, parcalar, resimliParcalar);
            }
        }
    }

    //servis_verileri.txt dosyasından login ekranından alınan plaka girişi ile eşleşen servis bilgileri döndürülür.
    public List<ParcaSecimi> plakaIleSorgula(String plaka) {
        List<ParcaSecimi> secilenParcalar = new ArrayList<>();
        Map<String, Map<String, List<ParcaSecimi>>> aracServis = listeleme.getParcaSecimleri();

        for(Map<String, List<ParcaSecimi>> plakaMap : aracServis.values()) {
            if(plakaMap.containsKey(plaka)) {
                secilenParcalar.addAll(plakaMap.get(plaka));
            }
        }
        return secilenParcalar;
    }
}