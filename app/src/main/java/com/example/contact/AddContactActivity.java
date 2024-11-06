package com.example.contact;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class AddContactActivity extends AppCompatActivity {

    private TextInputEditText fullNameEditText, birthdayEditText, emailEditText, phoneEditText;
    private TextView circularTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Initialize views
        fullNameEditText = findViewById(R.id.edit_text_full_name);
        birthdayEditText = findViewById(R.id.edit_text_birthday);
        emailEditText = findViewById(R.id.edit_text_email);
        phoneEditText = findViewById(R.id.edit_text_phone);
        circularTextView = findViewById(R.id.text_view_placeholder); // Circular TextView for first letter

        // Set click listener on birthdayEditText to open the DatePicker dialog
        birthdayEditText.setOnClickListener(v -> openDatePicker());

        // Set click listener on Save button to validate and proceed
        findViewById(R.id.save_button).setOnClickListener(v -> {
            if (validateInput()) {
                saveProfileData();
            }
        });
    }

    // Method to open the date picker dialog and select a date
    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) ->
                        birthdayEditText.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                year, month, day);

        datePickerDialog.show();
    }

    // Method to validate email and phone number
    private boolean validateInput() {
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String fullName = fullNameEditText.getText().toString().trim();

        // Email validation
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            emailEditText.requestFocus();
            return false;
        }

        // Phone number validation
        if (TextUtils.isEmpty(phone) || !phone.matches("\\d{10}")) {
            phoneEditText.setError("Enter a valid 10-digit phone number");
            phoneEditText.requestFocus();
            return false;
        }

        // Check for full name
        if (TextUtils.isEmpty(fullName)) {
            fullNameEditText.setError("Enter your full name");
            fullNameEditText.requestFocus();
            return false;
        }

        return true; // Input is valid
    }

    public void toMainPage(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    // Method to save the profile data and display the first letter of the full name
    private void saveProfileData() {
        String fullName = fullNameEditText.getText().toString().trim();
        String phoneNumber = phoneEditText.getText().toString();
        String emailAddress = emailEditText.getText().toString();

        DatabaseHandler db = new DatabaseHandler(this);
        db.addContact(new Contact(fullName, phoneNumber,emailAddress));

        Toast.makeText(this, "Profile data saved!", Toast.LENGTH_SHORT).show();
        // Code to save the profile data (e.g., to Firebase or local database) can go here
    }
}