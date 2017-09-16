package android.client.zxing.google.com.wish;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

// https://app.zeplin.io/project.html#pid=58b073244156617e806eed6e&sid=58b0772b7d25ae7d91e5bb32
public class SearchList extends AppCompatActivity {

    EditText etTargetDate;
    ListView lvList;
    String address; // api 사용위한 기본 url
    String search_address; // 검색 키워드 입력 받아서 합친 url
    DBHelper myDBHelper;
    POI data = new POI();
    SQLiteDatabase db;
    static String address2;
    static Double latitude ,longitude;
    ImageView icon;
    POI poi;

    Adapter adapter; // 커스텀 어댑터
    ArrayList<POI> resultList; // 어댑터에 연결할 데이터리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent intent = getIntent();

        etTargetDate = (EditText)findViewById(R.id.edit_search); // 메인액티비티에 있는 뷰
        lvList = (ListView)findViewById(R.id.listView);
        icon = (ImageView)findViewById(R.id.imageView3);
        icon.setOnClickListener(mClickListener);

        resultList = new ArrayList<POI>();
        adapter = new Adapter(this, R.layout.item, resultList);

        data = (POI)(intent.getSerializableExtra("poi"));
        Log.e("wish " , "   " + data.getWish());
        lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(Itemlistener);

        address = getResources().getString(R.string.api_url);
    }

    AdapterView.OnItemClickListener Itemlistener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.e(" 받아오나 테스트 ", "" + resultList.get(position).getLatitude());
            address2 = resultList.get(position).getAddress();
            latitude = resultList.get(position).getLatitude();
            longitude = resultList.get(position).getLongitude();

            poi = resultList.get(position);
            poi.setCheck(1);
            resultList.remove(position);
            resultList.add(position, poi);

            adapter.notifyDataSetChanged();
        }
    };


    ImageView.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            String target = etTargetDate.getText().toString();
            try {
                target = URLEncoder.encode(target, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            search_address =  address + target;
            new NetworkAsyncTask().execute(search_address);

        }
    };

    public boolean onCreateOptionsMenu(Menu menu){

        menu.add(0,1,0,"  ");

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        saveData();

        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();

        return true;

    }

    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {

        public final static String TAG = "NetworkAsyncTask";
        public final static int TIME_OUT = 10000;

        ProgressDialog progressDlg;
        String address;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(SearchList.this, "Wait", "Downloading..."); // 대화상자
        }

        @Override
        protected String doInBackground(String... strings) {
            address = strings[0];
            StringBuilder resultBuilder = new StringBuilder();

            try {
                URL url = new URL(address);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            resultBuilder.append(line + '\n');
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Toast.makeText(SearchList.this, "Malformed URL", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return resultBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            MyXmlParser parser = new MyXmlParser();

            resultList = parser.parse(result);

            adapter.setList(resultList); //            리스트뷰에 연결되어 있는 어댑터에 parsing 결과 ArrayList를 연결해줌
            adapter.notifyDataSetChanged();

            progressDlg.dismiss();
        }
    }

    public void saveData(){

        myDBHelper = new DBHelper(this);
        db = myDBHelper.getWritableDatabase();

        Log.e("wish " , " " + data.getWish() );
        ContentValues row = new ContentValues();
        row.put("wish", data.getWish());
        row.put("latitude", latitude);
        row.put("longitude", longitude);
        row.put("address", address2);
        db.insert(myDBHelper.TABLE_INCOMLPETE, null, row);
        db.close();
    }
}
