package android.client.zxing.google.com.wish;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

//  화면이당
public class WishFragment extends Fragment {
    private static final String ARG_WISH_ID = "wish_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private POI mWish;
    private EditText mTitleField;



    public static WishFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WISH_ID, crimeId);

        WishFragment fragment = new WishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID wishId = (UUID)getArguments().getSerializable(ARG_WISH_ID);
        mWish = WishLab.get(getActivity()).getWish(wishId);


    }

    @Override // 뷰 구성 여기서 !!!
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wish, container, false);

        /*mTitleField.setText(mWish.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                    // 이 메서드의 실행 코드는 여기서는 필요 없음
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mWish.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // 이 메서드의 실행 코드는 여기서는 필요 없음
            }
        });*/

        updateDate();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            updateDate();
        }
    }

    private void updateDate() {

    }
}
