package com.example.doandidong;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorVH> {

    ArrayList<Doctor> doctors;
    Listener listener;
    public DoctorAdapter(Listener listener,ArrayList<Doctor> doctors) {
        this.listener=listener;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public DoctorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_row, parent,false);
        return new DoctorVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DoctorVH holder, int position) {
        Doctor doctor =doctors.get(position);
//        holder.imgAvatar.setImageResource(doctor.getImg());
        holder.txName.setText(doctor.getFName()+" "+doctor.getLName());
        holder.txMajor.setText(doctor.getMajor());
        holder.txEmail.setText(doctor.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnItemClickListener(doctor);
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnButtonAdd(position,doctor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    class DoctorVH extends RecyclerView.ViewHolder{
        CircleImageView imgAvatar;
        TextView txName,txMajor,txEmail;
        Button btnAdd;

        public DoctorVH(@NonNull View itemView) {
            super(itemView);
            imgAvatar=itemView.findViewById(R.id.imgAvatar);
            txName=itemView.findViewById(R.id.txName);
            txMajor=itemView.findViewById(R.id.txMajor);
            txEmail=itemView.findViewById(R.id.txEmail);
            btnAdd=itemView.findViewById(R.id.btnAdd);
        }
    }
    interface Listener{
        void setOnItemClickListener(Doctor doctor);
        void setOnButtonAdd(int pos, Doctor doctor);
    }
}