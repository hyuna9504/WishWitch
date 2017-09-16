package android.client.zxing.google.com.wish;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qkrqh on 2017-02-24.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db";
    public static final String TABLE_COMPLETE = "complete";
    public static final String TABLE_INCOMLPETE = "incomplete";
    public static final String TABLE_FOLDER = "folder";
    public static final String TABLE_INFO = "info";

    public DBHelper(Context context) {
//        데이터베이스 파일 생성
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        데이터베이스 테이블 생성
        String createTable = "create table " + TABLE_COMPLETE + " ( _id integer primary key autoincrement, wish text, latitude double, longitude double, address text, name text, folder text);";
        sqLiteDatabase.execSQL(createTable);

        createTable = "create table " + TABLE_INCOMLPETE + " ( _id integer primary key autoincrement, wish text, latitude double, longitude double, address text, name text, folder text);";
        sqLiteDatabase.execSQL(createTable);

        createTable = "create table " + TABLE_FOLDER + " ( _id integer primary key autoincrement, folder text);";
        sqLiteDatabase.execSQL(createTable);

        createTable = "create table " + TABLE_INFO + " ( _id integer primary key autoincrement, name text);";
        sqLiteDatabase.execSQL(createTable);

        sqLiteDatabase.execSQL("insert into " + TABLE_COMPLETE + " values (null, '마스카라', " + 37.5079380 +", "  + 127.0450970 +" , '주소', '매장명' , '폴더');");
        sqLiteDatabase.execSQL("insert into " + TABLE_COMPLETE + " values (null, '세탁소 들르기', " + 37.5079380 +", "  + 127.0450970 +" , '주소', '매장명' , '폴더');");
        sqLiteDatabase.execSQL("insert into " + TABLE_INCOMLPETE + " values (null, '세탁소 들르기', " + 37.5079380 +", "  + 127.0450970 +" , '서울 강남구 삼성로69길 11', '워시엔조이코인워시 대치점' , '해야 할 것');");
        sqLiteDatabase.execSQL("insert into " + TABLE_INCOMLPETE + " values (null, '경비실 택배 찾기', " + 37.5079380 +", "  + 127.0450970 +" , '서울 강남구 언주로107길 48', '경비실' , '해야 할 것');");
        sqLiteDatabase.execSQL("insert into " + TABLE_INCOMLPETE + " values (null, '주방세제 사기', " + 37.5079380 +", "  + 127.0450970 +" , '경기 용인시 수지구 포은대로 552', '이마트 죽전점' , '사야 할 것');");
        sqLiteDatabase.execSQL("insert into " + TABLE_INCOMLPETE + " values (null, 'GS25 오모리 김치찌개 사기', " + 37.5079380 +", "  + 127.0450970 +" , '인천 계양구 계양문화로 55', 'GS25 계산동경점' , '사야 할 것');");
        sqLiteDatabase.execSQL("insert into " + TABLE_INCOMLPETE + " values (null, '인공눈물 사기', " + 37.5079380 +", "  + 127.0450970 +" , '대구 동구 산암로 98', '강남약국' , '사야 할 것');");
        sqLiteDatabase.execSQL("insert into " + TABLE_FOLDER + " values (null, '폴더1');");
        sqLiteDatabase.execSQL("insert into " + TABLE_INFO + " values (null, '봄');");

    }



    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
