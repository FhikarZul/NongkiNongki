package id.co.ewalabs.nongki_nongki.home;

public class DaftarCafeModel {
    private int idCafe;
    private String thumbCafe;
    private String namaCafe;
    private int jumLike;
    private int jumKomentar;

    public DaftarCafeModel(int idCafe, String thumbCafe, String namaCafe, int jumKomentar, int jumLike) {
        this.idCafe = idCafe;
        this.thumbCafe = thumbCafe;
        this.namaCafe = namaCafe;
        this.jumLike = jumLike;
        this.jumKomentar = jumKomentar;
    }

    public int getIdCafe() {
        return idCafe;
    }

    public String getThumbCafe() {
        return thumbCafe;
    }

    public String getNamaCafe() {
        return namaCafe;
    }

    public int getJumLike() {
        return jumLike;
    }

    public int getJumKomentar() {
        return jumKomentar;
    }
}




