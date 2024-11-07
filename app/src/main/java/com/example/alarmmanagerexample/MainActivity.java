package com.example.alarmmanagerexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alarmmanagerexample.broadcast_receiver.MyReceiver;
import com.example.alarmmanagerexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static final int ALARM_REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        binding.btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int time = Integer.parseInt(binding.edtTime.getText().toString());
                long triggerTime = System.currentTimeMillis()+(time * 1000L);

                Intent intentBroadCast = new Intent(MainActivity.this, MyReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,ALARM_REQ_CODE, intentBroadCast,PendingIntent.FLAG_MUTABLE);

                alarmManager.set(AlarmManager.RTC_WAKEUP,triggerTime,pendingIntent);
                Toast.makeText(MainActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();
            }
        });


    }
}