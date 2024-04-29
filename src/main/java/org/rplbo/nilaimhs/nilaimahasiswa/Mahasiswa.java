package org.rplbo.nilaimhs.nilaimahasiswa;

public class Mahasiswa {
    private String nim;
    private String nama;
    private double nilai;

    public Mahasiswa(String nim, String nama, double nilai) {
        this.nim = nim;
        this.nama = nama;
        this.nilai = nilai;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getNilai() {
        return nilai;
    }

    public void setNilai(double nilai) {
        this.nilai = nilai;
    }

    public String toString(){
        StringBuilder hasil = new StringBuilder();
        hasil.append(nim+" - "+nama+" ("+nilai+")");
        return hasil.toString();
    }
}
