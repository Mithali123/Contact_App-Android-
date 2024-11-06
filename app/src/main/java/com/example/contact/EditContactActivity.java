package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class EditContactActivity extends AppCompatActivity {
    private TextInputEditText editTextFullName, editTextPhone, editTextEmail;
    private Button saveButton;
    private ImageButton backButton;

    private DatabaseHandler databaseHandler;
    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        // Initialize views
        editTextFullName = findViewById(R.id.edit_text_full_name);
        editTextPhone = findViewById(R.id.edit_text_phone);
        editTextEmail = findViewById(R.id.edit_text_email);
        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);

        // Initialize database handler
        databaseHandler = new DatabaseHandler(this);

        // Get contact ID from intent
        Intent intent = getIntent();
        contactId = intent.getIntExtra("ID", -1);

        loadContactDetails(contactId);

        // Set up button click listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity
            }
        });
    }

    private void loadContactDetails(int id) {
        Contact contact = databaseHandler.getContact(id);
        if (contact != null) {
            editTextFullName.setText(contact.getName());
            editTextPhone.setText(contact.getPhoneNumber());
            editTextEmail.setText(contact.getEmail());
        }
    }

    private void updateContact() {
        String name = editTextFullName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new contact object with updated data
        Contact contact = new Contact(contactId, name, phone, email); // Assuming default values for favorite and blocked

        // Update the contact in the database
        int result = databaseHandler.updateContact(contact);

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("ID", contactId); // Pass back the contact ID
        startActivity(i);
    }

    // Removed the menu button and the popup menu method
}
