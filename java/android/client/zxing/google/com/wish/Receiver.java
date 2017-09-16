package android.client.zxing.google.com.wish;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.RemoteViews;

// 안쓸삘인데 우선 놔둠
public class Receiver extends BroadcastReceiver {

    NotificationManager mNotiManager;
    Notification noti;
    Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotiManager = (NotificationManager)context.getSystemService(MainActivity.NOTIFICATION_SERVICE);

        intent = new Intent(context, NewWish.class);

        PendingIntent content = PendingIntent.getActivity(context, 0, intent, 0);

        Log.e("tt", "s");

        RemoteViews customView = new RemoteViews(context.getPackageName(), R.layout.noti);

        noti = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.heart)
                .setContentIntent(content)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[] {1000,1000,200,200})
                .setLights(0xff00ff00, 500, 500)
                .setContent(customView)
                .build();

        noti.flags |= (Notification.FLAG_INSISTENT | Notification.FLAG_SHOW_LIGHTS);

        mNotiManager.notify(1, noti);
    }
}
