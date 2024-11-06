package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecentActivity extends AppCompatActivity {

//    ImageView setting;
    Button recent,contact;
    EditText search_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        hideSystemUI();

//        setting=findViewById(R.id.setting1);
        recent=findViewById(R.id.btn_recents);
        contact=findViewById(R.id.btn_contacts);
        search_contact=findViewById(R.id.search_text);
//
//        setting.setOnClickListener(v -> {
//            // Create an Intent to launch the settings activity
//            Intent intent = new Intent(RecentActivity.this, SettingActivity.class);
//            startActivity(intent);  // Launch the new activity
//        });

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a Toast message
                Toast.makeText(RecentActivity.this, "Already in recents page", Toast.LENGTH_SHORT).show();
            }
        });


        // Initialize DatabaseHandler
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        DatabaseHandler db = new DatabaseHandler(this);

//                db.addCallLog(new CallLog("Ravi", "9876543210", "Thu", "17:55", "17/10/24", "O", "108"));
//        db.addCallLog(new CallLog("Aditya", "7896541230", "Sat", "01:29", "07/01/24", "O", "18"));
//        db.addCallLog(new CallLog("Sridevi", "7896351530", "Sun", "03:59", "13/09/24", "O", "118"));
//        db.addCallLog(new CallLog("Adhvi", "9905050500", "Fri", "17:55", "17/10/24", "O", "108"));
//        db.addCallLog(new CallLog("Mithali", "7325489120", "Thu", "18:01", "13/09/24", "O", "48"));
//        db.addCallLog(new CallLog("ria", "7888896230", "Sun", "02:45", "16/09/24", "O", "38"));
//        db.addCallLog(new CallLog("harsh", "7778544560", "Tue", "2345", "16/09/24", "O", "10"));
//        db.addCallLog(new CallLog("apoorva", "9165401230", "Thu", "1703", "15/10/24", "O", "16"));
//        db.addCallLog(new CallLog("shiv", "3657954230", "Sat", "0559", "08/10/24", "O", "126"));
//        db.addCallLog(new CallLog("krish", "9004543210", "Thu", "0001", "03/10/23", "O", "26"));
//        db.addCallLog(new CallLog("aniket", "8877543210", "Sun", "2312", "23/10/24", "O", "550"));
//        db.addCallLog(new CallLog("priya", "9855556310", "Sat", "1335", "19/10/24", "O", "456"));
//        db.addCallLog(new CallLog("hrishav", "7896554321", "Thu", "0945", "19/10/24", "O", "756"));
//        db.addCallLog(new CallLog("priyanka", "1116543210", "Fri", "1011", "25/10/23", "O", "3"));
//        db.addCallLog(new CallLog("rishi", "3657954230", "Mon", "1039", "30/10/24", "O", "77"));
//        db.addCallLog(new CallLog("ritesh", "6549494665", "Sat", "0597", "03/10/24", "O", "36"));
//        db.addCallLog(new CallLog("sagar", "9996512203", "Sun", "1534", "01/10/24", "O", "78"));

        // Create a list to store the fetched call logs
        List<item> callLogItems = new ArrayList<>();
//        callLogItems.add(new item("Ravi","7896545632","R","7Aug","5s","Mon","4:45"));
//        callLogItems.add(new item("raj","5693458756","R","7Aug","5s","Mon","4:45"));
//        callLogItems.add(new item("gupta","1236548963","G","7Aug","5s","Mon","4:45"));
//        callLogItems.add(new item("aashish","6987456321","A","7Aug","5s","Mon","4:45"));
//        callLogItems.add(new item("aarav","7896541222","A","7Aug","5s","Mon","4:45"));
//        callLogItems.add(new item("prithvi","3569756214","P","7Aug","5s","Mon","4:45"));

        // Fetch all call logs from the database
        List<CallLog> callLogs = dbHandler.getAllCallLogs();  // Correctly accessing the instance method

        // Loop through each call log and create an `item` object
        for (CallLog log : callLogs) {
            String name = log.getName();
            String initial;
            if (name == null || name == "NULL" || name.trim().isEmpty()) {
                name = "UNKNOWN";
                initial = "U";  // Since it's 'Unknown', we should set 'U'
            } else {
                initial = name.substring(0, 1).toUpperCase();  // Fetch the first letter from name
            }
            String phone = log.getPhoneNumber(); // Fetch phone number
            // Get initial letter from name
            String date = log.getCallDate(); // Fetch call date
            String duration = log.getDuration(); // Fetch call duration
            String day = log.getCallDay(); // Fetch call day
            String time = log.getCallTime(); // Fetch call time
            // String callType = log.getCallType(); // Fetch call type (if needed)

            // Create a new `item` object
            item callLogItem = new item(name, phone, initial, date, duration, day, time);

            // Add the `item` object to the list
            callLogItems.add(callLogItem);
        }

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the adapter with fetched call logs
        MyAdapter myAdapter = new MyAdapter(this, callLogItems);
        recyclerView.setAdapter(myAdapter);

        search_contact.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = search_contact.getText().toString().trim();
                if (!query.isEmpty()) {
                    Intent intent = new Intent(RecentActivity.this, Search_result_activity.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
                return true; // indicates the action was handled
            }
            return false;
        });

    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void toMainPage(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}

