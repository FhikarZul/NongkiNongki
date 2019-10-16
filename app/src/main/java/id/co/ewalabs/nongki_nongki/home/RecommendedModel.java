package id.co.ewalabs.nongki_nongki.home;

public class RecommendedModel {
    private String tumbCafe;
    private int idCafe;

    public RecommendedModel(String tumbCafe, int idCafe) {
        this.tumbCafe = tumbCafe;
        this.idCafe = idCafe;
    }

    public String getTumbCafe() {
        return tumbCafe;
    }

    public int getIdCafe() {
        return idCafe;
    }
}
