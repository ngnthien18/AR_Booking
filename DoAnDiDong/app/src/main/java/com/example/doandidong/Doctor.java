package com.example.doandidong;

import java.io.Serializable;

public class Doctor implements Serializable{
    String Id;
    String FName;
    String LName;
//    int img;
    String Birthday;
    String Phone;
    String Email;
    String Address;
    String Sex;
    String Major;

    public Doctor(String Id, String FName, String LName, String Birthday, String Phone, String Email, String Address, String Sex, String Major) {
        this.Id = Id;
        this.FName = FName;
        this.LName=LName;
        //this.img=img;
        this.Birthday = Birthday;
        this.Phone = Phone;
        this.Email = Email;
        this.Address = Address;
        this.Sex = Sex;
        this.Major = Major;
    }
    public Doctor() {
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getFName() {return FName;}

    public void setFName(String Fname) {
        this.FName = Fname;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }
    //    public int getImg() {
//        return img;
//    }
//
//    public void setImg(int img) {
//        this.img = img;
//    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        this.Birthday = birthday;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        this.Sex = sex;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        this.Major = major;
    }
}
