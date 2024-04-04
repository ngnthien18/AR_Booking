package com.example.doandidong;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends AppCompatActivity {

    String name, email, phone,userID;
    TextInputLayout tilName,tilPhone;
    TextInputEditText edName,edPhone;
    TextView txEmail;
    Button btnSave;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle("Cập Nhật Hồ Sơ");

        Intent intent=getIntent();
        userID=intent.getStringExtra("userId");
        name=intent.getStringExtra("name");
        email= intent.getStringExtra("email");
        phone=intent.getStringExtra("phone");

        tilName=findViewById(R.id.tilName);
        tilPhone=findViewById(R.id.tilPhone);
        edName=findViewById(R.id.edName);
        edPhone=findViewById(R.id.edPhone);
        txEmail=findViewById(R.id.txEmail);
        btnSave=findViewById(R.id.btnSave);

        db = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        txEmail.setText(email);
        edName.setText(name);
        edPhone.setText(phone);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edName.getText().toString().isEmpty() || edPhone.getText().toString().isEmpty()){
                    Toast.makeText(EditProfileActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                    return;
                }

                db.collection("User").document(userID).update("Name",edName.getText().toString(),"Phone",edPhone.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void Avoid) {
                                Log.d(TAG, "Cap nhat thanh cong");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Loi", e);
                            }
                        });
                finish();
            }
        });
    }
}