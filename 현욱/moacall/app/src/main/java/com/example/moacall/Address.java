package com.example.moacall;

public class Address {

    private String city;


    private String street;
    private String zipcode;
    private String detailAddress;

    public Address(String city, String street, String zipcode, String detailAddress) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.detailAddress = detailAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getstreet() {
        return street;
    }

    public void setstreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    @Override
    public String toString() {
        return city + "/"+ street + "/" +
                zipcode + "/" +
                detailAddress;
    }


}
