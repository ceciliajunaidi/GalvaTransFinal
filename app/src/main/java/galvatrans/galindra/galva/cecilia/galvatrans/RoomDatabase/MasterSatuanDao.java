package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MasterSatuanDao {

    @Insert
    void insertMasterSatuan(MasterSatuanEntity satuan);

    @Query("SELECT * FROM MasterSatuanEntity WHERE kodeSatuan = :kode")
    LiveData<MasterSatuanEntity> getSatuanById(String kode);

    @Query("DELETE FROM MasterSatuanEntity")
    void deleteMasterSatuan();

    @Query("SELECT * FROM MasterSatuanEntity")
    LiveData<List<MasterSatuanEntity>> getAllSatuan();

}
