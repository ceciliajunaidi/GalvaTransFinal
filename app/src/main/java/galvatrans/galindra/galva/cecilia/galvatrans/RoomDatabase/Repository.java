package galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class Repository {

    private DatabaseRoom myDatabase;
    String idDricver = "fadadada";

    public Repository(Context context) {
        this.myDatabase = Room.databaseBuilder(context, DatabaseRoom.class, "db_sj").build();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          TABLE IMAGE                                       //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    public void insertImage(ImagesEntity image) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.imagesDao().insertImage(image);

                return null;
            }
        }.execute();
    }

    public LiveData<List<ImagesEntity>> getAllImage(String idSj) {
        return myDatabase.imagesDao().getAllImage(idSj);
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteImageById(int idImage) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.imagesDao().deleteImageById(idImage);

                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteImages() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.imagesDao().deleteImages();

                return null;
            }
        }.execute();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                         TABLE BIAYA                                        //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    public void insertBiaya(BiayaEntity biaya) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.biayaDao().insertBiayaKendaraan(biaya);

                return null;
            }
        }.execute();
    }

    public LiveData<List<BiayaEntity>> getAllBiaya(String idSj) {
        return myDatabase.biayaDao().getAllBiaya(idSj);
    }

    public LiveData<BiayaEntity> getBiayaByIdBiaya(int idBiaya) {
        return myDatabase.biayaDao().getBiayaById(idBiaya);
    }

    public LiveData<List<BiayaEntity>> getAllBiaya() {
        return myDatabase.biayaDao().getAllBiaya();
    }

    @SuppressLint("StaticFieldLeak")
    public void updateBiayaById(int idBiaya, String jumlah, String biaya, String keterangan, String kmAkhir) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.biayaDao().updateBiayaById(idBiaya, jumlah, biaya, keterangan, kmAkhir);

                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteBiayaById(int idBiaya) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.biayaDao().deleteBiayaById(idBiaya);

                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteBiayas() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.biayaDao().deleteBiaya();

                return null;
            }
        }.execute();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      TABLE MASTER MOBIL                                    //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    public void insertMasterMobil(MasterMobilEntity mobil) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterMobilDao().insertMasterMobil(mobil);

                return null;
            }
        }.execute();
    }

    public LiveData<MasterMobilEntity> getMobilById(String id) {
        return myDatabase.masterMobilDao().getMobilById(id);
    }

    public LiveData<List<MasterMobilEntity>> getAllMobil() {
        return myDatabase.masterMobilDao().getAllMobil();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteMasterMobil() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterMobilDao().deleteMasterMobil();

                return null;
            }
        }.execute();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   TABLE MASTER JENIS BIAYA                                 //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    public void insertMasterJenisBiaya(MasterJenisBiayaEntity jenisBiaya) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterJenisBiayaDao().insertMasterJenisBiaya(jenisBiaya);

                return null;
            }
        }.execute();
    }

    public LiveData<MasterJenisBiayaEntity> getJenisBiayaById(String id) {
        return myDatabase.masterJenisBiayaDao().getJenisBiayaById(id);
    }

    public LiveData<List<MasterJenisBiayaEntity>> getAllJenisBiaya() {
        return myDatabase.masterJenisBiayaDao().getAllJenisBiaya();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteMasterJenisBiaya() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterJenisBiayaDao().deleteMasterJenisBiaya();

                return null;
            }
        }.execute();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   TABLE MASTER JENIS SATUAN                                //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    public void insertMasterSatuan(MasterSatuanEntity satuan) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterSatuanDao().insertMasterSatuan(satuan);

                return null;
            }
        }.execute();
    }

    public LiveData<MasterSatuanEntity> getSatuanById(String id) {
        return myDatabase.masterSatuanDao().getSatuanById(id);
    }

    public LiveData<List<MasterSatuanEntity>> getAllSatuan() {
        return myDatabase.masterSatuanDao().getAllSatuan();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteMasterSatuan() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterSatuanDao().deleteMasterSatuan();

                return null;
            }
        }.execute();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   TABLE MASTER DEPARTERMEN                                 //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    public void insertMasterDept(MasterDeptEntity dept) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterDeptDao().insertMasterDept(dept);

                return null;
            }
        }.execute();
    }

    public LiveData<MasterDeptEntity> getDeptById(String id) {
        return myDatabase.masterDeptDao().getDeptById(id);
    }

    public LiveData<List<MasterDeptEntity>> getAllDept() {
        return myDatabase.masterDeptDao().getAllDept();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteMasterDept() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterDeptDao().deleteMasterDept();

                return null;
            }
        }.execute();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      TABLE MASTER AREA                                     //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    public void insertMasterArea(MasterAreaEntity area) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterAreaDao().insertMasterArea(area);

                return null;
            }
        }.execute();
    }

    public LiveData<MasterAreaEntity> getAreaById(String id) {
        return myDatabase.masterAreaDao().getAreaById(id);
    }

    public LiveData<List<MasterAreaEntity>> getAllArea() {
        return myDatabase.masterAreaDao().getAllArea();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteMasterArea() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.masterAreaDao().deleteMasterArea();

                return null;
            }
        }.execute();
    }

}
