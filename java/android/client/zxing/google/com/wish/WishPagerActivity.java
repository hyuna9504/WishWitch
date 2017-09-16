package android.client.zxing.google.com.wish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class WishPagerActivity extends AppCompatActivity {

    private static final String EXTRA_WISH_ID =
            "com.bignerdranch.android.criminalintent.wish_id";

    private ViewPager mViewPager;
    private List<POI> mWishes;
    private TextView textView;

    public static Intent newIntent(Context packageContext, UUID wishId) {
        Intent intent = new Intent(packageContext, WishPagerActivity.class);
        intent.putExtra(EXTRA_WISH_ID, wishId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_pager);

        UUID wishId = (UUID)getIntent()
                .getSerializableExtra(EXTRA_WISH_ID);

        Intent intent2 = new Intent(getApplicationContext(), MyService.class);
        startService(intent2);

        mViewPager = (ViewPager) findViewById(R.id.activity_wish_pager_view_pager);
        textView = (TextView)findViewById(R.id.dd);
       /* mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });*/

        mWishes = WishLab.get(this).getWishes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                POI wish = mWishes.get(position);
                return WishFragment.newInstance(wish.getId());
            }

            @Override
            public int getCount() {
                return mWishes.size();
            }
        });

        for (int i = 0; i < mWishes.size(); i++) {
            if (mWishes.get(i).getId().equals(wishId)) {
                mViewPager.setCurrentItem(i);
                Log.e("count" , "  " + mWishes.size());

                break;
            }
        }
    }
}
