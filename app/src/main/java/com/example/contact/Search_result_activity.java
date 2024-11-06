package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView; // Import TextView
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Search_result_activity extends AppCompatActivity {

    Button recent,contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        hideSystemUI();
        recent=findViewById(R.id.btn_recents);
        contact=findViewById(R.id.btn_contacts);

        ImageView backButton = findViewById(R.id.back_button1);
        String query = getIntent().getStringExtra("query");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will close the current activity and go back
                onBackPressed();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a Toast message
                Toast.makeText(Search_result_activity.this, "contact button clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        recent.setOnClickListener(v -> {
            // Create an Intent to launch the settings activity
            Intent intent = new Intent(Search_result_activity.this, MainActivity.class);
            startActivity(intent);  // Launch the new activity
        });



        DatabaseHandler db = new DatabaseHandler(this);
        List<item> callLogItems = new ArrayList<>();

        List<CallLog> callLogs = db.searchCallLogs(query);  // Correctly accessing the instance method

        // Loop through each call log and create an `item` object
        for (CallLog log : callLogs) {
            String name = log.getName();
            String initial;
            if (name == null || name.equals("NULL") || name.trim().isEmpty()) {
                name = "Unknown";
                initial = "U";  // Since it's 'Unknown', we should set 'U'
            } else {
                initial = name.substring(0, 1).toUpperCase();  // Fetch the first letter from name
            }
            String phone = log.getPhoneNumber(); // Fetch phone number
            String date = log.getCallDate(); // Fetch call date
            String duration = log.getDuration(); // Fetch call duration
            String day = log.getCallDay(); // Fetch call day
            String time = log.getCallTime(); // Fetch call time

            // Create a new `item` object
            item callLogItem = new item(name, phone, initial, date, duration, day, time);

            // Add the `item` object to the list
            callLogItems.add(callLogItem);
        }

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler);
        TextView noResultsText = findViewById(R.id.no_results_text); // Initialize the TextView

        if (callLogItems.isEmpty()) {
            // If no results found
            recyclerView.setVisibility(View.GONE); // Hide RecyclerView
            noResultsText.setVisibility(View.VISIBLE); // Show no results text
        } else {
            // If results are found
            recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
            noResultsText.setVisibility(View.GONE); // Hide no results text

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Set up the adapter with fetched call logs
            MyAdapter myAdapter = new MyAdapter(this, callLogItems);
            recyclerView.setAdapter(myAdapter);
        }
    }
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
