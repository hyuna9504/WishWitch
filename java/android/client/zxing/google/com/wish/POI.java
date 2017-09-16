package android.client.zxing.google.com.wish;

import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by qkrqh on 2017-02-24.
 */

public class POI implements Serializable {

    private int _id; //0
    private String wish; //1
    private double latitude; //2
    private double longitude;//3
    private String address; //4
    private String title; // 매장명  5
    private String folder; // 6
    private String date;
    int check;
    private UUID mId;

    private Marker marker;

    public POI(String wish, String title, String address) {
        this.wish = wish;
        this.title = title;
        this.address = address;
        check = 0;
        mId = UUID.randomUUID();
    }


    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public POI(){

        mId = UUID.randomUUID();
        check = 0;

    }
    public UUID getId() {
        return mId;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void setId(UUID _id) {
        this.mId = _id;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
