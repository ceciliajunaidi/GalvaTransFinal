package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MasterSatuanEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String kodeSatuan;

    private String namaSatuan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeSatuan() {
        return kodeSatuan;
    }

    public void setKodeSatuan(String kodeSatuan) {
        this.kodeSatuan = kodeSatuan;
    }

    public String getNamaSatuan() {
        return namaSatuan;
    }

    public void setNamaSatuan(String namaSatuan) {
        this.namaSatuan = namaSatuan;
    }
}
