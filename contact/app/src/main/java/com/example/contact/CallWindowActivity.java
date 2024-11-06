package com.example.contact;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class CallWindowActivity extends AppCompatActivity {
    TextView call_name, call_number, call_duration;
    private long startTime;
    private Handler handler = new Handler();
    private Runnable updateDurationRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_window);

        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");

        call_name = findViewById(R.id.contact_name);
        call_number = findViewById(R.id.contact_number);
        call_duration = findViewById(R.id.call_duration);
        call_name.setText(name != null ? name : "UNKNOWN");
        call_number.setText(phone);

        startTime = SystemClock.elapsedRealtime();

        // Start updating the duration display
        updateDurationRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = (SystemClock.elapsedRealtime() - startTime) / 1000;
                call_duration.setText(elapsedTime + " sec");
                handler.postDelayed(this, 1000); // Update every second
            }
        };
        handler.post(updateDurationRunnable);

        ImageView backButton = findViewById(R.id.end_call_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop updating duration
                handler.removeCallbacks(updateDurationRunnable);

                // Calculate final duration
                long endTime = SystemClock.elapsedRealtime();
                String callDuration = String.valueOf((endTime - startTime) / 1000);

                // Get date and time details
                Calendar calendar = Calendar.getInstance();
                String callDay = new SimpleDateFormat("EEEE").format(calendar.getTime());
                String callDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                String callTime = new SimpleDateFormat("HH:mm:ss").format(calendar.getTime());

                // Save call log with final duration
                DatabaseHandler db = new DatabaseHandler(CallWindowActivity.this);
                db.addCallLog(new CallLog(
                        name != null ? name : "UNKNOWN",
                        phone,
                        callDay,
                        callTime,
                        callDate,
                        "O",
                        callDuration // Final duration
                ));

                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateDurationRunnable); // Stop updating when activity is destroyed
    }
}
