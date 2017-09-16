package android.client.zxing.google.com.wish;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

// 시작화면 프로필 등록 https://app.zeplin.io/project.html#pid=58b073244156617e806eed6e&sid=58b0ab354e943e908063990e
// TODO 검정줄 집어넣고 editText 배경 @null 로 만들기
public class MainActivity extends AppCompatActivity {

    private ImageView simg1, simg2, simg3, simg4, simg5;
    private EditText enter_id;
    private ImageView user_image;
    private ImageView btn1;
    DBHelper dbHelper;
    SQLiteDatabase db, db2;
    static String text_id;
    ContentValues row;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        db2 = dbHelper.getReadableDatabase();
        row = new ContentValues();
        Cursor cursor = db.query(DBHelper.TABLE_INFO, null, null, null, null, null, null, null);
        cursor.moveToNext();
        String setId = cursor.getString(1);

        simg1 = (ImageView) findViewById(R.id.select_image1);
        simg2 = (ImageView) findViewById(R.id.select_image2);
        simg3 = (ImageView) findViewById(R.id.select_image3);
        simg4 = (ImageView) findViewById(R.id.select_image4);
        simg5 = (ImageView) findViewById(R.id.select_image5);
        btn1 = (ImageView)findViewById(R.id.start);
        enter_id = (EditText) findViewById(R.id.enter_id);
        user_image = (ImageView) findViewById(R.id.user_image);

        // 디비에서 닉네임 얻어오기
        enter_id.setText(setId);

        simg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_image.setImageResource(R.drawable.user_img2);
            }
        });
        simg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_image.setImageResource(R.drawable.user_img1);
            }
        });
        simg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_image.setImageResource(R.drawable.user_img3);
            }
        });
        simg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_image.setImageResource(R.drawable.user_img4);
            }
        });
        simg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_image.setImageResource(R.drawable.user_img5);
            }
        });

        //디비 여기서 건들고 가야지
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                text_id = enter_id.getText().toString();
                row.put("name", text_id);

                String whereClause = null;
                String[] whereArgs = null;
                db.update(dbHelper.TABLE_INFO ,row,whereClause,whereArgs);

                Intent intent = new Intent(MainActivity.this, WishListActivity.class);
                startActivity(intent);
            }
        });
    }

}
