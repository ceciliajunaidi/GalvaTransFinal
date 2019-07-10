package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ImagesEntity.class, BiayaEntity.class, MasterMobilEntity.class,
        MasterJenisBiayaEntity.class, MasterSatuanEntity.class, MasterDeptEntity.class,
        MasterAreaEntity.class}, version = 1, exportSchema = false)

abstract class DatabaseRoom extends RoomDatabase {

    abstract ImagesDao imagesDao();

    abstract BiayaDao biayaDao();

    abstract MasterMobilDao masterMobilDao();

    abstract MasterJenisBiayaDao masterJenisBiayaDao();

    abstract MasterSatuanDao masterSatuanDao();

    abstract MasterDeptDao masterDeptDao();

    abstract MasterAreaDao masterAreaDao();

}
