
package com.shareme.bank.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("donation_last_date")
    @Expose
    private String donationLastDate;
    @SerializedName("blood_type")
    @Expose
    private String bloodType;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private int id;

    public Client(int id, String name, String city_id, String blode_type, String email, String last_donate, String birthDate, String phone) {

        this.id=id;
        this.name=name;
        this.cityId = city_id;
        this.bloodType = blode_type;
        this.email = email;
        this.donationLastDate = last_donate;
        this.phone = phone;
        this.birthDate = birthDate;



    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDonationLastDate() {
        return donationLastDate;
    }

    public void setDonationLastDate(String donationLastDate) {
        this.donationLastDate = donationLastDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
