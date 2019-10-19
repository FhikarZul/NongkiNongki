package id.co.ewalabs.nongki_nongki.home.cafe_terdekat;

public class CafeTerdekatModel {
    private int idCafe;
    private String namaCafe;
    private String lokasi;

    public CafeTerdekatModel(int idCafe, String namaCafe, String lokasi) {
        this.idCafe = idCafe;
        this.namaCafe = namaCafe;
        this.lokasi = lokasi;
    }


    public int getIdCafe() {
        return idCafe;
    }

    public String getNamaCafe() {
        return namaCafe;
    }

    public String getLokasi() {
        return lokasi;
    }
}
