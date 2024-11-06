package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter contactAdapter;
    private List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideSystemUI();

        RecyclerView recyclerView = findViewById(R.id.contact_recycler_view);
        EditText searchBar = findViewById(R.id.search_bar);

        DatabaseHandler db = new DatabaseHandler(this);
        contacts = db.getAllContacts();
//        db.addContact(new Contact("Ravi", "7903560929","ravi@gmail.com"));
//        db.addContact(new Contact("Aditya", "1234567890","adi@gmail.com"));
//        db.addContact(new Contact("Sridevi", "9876543210","sridevi@gmail.com"));
//        db.addCallLog(new CallLog("Ravi", "9876543210", "Thu", "17:55", "17/10/24", "O", "108"));
//        db.addCallLog(new CallLog("Aditya", "1234567890", "Sat", "01:29", "07/01/24", "O", "18"));
//        db.addCallLog(new CallLog("Sridevi", "9876543210", "Sun", "03:59", "13/09/24", "O", "118"));
//        db.addCallLog(new CallLog("Unknown", "0000000000", "Fri", "17:55", "17/10/24", "O", "108"));
//        db.addCallLog(new CallLog("Unknown", "9999999999", "Thu", "18:01", "13/09/24", "O", "48"));
        contactAdapter = new ContactAdapter(this, contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterContacts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadContacts() {
        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> latestContacts = db.getAllContacts();
        Collections.sort(latestContacts, Comparator.comparing(Contact::getName));
        contacts.clear();
        contacts.addAll(latestContacts);
        contactAdapter.notifyDataSetChanged();
    }

    private void filterContacts(String query) {
        List<Contact> filteredContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredContacts.add(contact);
            }
        }
        contactAdapter.updateContacts(filteredContacts);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadContacts();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

        public void ToDialerPage(View view) {
            Intent i = new Intent(getApplicationContext(), DialerActivity.class);
            startActivity(i);
        }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
        hideSystemUI();
    }
}