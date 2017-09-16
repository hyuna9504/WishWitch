package android.client.zxing.google.com.wish;

import java.util.Date;
import java.util.UUID;

//  형식보라고 놔둠
public class Wish {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Wish() {
        // 고유한 식별자를 생성한다.
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}

