package org.rplbo.nilaimhs.nilaimahasiswa;

public class Parkir {
    private int id_parkir;
    private String no_plat;
    private int lama_detik;
    private int harga;

    public Parkir(int id_parkir, String no_plat, int lama_detik, int harga) {
        this.id_parkir = id_parkir;
        this.no_plat = no_plat;
        this.lama_detik = lama_detik;
        this.harga = harga;
    }

    public int getId_parkir() {
        return id_parkir;
    }

    public void setId_parkir(int id_parkir) {
        this.id_parkir = id_parkir;
    }

    public String getNo_plat() {
        return no_plat;
    }

    public void setNo_plat(String no_plat) {
        this.no_plat = no_plat;
    }

    public int getLama_detik() {
        return lama_detik;
    }

    public void setLama_detik(int lama_detik) {
        this.lama_detik = lama_detik;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
