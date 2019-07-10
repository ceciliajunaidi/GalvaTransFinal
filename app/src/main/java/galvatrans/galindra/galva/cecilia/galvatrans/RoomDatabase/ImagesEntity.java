package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ImagesEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idImage;

    private String image;

    private String sjImage;

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSjImage() {
        return sjImage;
    }

    public void setSjImage(String sjImage) {
        this.sjImage = sjImage;
    }
}
