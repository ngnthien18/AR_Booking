package com.example.doandidong;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentVH>{

    ArrayList<Appointment> appointments;
    Listener listener;
    public AppointmentAdapter(Listener listener,ArrayList<Appointment> appointments){
        this.listener=listener;
        this.appointments=appointments;
    }
    @NonNull
    @Override
    public AppointmentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row,parent,false);
        return new AppointmentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentVH holder, int position) {
        Appointment appointment=appointments.get(position);
        holder.txIdA.setText("Lịch khám: ");
        holder.txDoctorName.setText("Bác sĩ: ".concat(appointment.getDoctorName()));
        holder.txType.setText("Loại khám: ".concat(appointment.getType()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItemListener(appointment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class AppointmentVH extends RecyclerView.ViewHolder{
        CircleImageView imgApp;
        TextView txIdA,txDoctorName,txType;

        public AppointmentVH(@NonNull View itemView) {
            super(itemView);
            imgApp=itemView.findViewById(R.id.imgApp);
            txIdA=itemView.findViewById(R.id.txIdA);
            txDoctorName=itemView.findViewById(R.id.txDoctorName);
            txType=itemView.findViewById(R.id.txType);
        }
    }
    interface Listener{
        void onClickItemListener(Appointment appointment);
    }
}
