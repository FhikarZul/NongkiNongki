package id.co.ewalabs.nongki_nongki.model;

public class Comment {
    private int idKomentar;
    private int idCafe;
    private int idUser;
    private String isi;

    public Comment() {
    }

    public Comment(int idKomentar, int idCafe, int idUser, String isi) {
        this.idKomentar = idKomentar;
        this.idCafe = idCafe;
        this.idUser = idUser;
        this.isi = isi;
    }

    public int getIdKomentar() {
        return idKomentar;
    }

    public void setIdKomentar(int idKomentar) {
        this.idKomentar = idKomentar;
    }

    public int getIdCafe() {
        return idCafe;
    }

    public void setIdCafe(int idCafe) {
        this.idCafe = idCafe;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
