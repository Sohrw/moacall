package com.example.moacall;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DispatchAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    Context dContext = null;
    LayoutInflater layoutInflater = null;
    ArrayList<AcceptData> acceptData;
    MainActivity activity;

    public DispatchAdapter(Context context, ArrayList<AcceptData> data) {
        dContext = context;
        acceptData = data;
        layoutInflater = LayoutInflater.from(dContext);
    }

    @Override
    public int getCount() {
        return acceptData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public AcceptData getItem(int position) { return acceptData.get(position); }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listview_item, null);
        TextView startTimeText = (TextView) view.findViewById(R.id.startTimeText);
        TextView acceptText = (TextView) view.findViewById(R.id.acceptTime);
        TextView summaryAddressText = (TextView) view.findViewById(R.id.summaryAddress);
        TextView typeOfPaymentText = (TextView) view.findViewById(R.id.typeOfPaymentText);
        TextView priceText = (TextView) view.findViewById(R.id.priceText);
        TextView clientAddressText = (TextView) view.findViewById(R.id.clinetAddressText);
        TextView clientRefectorAddressText = (TextView) view.findViewById(R.id.clientRefectorAddressText);
        TextView memoText = (TextView) view.findViewById(R.id.memoText);
        Button pickupButton = (Button) view.findViewById(R.id.mapButton);
        String acceptTime = acceptData.get(position).getAcceptTime();
        pickupButton.setText("픽업");
        pickupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RetrofitExService.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitExService retrofitExService = retrofit.create(RetrofitExService.class);
                Log.d("SendToAccept", "sendToAccept");



                retrofitExService.pickUpOrder(acceptData.get(position).getId()).enqueue(new Callback<List<AcceptData>>() {
                    @Override
                    public void onResponse(Call<List<AcceptData>> call, Response<List<AcceptData>> response) {
                        List<AcceptData> data = response.body();

                        if (data != null) {
                            pickupButton.setClickable(false);
                            view.setBackgroundColor(Color.parseColor("#afe3ff"));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AcceptData>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        String startTime = acceptData.get(position).getStartTime();
        LocalDateTime presentTime = LocalDateTime.now();
        startTimeText.setText(compareMinute(startTime, presentTime) + "분");
        acceptText.setText("20분 " + " / 접수 : " + acceptTime);
        summaryAddressText.setText(acceptData.get(position).getFoodName() + "/금방나옴/" + "9.99km");

        switch (acceptData.get(position).getPaymentType()) {
            case PRE_PAYMENT:
                typeOfPaymentText.setText("선결제");
                break;
            case CARD:
                typeOfPaymentText.setText("카드");
                break;
            case CASH:
                typeOfPaymentText.setText("현금");
                break;
            default:
                typeOfPaymentText.setText("NULL");
        }
        priceText.setText(acceptData.get(position).getClientPrice() + "/" + acceptData.get(position).getDeliveryPrice());
        clientAddressText.setText(acceptData.get(position).getClientAddress());
        memoText.setText(acceptData.get(position).getClientMemo());
        if (acceptData.get(position).getStatus() == DeliveryStatus.DELIVERY) {
            pickupButton.setEnabled(false);
            view.setBackgroundColor(Color.GREEN);
        }

        return view;

    }

    public static long compareMinute(String date1, LocalDateTime date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        LocalTime startDate = LocalDateTime.parse(date1, formatter).toLocalTime();
        LocalTime endDate = date2.toLocalTime();
        Duration diff = Duration.between(startDate, endDate);
        long diffMinute = diff.toMinutes();
        if (diffMinute < 0) {
            return diffMinute * -1;
        } else {
            return diffMinute;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
