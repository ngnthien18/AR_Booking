package com.example.doandidong;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etRName,etRPhone,etREmail, etRPassword, etVPassword;
    Button btnRegister;
    FirebaseAuth auth;
    FirebaseFirestore fb;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Đăng Ký");

        etRName=findViewById(R.id.etRName);
        etRPhone=findViewById(R.id.etRPhone);
        etREmail=findViewById(R.id.etREmail);
        etRPassword=findViewById(R.id.etRPassword);
        etVPassword=findViewById(R.id.etVPassword);
        btnRegister=findViewById(R.id.btnRegister);
        auth=FirebaseAuth.getInstance();
        fb=FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etRName.getText().toString().trim();
                String phone=etRPhone.getText().toString().trim();
                String email = etREmail.getText().toString().trim();
                String password = etRPassword.getText().toString().trim();
                String vpassword=etVPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Họ tên không được bỏ trống!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Số điện thoại không được bỏ trống!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email không được bỏ trống!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu không được bỏ trống!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), R.string.minimum_password, Toast.LENGTH_LONG).show();
                    return;
                }

                if(!password.equals(vpassword)){
                    Toast.makeText(getApplicationContext(), "Mật khẩu xác nhận không đúng!",Toast.LENGTH_LONG).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        userID=auth.getCurrentUser().getUid();
                        DocumentReference doc=fb.collection("User").document(userID);
                        Map<String,Object> user =new HashMap<>();
                        user.put("Name",etRName.getText().toString());
                        user.put("Email",etREmail.getText().toString());
                        user.put("Phone",etRPhone.getText().toString());
                        doc.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG,"Them thanh cong");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"Loi");
                            }
                        });
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}