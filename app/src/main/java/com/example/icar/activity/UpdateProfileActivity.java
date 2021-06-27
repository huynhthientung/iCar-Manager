package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.icar.R;
import com.example.icar.model.Customer;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

public class UpdateProfileActivity extends AppCompatActivity {

    private final int REQUEST_CODE_IMAGE = 1;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String name;

    private ImageView imgView;
    private Button btnPickPhoto, btnUpdate;
    private EditText edtName, edtEmail, edtPhone, edtAddress, edtBirthday;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private ProgressBar progressBar;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("avatar");

        user = FirebaseAuth.getInstance().getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        initializeViews();

        // handle objects
        btnPickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                saveImage();
                updateProfile();
            }
        });
    }

    /**
     * add profile to database
     */
    private void updateProfile() {
        String uid = user.getUid();
        String email = user.getEmail();
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        String birthday = edtBirthday.getText().toString();
        boolean gender = rbMale.isChecked();

        if (uid.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty() || birthday.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
        } else {
            Customer customer = new Customer(uid, name, email, phone, address, birthday, gender);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Customers").child(uid).setValue(customer)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(UpdateProfileActivity.this, "Update profile successfully", Toast.LENGTH_SHORT).show();
                                //TODO: navigate to Dashboard
                                startActivity(new Intent(UpdateProfileActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(UpdateProfileActivity.this, "Update profile unsuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    /**
     * save image to store database
     */
    private void saveImage() {
        final StorageReference avatarReference = storageReference.child(user.getUid()+ ".png");

        // Get the data from an ImageView as bytes
        imgView.setDrawingCacheEnabled(true);
        imgView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = avatarReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UpdateProfileActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                progressBar.setVisibility(View.GONE);
                //Toast.makeText(UpdateProfileActivity.this, "Upload image successfully", Toast.LENGTH_SHORT).show();
            }
        });
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return avatarReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest;
                    profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(edtName.getText().toString())
                            .setPhotoUri(downloadUri).build();
                    user.updateProfile(profileChangeRequest);
                    Log.d("URL", downloadUri + "");
                } else {
                    // Handle failures
                    // ...
                    Log.d("AAA", ": fail at continueWithTask");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeViews() {
        imgView = findViewById(R.id.imageView);
        btnPickPhoto = findViewById(R.id.buttonPick);
        btnUpdate = findViewById(R.id.buttonUpdate);
        edtName = findViewById(R.id.editTextTextPersonName);
        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        edtPhone = findViewById(R.id.editTextPhone);
        edtAddress = findViewById(R.id.editTextTextAddress);
        edtBirthday = findViewById(R.id.editTextDate);
        rgGender = findViewById(R.id.radioGroup);
        rbMale = findViewById(R.id.radioButtonMale);
        rbFemale = findViewById(R.id.radioButtonFemale);
        progressBar = findViewById(R.id.progressBar);
        edtName.setText(name);
        edtEmail.setText(user.getEmail());
        edtEmail.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}