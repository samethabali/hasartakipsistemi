package com.example.hasartakipjava;

import java.io.IOException;

public abstract class FileManager {
    //Bu metotlar KazaFile ve ServisFile sınıflarında override edilir.

    //dosyayaYaz metodu kullanıcıdan alınan verilerin KazaFile sınıfı içerisinde kaza_verileri.txt dosyasına,
    //ServisFile sınıfı içerisinde servis_verileri.txt dosyasına yazdırılmasını ve burada tutulmasını sağlar.
    abstract void dosyayaYaz();

    //dosyadanOku metodu kullanıcının istediği  verilerin KazaFile sınıfı içerisinde kaza_verileri.txt dosyasından,
    //ServisFile sınıfı içerisinde servis_verileri.txt dosyasından alınmasını ve kullanılmasını sağlar.
    abstract void dosyadanOku() throws IOException;

}