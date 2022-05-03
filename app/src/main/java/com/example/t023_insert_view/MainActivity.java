package com.example.t023_insert_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText name , email , contact ;
    Spinner spinnerCity ;
    AutoCompleteTextView actvLang ;
    String lang[] = {"Android","Java","ABAP","Kotlin","C++"};

    Button btnAdduser ;
    ProgressBar progressBar ;
    ArrayAdapter arrayAdapter ;

    DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        btnAdduser = findViewById(R.id.btnAdduser);
        progressBar = findViewById(R.id.progressBar);
        spinnerCity = findViewById(R.id.spinnerCity);
        //Values in Spinner Taken Directly from Values Folder

        actvLang = findViewById(R.id.actvLang);
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1 , lang);
        actvLang.setAdapter(arrayAdapter);
        actvLang.setThreshold(1);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        btnAdduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NAME = name.getText().toString().trim();
                String EMAIL = email.getText().toString().trim();
                String CONTACT = contact.getText().toString().trim();
                String CITY = spinnerCity.getSelectedItem().toString().trim();
                String LANG = actvLang.getText().toString().trim();

                if (NAME.isEmpty()){
                    name.setError("Name Required");
                    name.requestFocus();
                    return;
                }

                if (EMAIL.isEmpty()){
                    email.setError("E-mail Required");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                    email.setError("Enter proper E-mail ID");
                    email.requestFocus();
                    return;
                }

                if (CONTACT.isEmpty()){
                    contact.setError("Contact Number Required");
                    contact.requestFocus();
                    return;
                }

                if (LANG.isEmpty()){
                    actvLang.setError("Language Required");
                    actvLang.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // To Insert Data
                String uniqId = databaseReference.push().getKey();
                User user = new User(uniqId , NAME , EMAIL , CONTACT , CITY , LANG);
                databaseReference.child(uniqId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            contact.setText("");
                            actvLang.setText("");
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }) ;

            }
        });

    }

    public void openUserActivity(View view) {
        startActivity(new Intent(MainActivity.this , UserActivity.class));
    }
}
