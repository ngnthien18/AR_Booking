package com.example.doandidong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppointmentInfoActivity extends AppCompatActivity {

    ImageView ivApp;
    TextView tvIdA, tvDoctorName, tvType, tvDate, tvTime;
    String appointmentId;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);

        getSupportActionBar().setTitle("Thông Tin Lịch Khám");

        appointmentId=getIntent().getStringExtra("appointmentId");
        ivApp=findViewById(R.id.ivApp);
        tvIdA=findViewById(R.id.tvIdA);
        tvDoctorName=findViewById(R.id.tvDoctorName);
        tvType=findViewById(R.id.tvType);
        tvDate=findViewById(R.id.tvDate);
        tvTime=findViewById(R.id.tvTime);

        db=FirebaseFirestore.getInstance();
        DocumentReference docRef=db.collection("Appointment").document(appointmentId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Appointment appointment=documentSnapshot.toObject(Appointment.class);
                    tvIdA.setText("Lịch khám ");
                    tvDoctorName.setText(appointment.getDoctorName());
                    tvType.setText(appointment.getType());
                    tvDate.setText(appointment.getDate());
                    tvTime.setText(appointment.getTime());
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