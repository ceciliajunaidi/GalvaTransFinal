package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import com.google.gson.annotations.SerializedName;

public class MasterSatuan {

    @SerializedName("kode")
    private String kodeSatuan;

    @SerializedName("nama")
    private String jenisSatuan;

    public MasterSatuan(String kodeSatuan, String jenisSatuan) {
        this.kodeSatuan = kodeSatuan;
        this.jenisSatuan = jenisSatuan;
    }

    public String getKodeSatuan() {
        return kodeSatuan;
    }

    public void setKodeSatuan(String kodeSatuan) {
        this.kodeSatuan = kodeSatuan;
    }

    public String getJenisSatuan() {
        return jenisSatuan;
    }

    public void setJenisSatuan(String jenisSatuan) {
        this.jenisSatuan = jenisSatuan;
    }
}
