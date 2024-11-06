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
import android.widget.Toast;

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
//        db.addContact(new Contact("Alice", "1234567890", "alice@example.com"));
//        db.addContact(new Contact("Bob", "2345678901", "bob@example.com"));
//        db.addContact(new Contact("Charlie", "3456789012", "charlie@example.com"));
//        db.addContact(new Contact("David", "4567890123", "david@example.com"));
//        db.addContact(new Contact("Eve", "5678901234", "eve@example.com"));
//        db.addContact(new Contact("Frank", "6789012345", "frank@example.com"));
//        db.addContact(new Contact("Grace", "7890123456", "grace@example.com"));
//        db.addContact(new Contact("Hannah", "8901234567", "hannah@example.com"));
//        db.addContact(new Contact("Ivan", "9012345678", "ivan@example.com"));
//        db.addContact(new Contact("Jack", "0123456789", "jack@example.com"));
//        db.addContact(new Contact("Karen", "9876543211", "karen@example.com"));
//        db.addContact(new Contact("Liam", "8765432109", "liam@example.com"));
//        db.addContact(new Contact("Mia", "7654321098", "mia@example.com"));
//        db.addContact(new Contact("Noah", "6543210987", "noah@example.com"));
//        db.addContact(new Contact("Olivia", "5432109876", "olivia@example.com"));
//        db.addContact(new Contact("Paul", "4321098765", "paul@example.com"));
//        db.addContact(new Contact("Quinn", "3210987654", "quinn@example.com"));
//        db.addContact(new Contact("Rita", "2109876543", "rita@example.com"));
//        db.addContact(new Contact("Sam", "1098765432", "sam@example.com"));
//        db.addContact(new Contact("Tina", "0987654321", "tina@example.com"));
//        db.addContact(new Contact("Uma", "1112223333", "uma@example.com"));
//        db.addContact(new Contact("Vera", "2223334444", "vera@example.com"));
//        db.addContact(new Contact("Walter", "3334445555", "walter@example.com"));
//        db.addContact(new Contact("Xena", "4445556666", "xena@example.com"));
//        db.addContact(new Contact("Yash", "5556667777", "yash@example.com"));
//        db.addContact(new Contact("Zoe", "6667778888", "zoe@example.com"));
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

    public void toMainPage(View view) {
        Toast.makeText(MainActivity.this, "Already in Contacts page", Toast.LENGTH_SHORT).show();
    }

    public void toRecentsPage(View view) {
        Intent i = new Intent(MainActivity.this, RecentActivity.class);
        startActivity(i);
    }
}