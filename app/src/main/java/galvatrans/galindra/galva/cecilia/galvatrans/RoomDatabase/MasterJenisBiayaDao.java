package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MasterJenisBiayaDao {

    @Insert
    void insertMasterJenisBiaya(MasterJenisBiayaEntity jenisBiaya);

    @Query("SELECT * FROM MasterJenisBiayaEntity WHERE kodeJenis = :kode")
    LiveData<MasterJenisBiayaEntity> getJenisBiayaById(String kode);

    @Query("SELECT * FROM MasterJenisBiayaEntity")
    LiveData<List<MasterJenisBiayaEntity>> getAllJenisBiaya();

    @Query("DELETE FROM MasterJenisBiayaEntity")
    void deleteMasterJenisBiaya();
}
