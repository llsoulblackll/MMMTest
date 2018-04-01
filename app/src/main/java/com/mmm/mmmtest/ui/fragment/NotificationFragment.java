package com.mmm.mmmtest.ui.fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.mmm.mmmtest.R;
import com.mmm.mmmtest.ui.activity.MainActivity;
import com.mmm.mmmtest.util.NotificationPublisher;

import java.util.Calendar;
import java.util.Random;

public class NotificationFragment extends Fragment {

    public static final String NOTIFICATION_FRAGMENT_KEY = "notification_fragment";
    public static final String SENDING_NOTIFICATIONS = "sending_notifications";


    private Random rand = new Random();

    //PendingIntent notificationPendingIntent;
    private AlarmManager alarmManager;

    public NotificationFragment(){
    }

    CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(compoundButton.isChecked()) {
                /*new AlertDialog.Builder(getActivity())
                        .setTitle("Alert")
                        .setMessage("This is just a test alert")
                        .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();*/
                broadcastNotification(getNotification("MMMTest", "Open to see what happens"), 10000L, true);
                getContext().getSharedPreferences(
                        getString(R.string.shared_preferences_file_key), Context.MODE_PRIVATE).edit().putBoolean(SENDING_NOTIFICATIONS, true).apply();
            } else{
                alarmManager.cancel(getNotificationPublisherPendingIntent(null));
                getContext().getSharedPreferences(
                        getString(R.string.shared_preferences_file_key), Context.MODE_PRIVATE).edit().remove(SENDING_NOTIFICATIONS).apply();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View notificationFragmentLayout = inflater.inflate(R.layout.fragment_notification, container, false);

        alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        Switch notificationSwitch = (Switch)notificationFragmentLayout.findViewById(R.id.swNotification);

        if(getContext().getSharedPreferences(
                getString(R.string.shared_preferences_file_key), Context.MODE_PRIVATE).getBoolean(SENDING_NOTIFICATIONS, false))
            notificationSwitch.setChecked(true);

        notificationSwitch.setOnCheckedChangeListener(switchListener);

        return notificationFragmentLayout;
    }


    private void broadcastNotification(Notification not, long interval, boolean loop){

        PendingIntent notificationPublisherPendingIntent = getNotificationPublisherPendingIntent(not);

        //USE RTC SINCE THAT'S WHAT SYSTEM.CURRENT TIME MILLS RETURNS THE UTC TIME, ELAPSED TIME USES THE DEVICE TIME, NOT SURE THOU
        if(loop)
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + interval, interval, notificationPublisherPendingIntent);
        else
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + interval, notificationPublisherPendingIntent);

    }

    private PendingIntent getNotificationPublisherPendingIntent(Notification not){
        Intent notIn = new Intent(getContext(), NotificationPublisher.class);
        notIn.putExtra(NotificationPublisher.NOTIFICATION_KEY, not);
        notIn.putExtra(NotificationPublisher.NOTIFICATION_ID_KEY, rand.nextInt(100000));
        //notIn.putExtra(NotificationPublisher.NOTIFICATION_LOOP_KEY, loop);
        //NEEDS UPDATE CURRENT FLAG IN ORDER TO SET THE PENDING INTENT EXTRAS FROM THE INTENT
        return PendingIntent.getBroadcast(getContext(), 0, notIn, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private Notification getNotification(String title, String body){

        Intent in = new Intent(getContext(), MainActivity.class);
        in.putExtra(NOTIFICATION_FRAGMENT_KEY, true);

        //SHOULD SET REQUEST CODE TO A VALUE OTHER THAN 0 MAYBE RAND TOO IF WE DID NOT WANT THE NOTIFICATION TO BE OVERWRITTEN IF IT ALREADY EXISTS BUT RATHER STACK
        PendingIntent pendIn = PendingIntent.getActivity(getContext(), 0, in, 0);
        return new NotificationCompat.Builder(getContext())
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_app)
                .setWhen(Calendar.getInstance().getTimeInMillis())
                .setContentIntent(pendIn)
                .setAutoCancel(true)
                .build();
    }
}
