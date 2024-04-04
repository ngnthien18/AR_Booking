package com.example.doandidong;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddAppointmentActivity extends AppCompatActivity implements TimeSlotAdapter.Listener{

    TextInputLayout tilDate,tilTime;
    TextInputEditText edDate,edTime;
    TextView txDoctorName,txType;
    int mYear, mMonth, mDay;
    Doctor doctor;
    Button btnAdd;
    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView rvTimeSlot;
    ArrayList<TimeSlot> timeSlots;
    TimeSlotAdapter timeSlotAdapter;
    String userId,tsId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        getSupportActionBar().setTitle("Đặt Lịch Khám");

        Intent intent=getIntent();
        doctor= (Doctor) intent.getSerializableExtra("doctors");
        txDoctorName=findViewById(R.id.txDoctorName);
        txType=findViewById(R.id.txType);
        tilDate = findViewById(R.id.tilDate);
        tilTime=findViewById(R.id.tilTime);
        edTime=findViewById(R.id.edTime);
        edDate = findViewById(R.id.edDate);
        btnAdd=findViewById(R.id.btnAdd);

        rvTimeSlot=findViewById(R.id.rvTimeSlot);
        auth=FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();
        timeSlots=new ArrayList<>();
        timeSlotAdapter=new TimeSlotAdapter(AddAppointmentActivity.this,timeSlots);
        rvTimeSlot.setAdapter(timeSlotAdapter);
        rvTimeSlot.setLayoutManager(new GridLayoutManager(AddAppointmentActivity.this,2));

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                DatePickerDialog datePickerDialog = new DatePickerDialog ( AddAppointmentActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edDate.setText ( dayOfMonth + "/" + String.format("%02d",month+1) + "/" + year );
                        db.collection("TimeSlot")
                                .whereEqualTo("DoctorId",doctor.getId())
                                .whereEqualTo("Date",edDate.getText().toString())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        timeSlots.clear();
                                        for (QueryDocumentSnapshot document: task.getResult()) {
                                            String Id=document.getId();
                                            String Date=document.get("Date").toString();
                                            String Time = document.get("Time").toString();
                                            String Status=document.get("Status").toString();
                                            String DoctorId =document.get("DoctorId").toString();
                                            TimeSlot timeSlot=new TimeSlot(Id,Date,Time,Status,DoctorId);
                                            timeSlots.add(timeSlot);
                                        }
                                        timeSlotAdapter.notifyDataSetChanged();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddAppointmentActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        } );

        txDoctorName.setText(doctor.getFName()+" "+doctor.getLName());
        txType.setText(doctor.getMajor());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edTime.getText().toString().isEmpty()
                        ||edDate.getText().toString().isEmpty()){
                    tilDate.setError("Not null");
                    tilTime.setError("Not null");
                return;}
                Map<String,Object> map=new HashMap<>();
                map.put("DoctorName",txDoctorName.getText().toString());
                map.put("Type",txType.getText().toString());
                map.put("Date",edDate.getText().toString());
                map.put("Time",edTime.getText().toString());
                map.put("UserId",userId);
                db=FirebaseFirestore.getInstance();
                db.collection("Appointment").add(map)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG,"Them thanh cong"+documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG,"Loi",e);
                            }
                        });

                db.collection("TimeSlot").document(tsId).update("Status","Đã Đặt")
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void setOnItemClickListener(TimeSlot timeSlot) {
        edTime.setText(timeSlot.getTime());
        tsId=timeSlot.getIdT();
    }
}