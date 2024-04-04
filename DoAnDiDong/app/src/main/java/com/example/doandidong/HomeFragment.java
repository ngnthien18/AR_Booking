package com.example.doandidong;

import static android.app.appsearch.AppSearchResult.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements DoctorAdapter.Listener{

    RecyclerView rvDoctors;
    ArrayList<Doctor> doctors;
    DoctorAdapter doctorAdapter;
    FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvDoctors=view.findViewById(R.id.rvDoctors);
        db=FirebaseFirestore.getInstance();
        doctors=new ArrayList<>();
        doctorAdapter=new DoctorAdapter(this,doctors);
        rvDoctors.setAdapter(doctorAdapter);
        rvDoctors.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rvDoctors.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        db.collection("Doctor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            String Id=document.getId();
                            String FName = document.get("FName").toString();
                            String LName = document.get("LName").toString();
                            String Birthday=document.get("Birthday").toString();
                            String Phone =document.get("Phone").toString();
                            String Email=document.get("Email").toString();
                            String Address=document.get("Address").toString();
                            String Sex=document.get("Sex").toString();
                            String Major=document.get("Major").toString();
                            Doctor doctor=new Doctor(Id,FName,LName,Birthday,Phone,Email,Address,Sex,Major);
                            doctors.add(doctor);
                        }
                        doctorAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void setOnItemClickListener(Doctor doctor) {
        Intent intent=new Intent(getActivity(),DoctorInfoActivity.class);
        intent.putExtra("doctorID",doctor.getId());
        startActivity(intent);
    }

    @Override
    public void setOnButtonAdd(int position,Doctor doctor) {
        Intent intent=new Intent(getActivity(), AddAppointmentActivity.class);
        intent.putExtra("doctors",doctor);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.mnuSort){
            doctors.clear();
            db.collection("Doctor")
                    .orderBy("LName")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                String Id=document.getId();
                                String FName = document.get("FName").toString();
                                String LName = document.get("LName").toString();
                                String Birthday=document.get("Birthday").toString();
                                String Phone =document.get("Phone").toString();
                                String Email=document.get("Email").toString();
                                String Address=document.get("Address").toString();
                                String Sex=document.get("Sex").toString();
                                String Major=document.get("Major").toString();
                                Doctor doctor=new Doctor(Id,FName,LName,Birthday,Phone,Email,Address,Sex,Major);
                                doctors.add(doctor);
                            }
                            doctorAdapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
            doctorAdapter.notifyDataSetChanged();
        }
        if (item.getItemId()==R.id.mnuSearch){
            Intent intent=new Intent(getActivity(),SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}