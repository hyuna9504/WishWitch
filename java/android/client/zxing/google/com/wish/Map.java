package android.client.zxing.google.com.wish;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 지도를 통해서 직접 위치 설정  https://app.zeplin.io/project.html#pid=58b073244156617e806eed6e&sid=58b0937284ebcb59913fa342
// TODO 반투명 가능한데 마커에 설정해야되서...
public class Map extends AppCompatActivity {

    private GoogleMap googleMap;
    DBHelper dbHelper;

    private MarkerOptions centerMarkerOptions;
    private Marker centerMarker;

    private MarkerOptions poiMarkerOptions;
    private ArrayList<POI> poiList;
    List<Address> list = null;

    private LocationManager locManager;

    SQLiteDatabase db;
    POI data = new POI();
    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();

        geocoder = new Geocoder(this);

        data = (POI)(intent.getSerializableExtra("poi"));

        // 위치 관리자 준비
        startLocationService();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallback);

        poiList = new ArrayList<POI>();

        readData();

        /*중심 지점을 위한 마커 옵션 준비*/
        centerMarkerOptions = new MarkerOptions();
        centerMarkerOptions.title("현재 위치");
        centerMarkerOptions.snippet("현재 위치 입니다.");
        centerMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.blue_marker));

        poiMarkerOptions = new MarkerOptions();
        poiMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pink_marker));
    }



    /*Google Map 준비 시 호출할 CallBack 인터페이스*/
    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap Map) {
//            로딩한 구글맵을 보관
            googleMap = Map;

            LatLng lastLatLng = new LatLng(32.5079380, 127.0450970);

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 17));

            centerMarkerOptions.position(lastLatLng);
            centerMarker = googleMap.addMarker(centerMarkerOptions);
            centerMarker.showInfoWindow();

            for (POI poi : poiList) {
                poiMarkerOptions.title(poi.getTitle());
                poiMarkerOptions.snippet(poi.getAddress());
                poiMarkerOptions.position(new LatLng(poi.getLatitude(), poi.getLongitude()));
                poi.setMarker(googleMap.addMarker(poiMarkerOptions));

                LatLng latlng = new LatLng(poi.getLatitude(), poi.getLongitude());

                Marker newMarker;
                poiMarkerOptions.position(latlng);
                newMarker = googleMap.addMarker(poiMarkerOptions);
                newMarker.showInfoWindow();/*지도에 추가한 마커를 POI 객체에 저장*/

            }


//            마커 윈도우 클릭 시 이벤트 처리
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    /*클릭한 마커 객체 poiList에서 검색*/
                    for (POI poi : poiList) {
                        if (poi.getMarker().equals(marker)) {       /*현재 클릭한 InfoWindow의 마커를 poiList 의 poi 가 갖고 있는 마커와 비교*/
                            Toast.makeText(Map.this, "주소: " + poi.getAddress(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });


        }
    };

    /*위치 정보 수신 LocationListener*/
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

//            현재 수신한 위치 정보 Location을  LatLng 형태로 변환
            LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());

            Toast.makeText(Map.this, "변화", Toast.LENGTH_LONG).show();
//            새로운 위치로 지도 이동
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));

            // 새로운 위치로 마커의 위치 지정
            centerMarker.setPosition(currentLoc);

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

    // 위치 정보 수신 시작
    private void startLocationService() {
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Location lastLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(lastLocation != null){
            Double latitude = lastLocation.getLatitude();
            Double longitude = lastLocation.getLongitude();
        }

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1000, locationListener);

        Toast.makeText(Map.this, "시작", Toast.LENGTH_LONG).show();

    }

    public void readData() {

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from incomplete", null);
        while (cursor.moveToNext()) {
            POI item = new POI();
            item.set_id(cursor.getInt(0));
            item.setTitle(cursor.getString(5));
            item.setAddress(cursor.getString(4));
            item.setLatitude(cursor.getDouble(2));
            item.setLongitude(cursor.getDouble(3));
            poiList.add(item);

            //Log.e("dd", "" + cursor.getString(1) + "  " + cursor.getDouble(4));

        }

        db.close();
        dbHelper.close();
    }

    public boolean onCreateOptionsMenu(Menu menu){

        menu.add(0,1,0,"  ");

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        if(locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
            data.setLatitude(locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude());
            data.setLongitude(locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
        }

        else{
            data.setLongitude(127.0450970);
            data.setLatitude(37.5079380);
        }

        saveData();

        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();

        return true;

    }


        protected void onPause() {
        super.onPause();
//        위치 정보 수신 종료 - 위치 정보 수신 종료를 누르지 않았을 경우를 대비
 //       locManager.removeUpdates(locationListener);
    }

    public void saveData(){

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        String address;
        try {
            list = geocoder.getFromLocation((Double)data.getLatitude(), (Double)data.getLongitude(), 1);
            address = list.get(0).getAddressLine(0).toString();

        } catch (IOException e) {
            address = "서울 강남구 삼성로69길 11";
        }

        ContentValues row = new ContentValues();
        row.put("wish", data.getWish());
        row.put("latitude", data.getLatitude());
        row.put("longitude", data.getLongitude());
        row.put("address", address);
        db.insert(dbHelper.TABLE_INCOMLPETE, null, row);
        db.close();
    }
}
