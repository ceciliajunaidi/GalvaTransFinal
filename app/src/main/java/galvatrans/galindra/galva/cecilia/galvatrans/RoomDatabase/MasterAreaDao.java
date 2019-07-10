package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MasterAreaDao {

    @Insert
    void insertMasterArea(MasterAreaEntity area);

    @Query("SELECT * FROM MasterAreaEntity WHERE kodeArea = :kode")
    LiveData<MasterAreaEntity> getAreaById(String kode);

    @Query("DELETE FROM MasterAreaEntity")
    void deleteMasterArea();

    @Query("SELECT * FROM MasterAreaEntity")
    LiveData<List<MasterAreaEntity>> getAllArea();

}
