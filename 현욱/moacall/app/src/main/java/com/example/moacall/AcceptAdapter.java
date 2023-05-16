package com.example.moacall;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AcceptAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    Context dContext = null;
    LayoutInflater layoutInflater = null;
    ArrayList<AcceptData> acceptData;



    public AcceptAdapter(Context context, ArrayList<AcceptData> data) {
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
    public AcceptData getItem(int position) {
        return acceptData.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        if (converView == null) {
            final Context context = parent.getContext();
            if (layoutInflater == null) {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            converView = layoutInflater.inflate(R.layout.listview_item, parent, false);
            TextView startTimeText = (TextView) converView.findViewById(R.id.startTimeText);
            TextView acceptText = (TextView) converView.findViewById(R.id.acceptTime);
            TextView summaryAddressText = (TextView) converView.findViewById(R.id.summaryAddress);
            TextView typeOfPaymentText = (TextView) converView.findViewById(R.id.typeOfPaymentText);
            TextView priceText = (TextView) converView.findViewById(R.id.priceText);
            TextView clientAddressText = (TextView) converView.findViewById(R.id.clinetAddressText);

            TextView clientRefectorAddressText = (TextView) converView.findViewById(R.id.clientRefectorAddressText);
            TextView memoText = (TextView) converView.findViewById(R.id.memoText);
            Button mapButton = (Button) converView.findViewById(R.id.mapButton);
            String longitude = acceptData.get(position).getLongitude();
            String latitude = acceptData.get(position).getLatitude();
            String clientLatitude = acceptData.get(position).getClientLatitude();
            String clientLongitude = acceptData.get(position).getClientLongitude();
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MapViewActivity.class);

                    Log.d("LOCATION", latitude + " " + longitude);
                    Log.d("LOCATION", clientLatitude + " " + clientLongitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("clientLatitude", clientLatitude);
                    intent.putExtra("clientLongitude", clientLongitude);
                    ((MainActivity)context).startActivity(intent);
                }
            });
//            LinearLayout ll = (LinearLayout) converView.findViewById(R.id.map_layout);
//
//            ll.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);



            String startTime = acceptData.get(position).getStartTime();
            String acceptTime = acceptData.get(position).getAcceptTime();
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


            converView.setTag(""+position);
        }

        return converView;
    }

    public static long compareMinute(String date1, LocalDateTime date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSS");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSS");
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
        LocalTime startDate;
        if (date1.length() == 26) {
            startDate = LocalDateTime.parse(date1, formatter).toLocalTime();
            LocalTime endDate = date2.toLocalTime();
            Duration diff = Duration.between(startDate, endDate);
            long diffMinute = diff.toMinutes();
            if (diffMinute < 0) {
                return diffMinute * -1;
            } else {
                return diffMinute;
            }

        }
        else if(date1.length() == 25) {
             startDate = LocalDateTime.parse(date1, formatter2).toLocalTime();
            LocalTime endDate = date2.toLocalTime();
            Duration diff = Duration.between(startDate, endDate);
            long diffMinute = diff.toMinutes();
            if (diffMinute < 0) {
                return diffMinute * -1;
            } else {
                return diffMinute;
            }
        }
        else if(date1.length() == 24) {
             startDate = LocalDateTime.parse(date1, formatter3).toLocalTime();
            LocalTime endDate = date2.toLocalTime();
            Duration diff = Duration.between(startDate, endDate);
            long diffMinute = diff.toMinutes();
            if (diffMinute < 0) {
                return diffMinute * -1;
            } else {
                return diffMinute;
            }
        }
        else if(date1.length() == 23) {
             startDate = LocalDateTime.parse(date1, formatter4).toLocalTime();
            LocalTime endDate = date2.toLocalTime();
            Duration diff = Duration.between(startDate, endDate);
            long diffMinute = diff.toMinutes();
            if (diffMinute < 0) {
                return diffMinute * -1;
            } else {
                return diffMinute;
            }
        }
        else if(date1.length() == 22) {
             startDate = LocalDateTime.parse(date1, formatter5).toLocalTime();
            LocalTime endDate = date2.toLocalTime();
            Duration diff = Duration.between(startDate, endDate);
            long diffMinute = diff.toMinutes();
            if (diffMinute < 0) {
                return diffMinute * -1;
            } else {
                return diffMinute;
            }
        } else {
             startDate = LocalDateTime.parse(date1, formatter5).toLocalTime();
            LocalTime endDate = date2.toLocalTime();
            Duration diff = Duration.between(startDate, endDate);
            long diffMinute = diff.toMinutes();
            if (diffMinute < 0) {
                return diffMinute * -1;
            } else {
                return diffMinute;
            }
        }


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(dContext, "clicked", Toast.LENGTH_SHORT).show();
    }
}
