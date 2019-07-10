package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import com.google.gson.annotations.SerializedName;

public class MasterMobil {

    @SerializedName("Kode")
    private String kodeMobil;
    @SerializedName("Nama")
    private String namaMobil;
    @SerializedName("Kode_Area")
    private String kodeArea;
    @SerializedName("nomorTNKB")
    private String nomorTnkb;
    @SerializedName("bahan_bakar")
    private String bahanBakar;

    public String getKodeMobil() {
        return kodeMobil;
    }

    public void setKodeMobil(String kodeMobil) {
        this.kodeMobil = kodeMobil;
    }

    public String getNamaMobil() {
        return namaMobil;
    }

    public void setNamaMobil(String namaMobil) {
        this.namaMobil = namaMobil;
    }

    public String getKodeArea() {
        return kodeArea;
    }

    public void setKodeArea(String kodeArea) {
        this.kodeArea = kodeArea;
    }

    public String getNomorTnkb() {
        return nomorTnkb;
    }

    public void setNomorTnkb(String nomorTnkb) {
        this.nomorTnkb = nomorTnkb;
    }

    public String getBahanBakar() {
        return bahanBakar;
    }

    public void setBahanBakar(String bahanBakar) {
        this.bahanBakar = bahanBakar;
    }
}
