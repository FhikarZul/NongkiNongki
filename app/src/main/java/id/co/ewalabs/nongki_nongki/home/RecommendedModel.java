package id.co.ewalabs.nongki_nongki.home;

public class RecommendedModel {
    private int idCafe;
    private String thumbCafe;


    public RecommendedModel(int idCafe, String thumbCafe) {
        this.idCafe = idCafe;
        this.thumbCafe = thumbCafe;
    }

    public String getThumbCafe() {
        return thumbCafe;
    }

    public int getIdCafe() {
        return idCafe;
    }
}
