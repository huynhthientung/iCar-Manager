package com.example.icar.feedback;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icar.R;
import com.example.icar.model.Feedback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FeedbackActivity extends AppCompatActivity {

    private EditText edtContent;
    private Button btnSendFeedback;
    private DatabaseReference mReference;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mReference = FirebaseDatabase.getInstance().getReference();
        btnSendFeedback = findViewById(R.id.button_sendFeedback);
        edtContent = findViewById(R.id.editTextTextMultiLine);

        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String content = edtContent.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Vui lòng nhập góp ý", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd,MMMM,yyyy hh,mm,a");
                    String strDate = sdf.format(c.getTime());
                    String uid = mUser.getUid();
                    Feedback feedback = new Feedback(content, strDate, uid);
                    mReference.child("Feedback").push().setValue(feedback)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            Toast.makeText(FeedbackActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                            edtContent.setText("");
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}