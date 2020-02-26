package com.zssaer.notificationtest;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button Hight;
    private Button Default;
    private Button Low;
    private NotificationManager manager;
    private NotificationChannel notificationChannel1;
    private NotificationChannel notificationChannel2;
    private NotificationChannel notificationChannel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建NOtificationManage管理所有通知
        manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //判断安卓版本是否为8.0以上
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            creatAllChannel();
        }
        Hight=(Button)findViewById(R.id.button1);
        Hight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hightImportanceTest(v);
            }
        });
        Default=(Button)findViewById(R.id.button2);
        Default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTest(v);
            }
        });
        Low=(Button)findViewById(R.id.button3);
        Low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowImportanceTest(v);
            }
        });
    }

    /**
     * 注册所有通知通道
     */
    private void creatAllChannel() {
        String channelId="Hight";
        String channelName="重要性消息测试";
        int importance= NotificationManager.IMPORTANCE_HIGH;
        //为其创建通知频道
        createNotificationChannel(channelId,channelName,importance);

        channelId="Default";
        channelName="一般性消息测试";
        importance=NotificationManager.IMPORTANCE_DEFAULT;
        createNotificationChannel(channelId,channelName,importance);

        channelId="Low";
        channelName="低重要性消息测试";
        importance=NotificationManager.IMPORTANCE_LOW;
        createNotificationChannel(channelId,channelName,importance);
    }

    /**
     * 注册单个通知通道
     * @param id
     * @param name
     * @param importance
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(String id,String name,int importance){
        NotificationChannel channel=new NotificationChannel(id,name,importance);
        manager.createNotificationChannel(channel);
    }

    /**
     * 高重要性通知发送
     * @param v
     */
    private void hightImportanceTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel1=manager.getNotificationChannel("Hight");
        //判断用户是否取消该通知通道显示
        if (notificationChannel1.getImportance()==manager.IMPORTANCE_NONE){
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1=new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE,getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID,notificationChannel1.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        }else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent=new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            android.app.Notification notificatio = new NotificationCompat.Builder(v.getContext(), "Hight")
                    .setContentTitle("Hight notification")
                    .setContentText("This is hight importance Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
            //使用NotificationManage.notify()方法来显示通知
            manager.notify(1, notificatio);
        }
    }

    /**
     * 一般通知发送
     * @param v
     */
    private void defaultTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel2=manager.getNotificationChannel("Default");
        //判断用户是否取消该通知通道显示
        if (notificationChannel2.getImportance()==manager.IMPORTANCE_NONE){
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1=new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE,getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID,notificationChannel2.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        }else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent=new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            android.app.Notification notificatio = new NotificationCompat.Builder(v.getContext(), "Default")
                    .setContentTitle("Default notification")
                    .setContentText("This is default Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
            //使用NotificationManage.notify()方法来显示通知
            manager.notify(2, notificatio);
        }
    }

    /**
     * 低重要性通知发送
     * @param v
     */
    private void lowImportanceTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel3=manager.getNotificationChannel("Low");
        //判断用户是否取消该通知通道显示
        if (notificationChannel3.getImportance()==manager.IMPORTANCE_NONE){
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1=new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE,getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID,notificationChannel3.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        }else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent=new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            android.app.Notification notificatio = new NotificationCompat.Builder(v.getContext(), "Low")
                    .setContentTitle("Low notification")
                    .setContentText("This is low importance Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
            //使用NotificationManage.notify()方法来显示通知
            manager.notify(3, notificatio);
        }
    }




}
