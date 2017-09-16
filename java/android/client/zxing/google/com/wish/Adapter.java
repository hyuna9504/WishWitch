package android.client.zxing.google.com.wish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


// cafe 검색 후 나오는 결과 보여줄 어댑터
public class Adapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<POI> list; // 로컬API 결과 나온 데이터들 모음
    LayoutInflater inflater;
    ViewHolder viewHolder = null;


    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {

        return list.get(position);

    }

    public long getItemId(int position){

        return (long)position;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final int pos =  position;
        LinearLayout li;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout, parent, false);
            viewHolder.textTitle = (TextView)view.findViewById(R.id.title);
            viewHolder.textAddress = (TextView)view.findViewById(R.id.address);
            viewHolder.imageView = (ImageView)view.findViewById(R.id.iv1); // 이미지 연결 위해
            view.setTag(viewHolder);
            viewHolder.check_red = (ImageView)view.findViewById(R.id.checkbox);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        POI dto = list.get(position);


        viewHolder.textTitle.setText(dto.getTitle());
        viewHolder.textAddress.setText(dto.getAddress());

        viewHolder.imageView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(pos).getTitle() + " " , Toast.LENGTH_LONG).show();
            }
        });

        if(dto.getCheck() == 1){
            view.setBackgroundColor(Color.parseColor("#E35757"));
            viewHolder.textTitle.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.textAddress.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.imageView.setImageResource(R.mipmap.shape_2);
            viewHolder.check_red.setImageResource(R.mipmap.white_icon);
        }


        return view;
    }

    public void setList(ArrayList<POI> list) {
        this.list = list;
    }

    public Adapter(Context context, int layout, ArrayList<POI> myData) {
        this.context = context;
        this.layout = layout;
        this.list = myData;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    class ViewHolder {
        public TextView textTitle = null;
        public TextView textAddress = null;
        public ImageView imageView = null;
        public ImageView check_red = null;
    }
}
