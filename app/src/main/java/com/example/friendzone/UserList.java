package com.example.friendzone;

public class UserList {
    private String FullName;
    private String Bio;
    private String Retailer;
    private String PhoneNumber;
    private String Gender;
    private String Email;
    private String City;
    private String Age;

    public UserList(String Retailer, String PhoneNumber, String Gender, String FullName, String Email, String City, String Bio, String Age)
    {
        this.Retailer = Retailer;
        this.PhoneNumber = PhoneNumber;
        this.Gender = Gender;
        this.FullName = FullName;
        this.Email = Email;
        this.City = City;
        this.Bio = Bio;
        this.Age = Age;
    }

    public UserList(){

    }

    public String getRetailer() {
        return Retailer;
    }

    public void setRetailer(String Retailer) {
        this.Retailer = Retailer;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhone(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }
}
