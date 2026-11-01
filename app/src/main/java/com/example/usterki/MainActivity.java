package com.example.usterki;


import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etLocation, etDescription, etEmail;
    Spinner spCategory;
    RadioGroup rgPriority;
    CheckBox cbContact;
    Switch swNotifications;
    SeekBar sbUrgency;
    Button btnSubmit, btnClear;
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
        swNotifications = findViewById(R.id.swNotifications);
        sbUrgency = findViewById(R.id.sbUrgency);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);
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
                confirmReport();
            }
        });

        btnClear.setOnClickListener(v -> confirmClear());
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

        String description =
                etDescription.getText().toString().trim();

        if (description.isEmpty()) {
            etDescription.setError(
                    getString(R.string.error_description));
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

    private void confirmReport() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_title)
                .setMessage(R.string.confirm_message)
                .setPositiveButton(R.string.yes,
                        (dialog, which) -> sendReport())
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void sendReport() {

        tvStatus.setText(R.string.status_sent);

        Snackbar.make(
                        findViewById(R.id.main),
                        R.string.sent,
                        Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    tvStatus.setText("");
                })
                .show();
    }

    private void confirmClear() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.clear_title)
                .setMessage(R.string.clear_message)
                .setPositiveButton(R.string.clear_yes,
                        (dialog, which) -> clearForm())
                .setNegativeButton(R.string.clear_no, null)
                .show();
    }

    private void clearForm() {

        // Zapamiętanie poprzednich danych
        String oldTitle = etTitle.getText().toString();
        String oldLocation = etLocation.getText().toString();
        String oldDescription = etDescription.getText().toString();
        String oldEmail = etEmail.getText().toString();

        int oldCategory = spCategory.getSelectedItemPosition();
        int oldPriority = rgPriority.getCheckedRadioButtonId();
        int oldUrgency = sbUrgency.getProgress();

        boolean oldContact = cbContact.isChecked();
        boolean oldNotifications = swNotifications.isChecked();

        String oldStatus = tvStatus.getText().toString();

        CharSequence oldTitleError = etTitle.getError();
        CharSequence oldLocationError = etLocation.getError();
        CharSequence oldDescriptionError = etDescription.getError();
        CharSequence oldEmailError = etEmail.getError();

        // Wyczyszczenie formularza
        etTitle.setText("");
        etLocation.setText("");
        etDescription.setText("");
        etEmail.setText("");

        spCategory.setSelection(0);
        rgPriority.clearCheck();
        sbUrgency.setProgress(0);
        cbContact.setChecked(false);
        swNotifications.setChecked(false);

        etEmail.setEnabled(false);
        tvStatus.setText("");

        etTitle.setError(null);
        etLocation.setError(null);
        etDescription.setError(null);
        etEmail.setError(null);

        // Snackbar z przyciskiem Cofnij
        Snackbar.make(
                        findViewById(R.id.main),
                        R.string.cleared,
                        Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {

                    etTitle.setText(oldTitle);
                    etLocation.setText(oldLocation);
                    etDescription.setText(oldDescription);

                    spCategory.setSelection(oldCategory);

                    if (oldPriority == -1) {
                        rgPriority.clearCheck();
                    } else {
                        rgPriority.check(oldPriority);
                    }

                    sbUrgency.setProgress(oldUrgency);
                    cbContact.setChecked(oldContact);
                    swNotifications.setChecked(oldNotifications);

                    etEmail.setText(oldEmail);
                    etEmail.setEnabled(oldContact);

                    tvStatus.setText(oldStatus);

                    etTitle.setError(oldTitleError);
                    etLocation.setError(oldLocationError);
                    etDescription.setError(oldDescriptionError);
                    etEmail.setError(oldEmailError);
                })
                .show();
    }
}
