package com.example.moacall;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AcceptData implements Serializable {

    @SerializedName("orderId")
    private Long id;

    @SerializedName("orderDate")
    private String startTime;

    private String acceptTime;

    @SerializedName("foodName")
    private String foodName;

    @SerializedName("foodAddress")
    private Address foodAddress;

    @SerializedName("clientAddress")
    private Address clientAddress;
    @SerializedName("memo")
    private String clientMemo;
    @SerializedName("totalPrice")
    private int clientPrice;
    @SerializedName("deliveryPrice")
    private int deliveryPrice;
    @SerializedName("orderStatus")
    private DeliveryStatus status;
    @SerializedName("paymentType")
    private PaymentType paymentType;

    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    @SerializedName("clientLatitude")
    private String clientLatitude;
    @SerializedName("clientLongitude")
    private String clientLongitude;
    

    public AcceptData(Long id, String startTime, String acceptTime, String foodName, Address foodAddress, Address clientAddress, String clientMemo, int clientPrice, int deliveryPrice, DeliveryStatus status, PaymentType paymentType, String latitude, String longitude, String clientLatitude, String clientLongitude) {
        this.id = id;
        this.startTime = startTime;
        this.acceptTime = acceptTime;
        this.foodName = foodName;
        this.foodAddress = foodAddress;
        this.clientAddress = clientAddress;
        this.clientMemo = clientMemo;
        this.clientPrice = clientPrice;
        this.deliveryPrice = deliveryPrice;
        this.status = status;
        this.paymentType = paymentType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.clientLatitude = clientLatitude;
        this.clientLongitude = clientLongitude;

    }

    public String getClientLatitude() {
        return clientLatitude;
    }

    public void setClientLatitude(String clientLatitude) {
        this.clientLatitude = clientLatitude;
    }

    public String getClientLongitude() {
        return clientLongitude;
    }

    public void setClientLongitude(String clientLongitude) {
        this.clientLongitude = clientLongitude;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public void setFoodAddress(Address foodAddress) {
        this.foodAddress = foodAddress;
    }

    public void setClientAddress(Address clientAddress) {
        this.clientAddress = clientAddress;
    }

    public void setClientMemo(String clientMemo) {
        this.clientMemo = clientMemo;
    }

    public void setClientPrice(int clientPrice) {
        this.clientPrice = clientPrice;
    }

    public void setDeliveryPrice(int deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getFoodAddress() {
        return foodAddress.toString();
    }

    public String getClientAddress() {
        return clientAddress.toString();
    }

    public String getClientMemo() {
        return clientMemo;
    }

    public int getClientPrice() {
        return clientPrice;
    }

    public int getDeliveryPrice() {
        return deliveryPrice;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
