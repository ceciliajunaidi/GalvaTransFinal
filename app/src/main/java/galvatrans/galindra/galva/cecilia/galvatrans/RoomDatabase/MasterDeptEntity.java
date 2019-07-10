package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MasterDeptEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String kodeDept;

    private String namaDept;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeDept() {
        return kodeDept;
    }

    public void setKodeDept(String kodeDept) {
        this.kodeDept = kodeDept;
    }

    public String getNamaDept() {
        return namaDept;
    }

    public void setNamaDept(String namaDept) {
        this.namaDept = namaDept;
    }
}
