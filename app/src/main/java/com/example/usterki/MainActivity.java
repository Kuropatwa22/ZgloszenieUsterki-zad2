package com.example.usterki;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etLocation, etDescription, etEmail;
    Spinner spCategory;
    RadioGroup rgPriority;
    CheckBox cbContact;
    Button btnSubmit;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);
        etEmail = findViewById(R.id.etEmail);

        spCategory = findViewById(R.id.spCategory);
        rgPriority = findViewById(R.id.rgPriority);
        cbContact = findViewById(R.id.cbContact);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvStatus = findViewById(R.id.tvStatus);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.categories,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spCategory.setAdapter(adapter);

        cbContact.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    etEmail.setEnabled(isChecked);

                    if (!isChecked) {
                        etEmail.setText("");
                    }
                });

        btnSubmit.setOnClickListener(v -> {
            if (validateForm()) {
                tvStatus.setText(R.string.status_sent);
            }
        });


    }

    private boolean validateForm() {

        String title = etTitle.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError(getString(R.string.error_title));
            etTitle.requestFocus();
            return false;
        }

        if (spCategory.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.error_category,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (rgPriority.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.error_priority,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        String location = etLocation.getText().toString().trim();

        if (location.isEmpty()) {
            etLocation.setError(getString(R.string.error_location));
            etLocation.requestFocus();
            return false;
        }

        String description = etDescription.getText().toString().trim();

        if (description.isEmpty()) {
            etDescription.setError(getString(R.string.error_description));
            etDescription.requestFocus();
            return false;
        }

        if (cbContact.isChecked()) {

            String email = etEmail.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError(getString(R.string.error_email));
                etEmail.requestFocus();
                return false;
            }
        }

        return true;
    }

}
