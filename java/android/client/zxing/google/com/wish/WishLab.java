package android.client.zxing.google.com.wish;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 객체들을 저장하는 데이터 저장소
public class WishLab {

    DBHelper dbHelper;
    SQLiteDatabase db;
    private static WishLab sWishLab;
    private List<POI> mWishes;


    public static WishLab get(Context context) {
        if (sWishLab == null) {
            sWishLab = new WishLab(context);
        }
        return sWishLab;
    }

    private WishLab(Context context) {
        dbHelper = new DBHelper(context);
        mWishes = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_INCOMLPETE, null, null, null, null, null, null, null);

        int count = 0;

        while (cursor.moveToNext()) {
            count++;
            POI newItem = new POI();
            newItem.setWish(cursor.getString(1));
            newItem.setLatitude(cursor.getDouble(2));
            newItem.setLongitude(cursor.getDouble(3));
            newItem.setAddress(cursor.getString(4));
            newItem.setTitle(cursor.getString(5));

            mWishes.add(newItem); // myData 객체들이 들어가있음
        }

    }

    public void addWish(POI c) {
        mWishes.add(c);
    }

    public List<POI> getWishes() {

        mWishes = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_INCOMLPETE, null, null, null, null, null, null, null);

        int count = 0;

        while (cursor.moveToNext()) {
            count++;
            POI newItem = new POI();
            newItem.setWish(cursor.getString(1));
            newItem.setLatitude(cursor.getDouble(2));
            newItem.setLongitude(cursor.getDouble(3));
            newItem.setAddress(cursor.getString(4));
            newItem.setTitle(cursor.getString(5));

            mWishes.add(newItem); // myData 객체들이 들어가있음
        }

        return mWishes;
    }

    public POI getWish(UUID id) {
        for (POI wish : mWishes) {
            if (wish.getId().equals(id)) {
                return wish;
            }
        }
        return null;
    }
}