package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import com.google.gson.annotations.SerializedName;

public class MasterBiaya {

    @SerializedName("kode_jenis")
    private String kodeBiaya;

    @SerializedName("nama_jenisbiaya")
    private String jenisBiaya;

    @SerializedName("statuskm")
    private Boolean statusKm;

    @SerializedName("kode_satuan")
    private String satuan;

    public String getKodeBiaya() {
        return kodeBiaya;
    }

    public void setKodeBiaya(String kodeBiaya) {
        this.kodeBiaya = kodeBiaya;
    }

    public String getJenisBiaya() {
        return jenisBiaya;
    }

    public void setJenisBiaya(String jenisBiaya) {
        this.jenisBiaya = jenisBiaya;
    }

    public Boolean getStatusKm() {
        return statusKm;
    }

    public void setStatusKm(Boolean statusKm) {
        this.statusKm = statusKm;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}
