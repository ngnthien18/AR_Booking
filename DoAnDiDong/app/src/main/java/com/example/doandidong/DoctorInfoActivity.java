package com.example.doandidong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorInfoActivity extends AppCompatActivity {

    ImageView ivAvatar;
    TextView tvName, tvPhone, tvEmail, tvBirthday, tvAddress, tvSex,tvMajor;
    String doctorID;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        getSupportActionBar().setTitle("Thông Tin Bác Sĩ");

        doctorID=getIntent().getStringExtra("doctorID");
        ivAvatar=findViewById(R.id.ivAvatar);
        tvName=findViewById(R.id.tvName);
        tvPhone=findViewById(R.id.tvPhone);
        tvEmail=findViewById(R.id.tvEmail);
        tvBirthday=findViewById(R.id.tvBirthday);
        tvAddress=findViewById(R.id.tvAddress);
        tvSex=findViewById(R.id.tvSex);
        tvMajor=findViewById(R.id.tvMajor);

        db=FirebaseFirestore.getInstance();
        DocumentReference docRef=db.collection("Doctor").document(doctorID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Doctor doctor=documentSnapshot.toObject(Doctor.class);
                    tvName.setText(doctor.getFName()+" "+doctor.getLName());
                    tvPhone.setText(doctor.getPhone());
                    tvEmail.setText(doctor.getEmail());
                    tvBirthday.setText(doctor.getBirthday());
                    tvAddress.setText(doctor.getAddress());
                    tvSex.setText(doctor.getSex());
                    tvMajor.setText(doctor.getMajor());
                }
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