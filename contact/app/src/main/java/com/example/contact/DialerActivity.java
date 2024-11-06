package com.example.contact;

import android.annotation.SuppressLint;
import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DialerActivity extends AppCompatActivity {

    private EditText phoneNumber, searchBar;
    private ImageButton clearButton, callButton, settingsButton, addContactButton; // Add settingsButton here
    private Button contactsSection, recentsSection;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        phoneNumber = findViewById(R.id.phone_number);
        clearButton = findViewById(R.id.clear_button);
        callButton = findViewById(R.id.call_button); // Initialize callButton
        settingsButton = findViewById(R.id.settings_button); // Initialize settingsButton
        searchBar = findViewById(R.id.search_contacts);
        contactsSection = findViewById(R.id.contacts_button);
        recentsSection = findViewById(R.id.recents_button);
        addContactButton = findViewById(R.id.add_contact_button);
        setupDialPad();
        setupClearButton();
        setupSearchBar();
        setupCallButton(); // Call the method to set up call button
        setupSettingsButton(); // Call the method to set up settings button
        disableSoftKeyboard();
        setupAddContactButton();// Disable the device's soft keyboard
    }

    // Method to disable the device's soft keyboard for the EditText
    private void disableSoftKeyboard() {
        phoneNumber.setShowSoftInputOnFocus(false);  // Disable soft keyboard
    }

    private void setupDialPad() {
        // Set click listeners for each button
        int[] buttonIds = {
                R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9,
                R.id.button0, R.id.buttonStar, R.id.buttonHash
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                int start = phoneNumber.getSelectionStart();  // Get cursor position
                String currentText = phoneNumber.getText().toString();
                String newText = currentText.substring(0, start) + button.getText().toString() + currentText.substring(start);
                phoneNumber.setText(newText);
                phoneNumber.setSelection(start + 1);  // Move cursor after the inserted number
            }
        };

        // Assign listener to each button
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupClearButton() {
        // Clear the EditText when clear button is pressed (deleting single number)
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cursorPosition = phoneNumber.getSelectionStart();  // Get cursor position
                if (cursorPosition > 0) {
                    String currentText = phoneNumber.getText().toString();
                    // Remove the character at the cursor position
                    String newText = currentText.substring(0, cursorPosition - 1) + currentText.substring(cursorPosition);
                    phoneNumber.setText(newText);
                    phoneNumber.setSelection(cursorPosition - 1);  // Move cursor back by one
                }
            }
        });

        // Show or hide clear button based on input
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupSearchBar() {
        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Hide Contacts and Recents when the search bar is clicked
                    contactsSection.setVisibility(View.GONE);
                    recentsSection.setVisibility(View.GONE);

                    // Start RecentActivity when the search bar is clicked
                    Intent intent = new Intent(DialerActivity.this, RecentActivity.class);
                    startActivity(intent);
                } else {
                    // Show Contacts and Recents again when the search bar loses focus
                    contactsSection.setVisibility(View.VISIBLE);
                    recentsSection.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void setupCallButton() {
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneNumber.getText().toString().trim();

                // Check if the phone number is exactly 10 digits
                if (phone.length() == 10) {
                    Intent intent = new Intent(DialerActivity.this, CallWindowActivity.class);
                    intent.putExtra("phone", phone); // Pass phone number to CallWindowActivity
                    startActivity(intent);
                } else {
                    Toast.makeText(DialerActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupAddContactButton() {
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start AddContactActivity
                Intent intent = new Intent(DialerActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setupSettingsButton() {
        // Set the OnClickListener for settingsButton to start SettingsActivity
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SettingsActivity
                Intent intent = new Intent(DialerActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    // Optional: Add a backspace feature to remove the last digit (long-click option if needed)
    public void backspace(View view) {
        String currentText = phoneNumber.getText().toString();
        if (currentText.length() > 0) {
            phoneNumber.setText(currentText.substring(0, currentText.length() - 1));
        }
    }


    public void toRecentsPage(View view) {
        Intent i = new Intent(getApplicationContext(), RecentActivity.class);
        startActivity(i);
    }

    public void toMainPage(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
