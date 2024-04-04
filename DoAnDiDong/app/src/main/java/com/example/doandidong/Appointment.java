package com.example.doandidong;

import java.io.Serializable;

public class Appointment implements Serializable {
    String idA;
    String DoctorName;
    String Type;
    String Date;
    String Time;

    public Appointment() {
    }

    public Appointment(String idA, String doctorName, String type, String date, String time) {
        this.idA = idA;
        this.DoctorName = doctorName;
        this.Type = type;
        this.Date = date;
        this.Time = time;
    }

    public String getIdA() {
        return idA;
    }

    public void setIdA(String idA) {
        this.idA = idA;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        this.DoctorName = doctorName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }
}
