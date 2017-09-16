package android.client.zxing.google.com.wish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.MenuInflater;
import android.widget.TextView;

// 새로운 위시리스트 등록 화면 https://app.zeplin.io/project.html#pid=58b073244156617e806eed6e&sid=58b08aa59029097880a42852
// TODO 푸시알림 부분 누르면 색 변하고 데이터 저장
public class NewWish extends AppCompatActivity {

    EditText wish;
    TextView new_wish_list, folder, on, off, gps_setting, clock, to_api, to_map;
    ImageView plus, buy_icon;
    Intent intent;
    POI poi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wish);

        Intent intent2 = new Intent(NewWish.this, MyService.class);
        startService(intent2);

        new_wish_list =(TextView)findViewById(R.id.new_wish_list);
        plus = (ImageView)findViewById(R.id.plus);
        wish = (EditText)findViewById(R.id.wish);
        folder = (TextView)findViewById(R.id.folder);
        buy_icon = (ImageView)findViewById(R.id.buy_icon);
        on = (TextView)findViewById(R.id.on);
        off = (TextView)findViewById(R.id.off);
        gps_setting = (TextView)findViewById(R.id.gps_setting);
        clock = (TextView)findViewById(R.id.clock);
        to_map = (TextView)findViewById(R.id.to_map);
        to_api = (TextView)findViewById(R.id.to_api);

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.folder :



                break;
            case R.id.clock :

                break;
            case R.id.to_map :

                poi = new POI();
                poi.setWish(wish.getText().toString());

                intent = new Intent(this, Map.class);
                intent.putExtra("poi",poi);
                startActivityForResult(intent, 100);

                break;
            case R.id.to_api :

                 poi = new POI();
                poi.setWish(wish.getText().toString());
                intent = new Intent(this, SearchList.class);
                intent.putExtra("poi",poi);

                startActivityForResult(intent, 100);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 100:
                    this.finish();
                break;
        }
    }
}