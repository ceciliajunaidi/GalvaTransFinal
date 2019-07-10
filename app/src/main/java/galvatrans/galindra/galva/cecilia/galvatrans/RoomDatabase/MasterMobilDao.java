package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MasterMobilDao {

    @Insert
    void insertMasterMobil(MasterMobilEntity mobil);

    @Query("SELECT * FROM MasterMobilEntity WHERE kode = :kode")
    LiveData<MasterMobilEntity> getMobilById(String kode);

    @Query("DELETE FROM MasterMobilEntity")
    void deleteMasterMobil();

    @Query("SELECT * FROM MasterMobilEntity")
    LiveData<List<MasterMobilEntity>> getAllMobil();

}
