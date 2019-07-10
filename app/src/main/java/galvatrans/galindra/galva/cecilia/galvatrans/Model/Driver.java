package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("response")
    private String response;

    @SerializedName("user")
    private String idDriver;

    @SerializedName("nama")
    private String namaDriver;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    public String getNamaDriver() {
        return namaDriver;
    }

    public void setNamaDriver(String namaDriver) {
        this.namaDriver = namaDriver;
    }
}
