package com.example.moacall;

import android.provider.ContactsContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitExService {

    String url = "http://192.168.0.4:8080";

    @GET("/api/orders")
    Call<List<AcceptData>> getOrdersData();

    @GET("/api/orders/accept")
    Call<List<AcceptData>> getOrdersAcceptData();

    @GET("/api/orders/dispatch")
    Call<List<AcceptData>> getOrdersDispatchData();

    @POST("/api/orders/{orderId}")
    Call<List<AcceptData>> dispatchOrder(@Path("orderId") Long orderId);

    @POST("/api/orderpick/{orderId}")
    Call<List<AcceptData>> pickUpOrder(@Path("orderId") Long orderId);

    @GET("/api/orders/delivery")
    Call<List<AcceptData>> getOrdersDeliveryData();

    @GET("/api/orders/complete")
    Call<List<AcceptData>> getOrdersCompleteData();

    @POST("/api/ordercomplete/{orderId}")
    Call<List<AcceptData>> completeOrder(@Path("orderId") Long orderId);

}
