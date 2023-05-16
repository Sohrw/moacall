package com.example.moacall.topElement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.moacall.AcceptAdapter;
import com.example.moacall.AcceptData;
import com.example.moacall.Address;
import com.example.moacall.DeliveryStatus;
import com.example.moacall.MainActivity;
import com.example.moacall.MapViewActivity;
import com.example.moacall.PaymentType;
import com.example.moacall.R;
import com.example.moacall.RetrofitExService;

import net.daum.mf.map.api.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class acceptFrag extends Fragment implements View.OnClickListener{

    ArrayList<AcceptData> acceptData;
    ListView acceptView;
    private AcceptAdapter acceptAdapter;
    ScrollView scrollView;
    MainActivity activity;
    Context context;
    AcceptData dataDTO;
    LinearLayout ll;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        dataDTO = null;
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {

        inflater = LayoutInflater.from(getActivity());
        View rootView = inflater.inflate(R.layout.accept, container, false);
        acceptData = new ArrayList<>();
        InitializeData(acceptData);


        acceptView = (ListView) rootView.findViewById(R.id.listView_accept);
        acceptAdapter = new AcceptAdapter(getContext(), acceptData);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView_accept);


        Button mapButton = (Button) acceptView.findViewById(R.id.mapButton);

        if (mapButton != null ) {

            mapButton.setFocusableInTouchMode(true);
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(acceptView.getContext(), "sss", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("null", "null");
        }






        acceptView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Button mapButton = (Button) adapterView.findViewById(R.id.mapButton);
//                if (mapButton != null ) {
//                    mapButton.setFocusableInTouchMode(true);
//                    mapButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(adapterView.getContext(), "sss", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Log.d("null", "null");
//                }
                Toast.makeText(adapterView.getContext(), "배차되었습니다!", Toast.LENGTH_SHORT).show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RetrofitExService.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitExService retrofitExService = retrofit.create(RetrofitExService.class);
                Log.d("SendToAccept", "sendToAccept");



                retrofitExService.dispatchOrder(acceptData.get(position).getId()).enqueue(new Callback<List<AcceptData>>() {
                    @Override
                    public void onResponse(Call<List<AcceptData>> call, Response<List<AcceptData>> response) {
                        List<AcceptData> data = response.body();

                        if (data != null) {
                            InitializeData(acceptData);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AcceptData>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });





            }
        });

        acceptView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        int totalHeight = 0;
        for (int i = 0; i< acceptAdapter.getCount(); i++) {
            View listItem = acceptAdapter.getView(i, null, acceptView);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = acceptView.getLayoutParams();
        params.height = totalHeight + (acceptView.getDividerHeight() * (acceptAdapter.getCount() -1));
        acceptView.setLayoutParams(params);
        acceptView.setAdapter(acceptAdapter);



        return rootView;
    }

    @Override
    public void onClick(View view) {
        View parentView = (View) view.getParent();
        Toast.makeText(view.getContext(), "지도가 눌렸어요!", Toast.LENGTH_SHORT).show();

    }

    public void InitializeData(ArrayList<AcceptData> acceptData) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitExService retrofitExService = retrofit.create(RetrofitExService.class);
        Log.d("frag", "frag");
        retrofitExService.getOrdersAcceptData().enqueue(new Callback<List<AcceptData>>() {
            @Override
            public void onResponse(Call<List<AcceptData>> call, Response<List<AcceptData>> response) {
                if(response.isSuccessful()) {
                    List<AcceptData> data = response.body();

                    if(data != null) {
                        for (int i = 0; i< data.size(); i++) {
                            String[] foodArray = data.get(i).getFoodAddress().split("/");
                            String[] clientArray = data.get(i).getClientAddress().split("/");

                            acceptData.add(new AcceptData(data.get(i).getId(), data.get(i).getStartTime(), data.get(i).getAcceptTime(), data.get(i).getFoodName(), new Address(foodArray[0], foodArray[1], foodArray[2], foodArray[3]), new Address(clientArray[0], clientArray[1], clientArray[2], clientArray[3]),data.get(i).getClientMemo(), data.get(i).getClientPrice(), data.get(i).getDeliveryPrice(), data.get(i).getStatus(), data.get(i).getPaymentType() ,data.get(i).getLatitude(), data.get(i).getLongitude(), data.get(i).getClientLatitude(), data.get(i).getClientLongitude()));
                            acceptAdapter.notifyDataSetChanged();

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<AcceptData>> call, Throwable t) {
                t.printStackTrace();
            }
        });






    }


}
