package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class BiayaEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idBiaya;

    private String noBukti;

    private String sjBiaya;

    private String noKendaraan;

    private String kodeArea;

    private String kodeSopir;

    private String kodeJenis;

    private String jenisBiaya;

    private String jumlah;

    private String kodeSatuan;

    private String harga;

    private String satuan;

    private String kmAwal;

    private String kmAkhir;

    private String keterangan;

    private String entryDate;

    private String date;

    private String namaPerusahaan;

    public int getIdBiaya() {
        return idBiaya;
    }

    public void setIdBiaya(int idBiaya) {
        this.idBiaya = idBiaya;
    }

    public String getNoBukti() {
        return noBukti;
    }

    public void setNoBukti(String noBukti) {
        this.noBukti = noBukti;
    }

    public String getSjBiaya() {
        return sjBiaya;
    }

    public void setSjBiaya(String sjBiaya) {
        this.sjBiaya = sjBiaya;
    }

    public String getNoKendaraan() {
        return noKendaraan;
    }

    public void setNoKendaraan(String noKendaraan) {
        this.noKendaraan = noKendaraan;
    }

    public String getKodeArea() {
        return kodeArea;
    }

    public void setKodeArea(String kodeArea) {
        this.kodeArea = kodeArea;
    }

    public String getKodeSopir() {
        return kodeSopir;
    }

    public void setKodeSopir(String kodeSopir) {
        this.kodeSopir = kodeSopir;
    }

    public String getKodeJenis() {
        return kodeJenis;
    }

    public void setKodeJenis(String kodeJenis) {
        this.kodeJenis = kodeJenis;
    }

    public String getJenisBiaya() {
        return jenisBiaya;
    }

    public void setJenisBiaya(String jenisBiaya) {
        this.jenisBiaya = jenisBiaya;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKodeSatuan() {
        return kodeSatuan;
    }

    public void setKodeSatuan(String kodeSatuan) {
        this.kodeSatuan = kodeSatuan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getKmAwal() {
        return kmAwal;
    }

    public void setKmAwal(String kmAwal) {
        this.kmAwal = kmAwal;
    }

    public String getKmAkhir() {
        return kmAkhir;
    }

    public void setKmAkhir(String kmAkhir) {
        this.kmAkhir = kmAkhir;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }
}