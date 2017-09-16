package android.client.zxing.google.com.wish;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

// 30초 간격 gps로 현재위치 받아와서 위시리스트를 수행할 장소와 50m 이내에 있다면 푸쉬 알람이 온다
// TODO 지금 미친놈처럼 베터리 잡아먹는 중이니 수정
public class MyService extends Service {

    Intent intent2;
    LocationManager locManager;
//    PendingIntent proximityIntent;
    PendingIntent content;
    private Receiver receiver;
    NotificationManager mNotiManager;
    Notification noti;
    ArrayList<POI> myData = new ArrayList<POI>();
    DBHelper myDBHelper;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() { // 최초 생성시 한번 실행
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { // 백그라운드에서 실행되는 동작들이 들어가는 곳

        int check = 0;
        receiver = new Receiver();
        myDBHelper = new DBHelper(getApplicationContext());

        // 위치 관리자 준비
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location lastLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastLocation != null) {
            Double latitude = lastLocation.getLatitude();
            Double longitude = lastLocation.getLongitude();
        }

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);

        myDBHelper = new DBHelper(getApplicationContext());
        myData = new ArrayList<POI>();

        mNotiManager = (NotificationManager) (getApplicationContext()).getSystemService(MainActivity.NOTIFICATION_SERVICE);


        intent2 = new Intent((getApplicationContext()), SearchList.class);

        RemoteViews customView = new RemoteViews((getApplicationContext()).getPackageName(), R.layout.noti);


        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_INCOMLPETE, null, null, null, null, null, null, null);

        int count = 0;

        while (cursor.moveToNext()) {
            count++;
            POI newItem = new POI();
            newItem.setLatitude(cursor.getDouble(2));
            newItem.setLongitude(cursor.getDouble(3));
            newItem.setTitle(cursor.getString(5));

            myData.add(newItem); // myData 객체들이 들어가있음

        }
        noti = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(content)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[]{1000, 1000, 200, 200})
                .setLights(0xff00ff00, 500, 500)
                .setContent(customView)
                .build();

        noti.flags |= (Notification.FLAG_INSISTENT | Notification.FLAG_SHOW_LIGHTS);


        if (myData.size() == 2)
            mNotiManager.notify(2, noti);

//        locManager.addProximityAlert(37.507797, 127.044856, 100f, -1, content);
        IntentFilter iFilter = new IntentFilter("android.client.zxing.google.com.wish.Receiver");
        registerReceiver(receiver, iFilter);

        return super.onStartCommand(intent2, flags, startId);

    }

    /*위치 정보 수신 LocationListener*/
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            Double distance;
//            현재 수신한 위치 정보 Location을  LatLng 형태로 변환
            // 여기서 디비랑 비교
            LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
            checkDistance(location);


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void checkDistance(Location location){

        double distance;
        int i=0;

        Location locationA = new Location("현재위치 ");

        locationA.setLatitude(location.getLatitude());
        locationA.setLongitude(location.getLongitude());

        Location locationB = new Location("DB에저장된애");

        //체크가 0인애 !!!!!!!
        while(myData.size() > i) {

            POI poi = myData.get(i);

            locationB.setLatitude(poi.getLatitude());
            locationB.setLongitude(poi.getLongitude());

            // 두 위치 사이의 거리를 m로 받아오는 메소드
            distance = locationA.distanceTo(locationB);

            Log.e("distance ", "" + distance);

            if (distance < 50 && poi.getCheck() == 0) {
                mNotiManager.notify(2, noti);
                myData.remove(i);
                poi.setCheck(1);
                myData.add(i,poi);
                Log.e("알람운다  " , " i = " + i + "  POI" +  poi.getTitle() + " check " + poi.getCheck());
            }
            else{
                poi.setCheck(0);
            }

            i++;
        }
    }

}

