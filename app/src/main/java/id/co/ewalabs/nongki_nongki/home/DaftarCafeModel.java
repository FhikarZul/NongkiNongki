package id.co.ewalabs.nongki_nongki.home;

import android.os.Parcel;
import android.os.Parcelable;

public class DaftarCafeModel implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idCafe);
        dest.writeString(this.thumbCafe);
        dest.writeString(this.namaCafe);
        dest.writeInt(this.jumLike);
        dest.writeInt(this.jumKomentar);
    }

    protected DaftarCafeModel(Parcel in) {
        this.idCafe = in.readInt();
        this.thumbCafe = in.readString();
        this.namaCafe = in.readString();
        this.jumLike = in.readInt();
        this.jumKomentar = in.readInt();
    }

    public static final Parcelable.Creator<DaftarCafeModel> CREATOR = new Parcelable.Creator<DaftarCafeModel>() {
        @Override
        public DaftarCafeModel createFromParcel(Parcel source) {
            return new DaftarCafeModel(source);
        }

        @Override
        public DaftarCafeModel[] newArray(int size) {
            return new DaftarCafeModel[size];
        }
    };
}




