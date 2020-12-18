package com.shareme.bank.fragments.home_fragments.create_donation_request;

public class CreateRequestModel {

    String api_token , patient_name , patient_age , blood_type , bags_num , hospital_name , hospital_address , phone , notes , latitude , logitude;
    int city_id;

    public CreateRequestModel(){}

    public CreateRequestModel(String api_token, String patient_name, String patient_age, String blood_type,
                              String bags_num, String hospital_name, String hospital_address, int city_id,
                              String phone, String notes, String latitude, String logitude) {
        this.api_token = api_token;
        this.patient_name = patient_name;
        this.patient_age = patient_age;
        this.blood_type = blood_type;
        this.bags_num = bags_num;
        this.hospital_name = hospital_name;
        this.hospital_address = hospital_address;
        this.city_id = city_id;
        this.phone = phone;
        this.notes = notes;
        this.latitude = latitude;
        this.logitude = logitude;
    }


    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(String patient_age) {
        this.patient_age = patient_age;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getBags_num() {
        return bags_num;
    }

    public void setBags_num(String bags_num) {
        this.bags_num = bags_num;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }
}
