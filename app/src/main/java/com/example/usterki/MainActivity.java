package com.example.usterki;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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

    }
}
