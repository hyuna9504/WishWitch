package android.client.zxing.google.com.wish;

import android.support.v4.app.Fragment;

public class WishListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WishListFragment();
    }
}
