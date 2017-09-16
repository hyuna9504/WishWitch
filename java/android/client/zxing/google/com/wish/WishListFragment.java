package android.client.zxing.google.com.wish;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

// 어댑터 역할인듯!!! https://app.zeplin.io/project.html#pid=58b073244156617e806eed6e&sid=58b08047739bb1a59292928d
public class WishListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mWishRecyclerView;
    private WishAdapter mAdapter;
    private boolean mSubtitleVisible;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        mWishRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mWishRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_wish_list, menu);
    }

    public void onStart(){
        super.onStart();
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_wish: // 얘가 추가
                POI wish = new POI();
                Intent intent = new Intent(getActivity(), NewWish.class);
                startActivityForResult(intent, 100);
                return true;
            case R.id.menu_user: // 프로필 설정
                wish = new POI();
                intent = MainActivity.newIntent(getActivity());
                startActivityForResult(intent, 100);
                return true;
            case R.id.ok:
                intent = new Intent(getActivity(), Setting.class );
                startActivityForResult(intent, 100);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI() {
        WishLab wishLab = WishLab.get(getActivity());
        List<POI> wishes = wishLab.getWishes();

        // 어댑터에 있는 리스트가 갱신이 안된거래여
        mAdapter = new WishAdapter(wishes);
        mWishRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    // 아이템 객체 하나 여기서 찾아
    private class WishHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private POI mWish;
        private TextView textDate;
        private TextView textLocation;
        private TextView textWish;

        public WishHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            //뷰 객체 여기서 찾아
            textDate = (TextView)itemView.findViewById(R.id.textDate);
            textLocation = (TextView)itemView.findViewById(R.id.textLocation);
            textWish = (TextView)itemView.findViewById(R.id.textWish);

        }

        public void bindWish(POI wish) {
            mWish = wish;
            textWish.setText(mWish.getWish());
            textLocation.setText(mWish.getAddress());
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class WishAdapter extends RecyclerView.Adapter<WishHolder> {
        private List<POI> mWishes;

        public WishAdapter(List<POI> wishes) {
            mWishes = wishes;
        }

        @Override
        public WishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_wish, parent, false);
            return new WishHolder(view);
        }

        @Override
        public void onBindViewHolder(WishHolder holder, int position) {
            POI wish = mWishes.get(position);
            holder.bindWish(wish);
        }

        @Override
        public int getItemCount() {
            return mWishes.size();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        updateUI();
        mAdapter.notifyDataSetChanged();
    }

}
