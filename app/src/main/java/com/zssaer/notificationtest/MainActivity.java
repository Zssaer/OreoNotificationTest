package com.zssaer.notificationtest;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
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
    private Button Media;
    private Button Vibrate;
    private Button LED;
    private Button Text;
    private Button Picture;
    private NotificationManager manager;
    private NotificationChannel notificationChannel1;
    private NotificationChannel notificationChannel2;
    private NotificationChannel notificationChannel3;
    private NotificationChannel notificationChannel4;
    private NotificationChannel notificationChannel5;
    private android.app.Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建NOtificationManage管理所有通知
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //判断安卓版本是否为8.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            creatAllChannel();
        }
        Hight = (Button) findViewById(R.id.button1);
        Hight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hightImportanceTest(v);
                //使用NotificationManage.notify()方法来显示通知
                manager.notify(1, notification);
            }
        });
        Default = (Button) findViewById(R.id.button2);
        Default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTest(v);
                //使用NotificationManage.notify()方法来显示通知
                manager.notify(2, notification);
            }
        });
        Low = (Button) findViewById(R.id.button3);
        Low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowImportanceTest(v);
                //使用NotificationManage.notify()方法来显示通知
                manager.notify(3, notification);
            }
        });
        Media = (Button) findViewById(R.id.button4);
        Media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withMediaTest(v);
                //使用NotificationManage.notify()方法来显示通知
                manager.notify(4, notification);
            }
        });
        Vibrate = (Button) findViewById(R.id.button5);
        Vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withVibrateTest(v);
                //使用NotificationManage.notify()方法来显示通知
                manager.notify(5, notification);
            }
        });
        LED = (Button) findViewById(R.id.button6);
        LED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withLEDTest(v);
                //使用NotificationManage.notify()方法来显示通知
                manager.notify(6, notification);
                Toast.makeText(MainActivity.this, "It is only lighting when the screen is off. ", Toast.LENGTH_LONG).show();
            }
        });
        Text = (Button) findViewById(R.id.button7);
        Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LongTextTest(v);
                //使用NotificationManage.notify()方法来显示通知
                manager.notify(7, notification);
            }
        });
        Picture = (Button) findViewById(R.id.button8);
        Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigPictureTest(v);
                //使用NotificationManage.notify()方法来显示通知
                manager.notify(8, notification);
            }
        });
    }

    
    /**
     * 注册所有通知通道
     */
    private void creatAllChannel() {
        String channelId = "Hight";
        String channelName = "重要性消息测试";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        //为其创建通知频道
        createNotificationChannel(channelId, channelName, importance);

        channelId = "Default";
        channelName = "一般性消息测试";
        importance = NotificationManager.IMPORTANCE_DEFAULT;
        createNotificationChannel(channelId, channelName, importance);

        channelId = "Low";
        channelName = "低重要性消息测试";
        importance = NotificationManager.IMPORTANCE_LOW;
        createNotificationChannel(channelId, channelName, importance);

        channelId = "Media";
        channelName = "自定义音频消息测试";
        importance = NotificationManager.IMPORTANCE_DEFAULT;
        createNotificationChannel(channelId, channelName, importance);

        channelId = "Vibrate";
        channelName = "震动消息测试";
        importance = NotificationManager.IMPORTANCE_DEFAULT;
        createNotificationChannel(channelId, channelName, importance);

        channelId = "LED";
        channelName = "前置LED消息测试";
        importance = NotificationManager.IMPORTANCE_DEFAULT;
        createNotificationChannel(channelId, channelName, importance);

    }

    /**
     * 注册单个通知通道
     *
     * @param id
     * @param name
     * @param importance
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(String id, String name, int importance) {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        if (id.equals("Media")) {
            //该通道下设置自定义音频
            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.media), android.app.Notification.AUDIO_ATTRIBUTES_DEFAULT);
        }
        if (id.equals("Vibrate")) {
            //该通道下开启震动
            channel.enableVibration(true);
            //设置震动时长(单位：毫秒)
            channel.setVibrationPattern(new long[]{0, 1000, 1000, 1000});
            channel.setSound(null, android.app.Notification.AUDIO_ATTRIBUTES_DEFAULT);
        }
        if (id.equals("LED")) {
            channel.setSound(null, android.app.Notification.AUDIO_ATTRIBUTES_DEFAULT);
            //该通道在熄屏下显示前置LED灯
            channel.enableLights(true);
            //设置LED颜色
            channel.setLightColor(Color.RED);
            channel.shouldShowLights();
        }
        manager.createNotificationChannel(channel);
    }

    /**
     * 高重要性通知发送
     *
     * @param v
     */
    private void hightImportanceTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel1 = manager.getNotificationChannel("Hight");
        //判断用户是否取消该通知通道显示
        if (notificationChannel1.getImportance() == manager.IMPORTANCE_NONE) {
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel1.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        } else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent = new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            notification = new NotificationCompat.Builder(v.getContext(), "Hight")
                    .setContentTitle("Hight notification")
                    .setContentText("This is hight importance Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();

        }
    }

    /**
     * 一般通知发送
     *
     * @param v
     */
    private void defaultTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel2 = manager.getNotificationChannel("Default");
        //判断用户是否取消该通知通道显示
        if (notificationChannel2.getImportance() == manager.IMPORTANCE_NONE) {
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel2.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        } else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent = new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            notification = new NotificationCompat.Builder(v.getContext(), "Default")
                    .setContentTitle("Default notification")
                    .setContentText("This is default Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
        }
    }

    /**
     * 低重要性通知发送
     *
     * @param v
     */
    private void lowImportanceTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel3 = manager.getNotificationChannel("Low");
        //判断用户是否取消该通知通道显示
        if (notificationChannel3.getImportance() == manager.IMPORTANCE_NONE) {
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel3.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        } else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent = new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            notification = new NotificationCompat.Builder(v.getContext(), "Low")
                    .setContentTitle("Low notification")
                    .setContentText("This is low importance Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
        }
    }

    /**
     * 带有自定义音频的通知发送
     *
     * @param v
     */
    private void withMediaTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel4 = manager.getNotificationChannel("Media");
        //判断用户是否取消该通知通道显示
        if (notificationChannel4.getImportance() == manager.IMPORTANCE_NONE) {
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel3.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        } else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent = new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            notification = new NotificationCompat.Builder(v.getContext(), "Media")
                    .setContentTitle("With media notification")
                    .setContentText("This is media Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
        }
    }

    /**
     * 带有自定义音频的通知发送
     *
     * @param v
     */
    private void withVibrateTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel5 = manager.getNotificationChannel("Vibrate");
        //判断用户是否取消该通知通道显示
        if (notificationChannel5.getImportance() == manager.IMPORTANCE_NONE) {
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel3.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        } else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent = new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            notification = new NotificationCompat.Builder(v.getContext(), "Vibrate")
                    .setContentTitle("With vibrate notification")
                    .setContentText("This is vibrate Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
        }
    }

    /**
     * 带有前置LED的通知发送
     *
     * @param v
     */
    private void withLEDTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel5 = manager.getNotificationChannel("LED");
        //判断用户是否取消该通知通道显示
        if (notificationChannel5.getImportance() == manager.IMPORTANCE_NONE) {
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel3.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        } else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent = new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            notification = new NotificationCompat.Builder(v.getContext(), "LED")
                    .setContentTitle("With LED notification")
                    .setContentText("This is LED Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setLights(Color.RED, 1000, 1000)
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
        }
    }

    /**
     * 长文本通知发送
     *
     * @param v
     */
    private void LongTextTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel2 = manager.getNotificationChannel("Default");
        //判断用户是否取消该通知通道显示
        if (notificationChannel2.getImportance() == manager.IMPORTANCE_NONE) {
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel2.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        } else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent = new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            notification = new NotificationCompat.Builder(v.getContext(), "Default")
                    .setContentTitle("Long text notification")
                    .setContentText("This is Long text Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("This is very very very very very very very very very very very very very very very very very very very " +
                            "very very very very very very very very very very very very very very very very very very very very very very very very very very very very very" +
                            " very very very very very very very very very very very very very very very very very very very very very very very very" +
                            " very very very very very very very very very very very very very very very very very very very very very very very very long text"))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
        }
    }

    /**
     * 大图片通知发送
     *
     * @param v
     */
    private void bigPictureTest(View v) {
        //利用NotificationManage.getNotificationChannel方法获取相关通知通道
        notificationChannel2 = manager.getNotificationChannel("Default");
        //判断用户是否取消该通知通道显示
        if (notificationChannel2.getImportance() == manager.IMPORTANCE_NONE) {
            //创建Intent跳转到设置该应用的通知管理内
            Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent1.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel2.getId());
            startActivity(intent1);
            Toast.makeText(MainActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        } else {
            //创建Intent 跳转到相应Application-Layout中
            Intent intent = new Intent(MainActivity.this, Notification.class);
            //使用PendingIntent来绑定对应Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            //利用NotificationCompat.Buillder()方法创建通知并传入对应通道
            notification = new NotificationCompat.Builder(v.getContext(), "Default")
                    .setContentTitle("Big picture notification")
                    .setContentText("This is Big picture Notification")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    //设置大图片显示
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.bp)))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    //绑定PendingIntent
                    .setContentIntent(pendingIntent)
                    .build();
        }
    }
}
