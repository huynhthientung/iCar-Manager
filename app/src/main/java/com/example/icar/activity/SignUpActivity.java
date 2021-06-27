package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.icar.MainActivity;
import com.example.icar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.jetbrains.annotations.NotNull;

public class SignUpActivity extends AppCompatActivity {

    private final String KEY = "name";

    private FirebaseAuth mAuth;
    private EditText edtName, edtEmail, edtPassword;
    private FloatingActionButton btnEnter;
    private CheckBox checkBox;
    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        edtName = findViewById(R.id.editTextName);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        btnEnter = findViewById(R.id.floatingButtonEnter);
        checkBox = findViewById(R.id.checkboxShowPassword);
        progressBar = findViewById(R.id.progressBar);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateNewUser();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
            }
        });


    }

    private void onCreateNewUser() {
        progressBar.setVisibility(View.VISIBLE);
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String name = edtName.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileChangeRequest;
                            profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    Log.d("AAA", "User's profile updated");
                                }
                            });

                            Toast.makeText(SignUpActivity.this, "Successfully sign-up", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, UpdateProfileActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(KEY, name);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignUpActivity.this, "" + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void OpenSigninPage(View view) {
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
    }

}