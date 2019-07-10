package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import com.google.gson.annotations.SerializedName;

public class Rute {

    @SerializedName("id")
    private String idRute;
    @SerializedName("no_rute")
    private String noRute;
    @SerializedName("id_user")
    private String idUser;
    @SerializedName("id_karyawan")
    private String idKaryawan;
    @SerializedName("id_kendaraan")
    private String idKendaraan;
    @SerializedName("id_tugas")
    private String idTugas;
    @SerializedName("tgl_order")
    private String tglOrder;
    @SerializedName("mulai")
    private String mulai;
    @SerializedName("selesai")
    private String selesai;
    @SerializedName("durasi")
    private String durasi;
    @SerializedName("id_status")
    private String idStatus;

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdRute() {
        return idRute;
    }

    public void setIdRute(String idRute) {
        this.idRute = idRute;
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
