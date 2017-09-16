package android.client.zxing.google.com.wish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

// 알람반경 설정 https://app.zeplin.io/project.html#pid=58b073244156617e806eed6e&sid=58b0b89e84ebcb5991404017
// TODO 알람반경설정 위치 받을건지 설정받고
public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public boolean onCreateOptionsMenu(Menu menu){

        menu.add(0,1,0,"  ");

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return true;
    }
}
