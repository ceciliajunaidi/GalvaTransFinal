package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ImagesDao {

    @Insert
    void insertImage(ImagesEntity image);

    @Query("SELECT * FROM ImagesEntity WHERE sjImage=:sjImage")
    LiveData<List<ImagesEntity>> getAllImage(String sjImage);

    @Query("DELETE FROM ImagesEntity WHERE idImage=:idImage")
    void deleteImageById(int idImage);

    @Query("DELETE FROM ImagesEntity")
    void deleteImages();

}

