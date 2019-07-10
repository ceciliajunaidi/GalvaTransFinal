package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MasterAreaEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String kodeArea;

    private String namaArea;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeArea() {
        return kodeArea;
    }

    public void setKodeArea(String kodeArea) {
        this.kodeArea = kodeArea;
    }

    public String getNamaArea() {
        return namaArea;
    }

    public void setNamaArea(String namaArea) {
        this.namaArea = namaArea;
    }
}
