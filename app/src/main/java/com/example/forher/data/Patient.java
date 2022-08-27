package com.example.forher.data;

import java.util.ArrayList;
import java.util.List;

public class Patient {

    String email;
    String password;
    String name;
    String age;
    String phone;
    String image;
    ArrayList<BreastCancer> xrayImages;
    int Couter;
    String recommendation;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<BreastCancer> getXrayImages() {
        return xrayImages;
    }

    public int getCouter() {
        return Couter;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public Patient(String password, String name,
                   String phone, String image) {


        this.password = password;
        this.name = name;

        this.phone = phone;
        this.image = image;


    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setXrayImages(ArrayList<BreastCancer> xrayImages) {
        this.xrayImages = xrayImages;
    }

    public void setCouter(int couter) {
        Couter = couter;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
