package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BiayaDao {

    @Insert
    void insertBiayaKendaraan(BiayaEntity biaya);

    @Query("SELECT * FROM BiayaEntity WHERE sjBiaya = :sjBiaya")
    LiveData<List<BiayaEntity>> getAllBiaya(String sjBiaya);

    @Query("DELETE FROM BiayaEntity WHERE idBiaya = :idBiaya")
    void deleteBiayaById(int idBiaya);

    @Query("SELECT * FROM BiayaEntity WHERE idBiaya = :idBiaya")
    LiveData<BiayaEntity> getBiayaById(int idBiaya);

    @Query("SELECT * FROM BiayaEntity")
    LiveData<List<BiayaEntity>> getAllBiaya();

    @Query("UPDATE BiayaEntity SET jumlah=:jumlah, harga=:biaya, keterangan=:keterangan, kmAkhir=:kmAkhir WHERE idBiaya=:idBiaya")
    void updateBiayaById(int idBiaya, String jumlah, String biaya, String keterangan, String kmAkhir);

    @Query("DELETE FROM BiayaEntity")
    void deleteBiaya();

}
