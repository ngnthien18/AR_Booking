package com.example.doandidong;

import java.io.Serializable;

public class TimeSlot implements Serializable {
    String IdT;
    String Date;
    String Time;
    String Status;
    String DoctorId;

    public TimeSlot(String idT, String date, String time, String status, String doctorId) {
        IdT = idT;
        Date=date;
        Time = time;
        Status = status;
        DoctorId = doctorId;
    }

    public String getIdT() {
        return IdT;
    }

    public void setIdT(String idT) {
        IdT = idT;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }
}
