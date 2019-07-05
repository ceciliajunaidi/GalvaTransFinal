package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rute {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("no_rute")
    @Expose
    public String noRute;
    @SerializedName("id_user")
    @Expose
    public String idUser;
    @SerializedName("id_karyawan")
    @Expose
    public String idKaryawan;
    @SerializedName("id_kendaraan")
    @Expose
    public String idKendaraan;
    @SerializedName("id_tugas")
    @Expose
    public String idTugas;
    @SerializedName("tgl_order")
    @Expose
    public String tglOrder;
    @SerializedName("mulai")
    @Expose
    public String mulai;
    @SerializedName("selesai")
    @Expose
    public String selesai;
    @SerializedName("durasi")
    @Expose
    public String durasi;
    @SerializedName("id_status")
    @Expose
    public String idStatus;

    public Rute(String id, String noRute, String idUser, String idKaryawan,
                String idKendaraan, String idTugas, String tglOrder, String mulai,
                String selesai, String durasi, String idStatus) {
        this.id = id;
        this.noRute = noRute;
        this.idUser = idUser;
        this.idKaryawan = idKaryawan;
        this.idKendaraan = idKendaraan;
        this.idTugas = idTugas;
        this.tglOrder = tglOrder;
        this.mulai = mulai;
        this.selesai = selesai;
        this.durasi = durasi;
        this.idStatus = idStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoRute() {
        return noRute;
    }

    public void setNoRute(String noRute) {
        this.noRute = noRute;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getIdKendaraan() {
        return idKendaraan;
    }

    public void setIdKendaraan(String idKendaraan) {
        this.idKendaraan = idKendaraan;
    }

    public String getIdTugas() {
        return idTugas;
    }

    public void setIdTugas(String idTugas) {
        this.idTugas = idTugas;
    }

    public String getTglOrder() {
        return tglOrder;
    }

    public void setTglOrder(String tglOrder) {
        this.tglOrder = tglOrder;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(String idStatus) {
        this.idStatus = idStatus;
    }
}
