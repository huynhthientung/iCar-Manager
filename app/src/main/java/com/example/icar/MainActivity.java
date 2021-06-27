package com.example.icar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.icar.activity.ForgetPasswordActivity;
import com.example.icar.activity.HomeActivity;
import com.example.icar.activity.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_INTERVAL = 2000;
    private long backPressed;
    private FirebaseAuth mAuth;
    private EditText edtEmail, edtPassword;
    private CheckBox checkBox;
    private ProgressBar progressBar;
    private FloatingActionButton btnEnter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        btnEnter = findViewById(R.id.floatingButtonEnter);
        checkBox = findViewById(R.id.checkboxShowPassword);
        progressBar = findViewById(R.id.progressBar);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignIn();
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

//    private void test() {
//        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
//        ExtraService extraService =
//                new ExtraService("ESK04", "Quay lại điểm lấy hàng", 200000);
//        root.child("ExtraServices").child(extraService.extraServiceKey).setValue(extraService).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "That bai", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void onSignIn() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Successfully sign-in", Toast.LENGTH_SHORT).show();
                    // TODO: move on to dashboard
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void OpenSignupPage(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    public void OpenForgotPasswordPage(View view) {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mAuth.signOut();
    }

    @Override
    public void onBackPressed() {
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}