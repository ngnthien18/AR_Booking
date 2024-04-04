package com.example.doandidong;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotVH>{
    ArrayList<TimeSlot> timeSlots;
    Listener listener;
    public TimeSlotAdapter(Listener listener, ArrayList<TimeSlot> timeSlots) {
        this.listener=listener;
        this.timeSlots = timeSlots;
    }

    @NonNull
    @Override
    public TimeSlotVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeslot_cell, parent,false);
        return new TimeSlotVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.TimeSlotVH holder, int position) {
        TimeSlot timeSlot =timeSlots.get(position);
        holder.txTime.setText(timeSlot.getTime());
        holder.txStatus.setText(timeSlot.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnItemClickListener(timeSlot);
            }
        });
        if (timeSlot.getStatus().equals("Đã Đặt")) {
            holder.itemView.setBackgroundColor(Color.GRAY);
            holder.itemView.setEnabled(false);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.itemView.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    class TimeSlotVH extends RecyclerView.ViewHolder{
        TextView txTime,txStatus;

        public TimeSlotVH(@NonNull View itemView) {
            super(itemView);
            txTime=itemView.findViewById(R.id.txTime);
            txStatus=itemView.findViewById(R.id.txStatus);
        }
    }
    interface Listener{
        void setOnItemClickListener(TimeSlot timeSlot);
    }
}
