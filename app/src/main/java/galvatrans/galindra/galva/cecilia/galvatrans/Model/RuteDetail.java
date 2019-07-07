package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import com.google.gson.annotations.SerializedName;

public class RuteDetail {

    @SerializedName("id")
    private String idRuteDetail;
    @SerializedName("rute")
    private String rute;
    @SerializedName("id_rute")
    private String idRute;
    @SerializedName("berangkat")
    private String berangkat;
    @SerializedName("tujuan")
    private String tujuan;
    @SerializedName("sampai")
    private String sampai;
    @SerializedName("note_admin")
    private String noteAdmin;
    @SerializedName("note_sopir")
    private String noteSopir;
    @SerializedName("foto")
    private String foto;
    @SerializedName("flag")
    private String flag;

    public String getIdRuteDetail() {
        return idRuteDetail;
    }

    public void setIdRuteDetail(String idRuteDetail) {
        this.idRuteDetail = idRuteDetail;
    }

    public String getRute() {
        return rute;
    }

    public void setRute(String rute) {
        this.rute = rute;
    }

    public String getIdRute() {
        return idRute;
    }

    public void setIdRute(String idRute) {
        this.idRute = idRute;
    }

    public String getBerangkat() {
        return berangkat;
    }

    public void setBerangkat(String berangkat) {
        this.berangkat = berangkat;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getSampai() {
        return sampai;
    }

    public void setSampai(String sampai) {
        this.sampai = sampai;
    }

    public String getNoteAdmin() {
        return noteAdmin;
    }

    public void setNoteAdmin(String noteAdmin) {
        this.noteAdmin = noteAdmin;
    }

    public String getNoteSopir() {
        return noteSopir;
    }

    public void setNoteSopir(String noteSopir) {
        this.noteSopir = noteSopir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
