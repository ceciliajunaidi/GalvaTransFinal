package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import com.google.gson.annotations.SerializedName;

public class Rute {

    @SerializedName("id")
    public String id;
    @SerializedName("no_rute")
    public String noRute;
    @SerializedName("id_user")
    public String idUser;
    @SerializedName("id_karyawan")
    public String idKaryawan;
    @SerializedName("id_kendaraan")
    public String idKendaraan;
    @SerializedName("id_tugas")
    public String idTugas;
    @SerializedName("tgl_order")
    public String tglOrder;
    @SerializedName("mulai")
    public String mulai;
    @SerializedName("selesai")
    public String selesai;
    @SerializedName("durasi")
    public String durasi;
    @SerializedName("id_status")
    public String idStatus;

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
