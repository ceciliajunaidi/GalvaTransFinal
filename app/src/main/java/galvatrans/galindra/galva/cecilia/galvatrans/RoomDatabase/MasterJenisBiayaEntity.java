package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MasterJenisBiayaEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String kodeJenis;
    private String jenisBiaya;
    private boolean statusKm;
    private String kodeSatuan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean getStatusKm() {
        return statusKm;
    }

    public void setStatusKm(boolean statusKm) {
        this.statusKm = statusKm;
    }

    public String getKodeSatuan() {
        return kodeSatuan;
    }

    public void setKodeSatuan(String kodeSatuan) {
        this.kodeSatuan = kodeSatuan;
    }
}
