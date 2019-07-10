package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MasterDeptDao {

    @Insert
    void insertMasterDept(MasterDeptEntity dept);

    @Query("SELECT * FROM MasterDeptEntity WHERE kodeDept = :kode")
    LiveData<MasterDeptEntity> getDeptById(String kode);

    @Query("DELETE FROM MasterDeptEntity")
    void deleteMasterDept();

    @Query("SELECT * FROM MasterDeptEntity")
    LiveData<List<MasterDeptEntity>> getAllDept();

}
