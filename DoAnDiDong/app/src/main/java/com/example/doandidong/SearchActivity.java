package com.example.doandidong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements DoctorAdapter.Listener {

    RecyclerView rvDoctors;
    ArrayList<Doctor> doctors;
    DoctorAdapter doctorAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("");

        rvDoctors=findViewById(R.id.rvDoctors);
        db= FirebaseFirestore.getInstance();
        doctors=new ArrayList<>();
        doctorAdapter=new DoctorAdapter(SearchActivity.this,doctors);
        rvDoctors.setAdapter(doctorAdapter);
        rvDoctors.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
        rvDoctors.addItemDecoration(new DividerItemDecoration(SearchActivity.this,DividerItemDecoration.VERTICAL));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);

        SearchView searchView=(SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setIconified(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doctors.clear();

                db.collection("Doctor")
                        .whereGreaterThanOrEqualTo("FName", newText)
                        .whereLessThanOrEqualTo("FName", newText + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String Id = document.getId();
                                    String FName = document.get("FName").toString();
                                    String LName = document.get("LName").toString();
                                    String Birthday = document.get("Birthday").toString();
                                    String Phone = document.get("Phone").toString();
                                    String Email = document.get("Email").toString();
                                    String Address = document.get("Address").toString();
                                    String Sex = document.get("Sex").toString();
                                    String Major = document.get("Major").toString();
                                    Doctor doctor = new Doctor(Id, FName, LName, Birthday, Phone, Email, Address, Sex, Major);
                                    doctors.add(doctor);
                                }
                                doctorAdapter.notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SearchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                return false;
            }
        });
        return true;
    }

    @Override
    public void setOnItemClickListener(Doctor doctor) {
        Intent intent=new Intent(SearchActivity.this,DoctorInfoActivity.class);
        intent.putExtra("doctorID",doctor.getId());
        startActivity(intent);
    }

    @Override
    public void setOnButtonAdd(int pos, Doctor doctor) {
        Intent intent=new Intent(SearchActivity.this, AddAppointmentActivity.class);
        intent.putExtra("doctors",doctor);
        startActivity(intent);
    }
}