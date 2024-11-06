package com.example.contact;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        hideSystemUI();

        TextView profilePictureContainer = findViewById(R.id.contactPicture);
        TextView contactNameContainer = findViewById(R.id.contactName);
        TextView contactNumberContainer = findViewById(R.id.contactNumber);
        TextView emailAddressContainer = findViewById(R.id.emailAddress);

        ImageButton backButton = findViewById(R.id.backBtn);
        ImageButton editButton = findViewById(R.id.editBtn);
        ImageButton favoriteButton = findViewById(R.id.favBtn);
        ImageButton deleteButton = findViewById(R.id.deleteBtn);
        ImageButton callButton = findViewById(R.id.callBtn);
        Button RecentPageBtn = findViewById(R.id.toRecents);

        Intent contactData = getIntent();
        String idString = contactData.getStringExtra("ID");
        if (idString == null) {
            Toast.makeText(this, "Contact ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        int contactId = Integer.parseInt(idString);

        DatabaseHandler db = new DatabaseHandler(this);
        contact = db.getContact(contactId);  // Fetch contact from the database

        if (contact == null) {
            Toast.makeText(this, "Contact not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        contactNameContainer.setText(contact.getName());
        if(contact.getFavorite() == 1) favoriteButton.setImageResource(R.drawable.star_filled);
        else favoriteButton.setImageResource(R.drawable.star);
        contactNumberContainer.setText(contact.getPhoneNumber());
        emailAddressContainer.setText(contact.getEmail());

        String profilePicture = String.valueOf(contact.getName().charAt(0));
        profilePictureContainer.setText(profilePicture);

        //Favorite Button
        favoriteButton.setOnClickListener(v -> {
            if(contact.getFavorite() == 1) {
                db.updateContact(new Contact(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), 0, contact.getBlocked()));
                favoriteButton.setImageResource(R.drawable.star);
                Toast.makeText(this, "Contact Removed from Favorites", Toast.LENGTH_SHORT).show();
            }
            else{
                db.updateContact(new Contact(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), 1, contact.getBlocked()));
                favoriteButton.setImageResource(R.drawable.star_filled);
                Toast.makeText(this, "Contact Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        });

        // Edit contact button
        // Edit contact button
        editButton.setOnClickListener(v -> {
            Intent i = new Intent(ProfileActivity.this, EditContactActivity.class);
            i.putExtra("ID", contact.getId());  // Key should match with EditContactActivity
            startActivity(i);
        });


        // Delete contact button
        deleteButton.setOnClickListener(v -> {
            db.deleteContact(contact.getId());
            Toast.makeText(ProfileActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
            finish();
        });

        callButton.setOnClickListener(v -> {
            Intent i = new Intent(ProfileActivity.this, CallWindowActivity.class);
            i.putExtra("name", String.valueOf(contact.getName()));
            i.putExtra("phone", String.valueOf(contact.getPhoneNumber()));
            startActivity(i);
        });

        RecentPageBtn.setOnClickListener(v -> {
            Intent i = new Intent(ProfileActivity.this, RecentActivity.class);
            startActivity(i);
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

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI(); // Ensure the UI stays hidden on resume
    }


}
