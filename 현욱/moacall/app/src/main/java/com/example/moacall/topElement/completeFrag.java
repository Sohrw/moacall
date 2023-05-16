package com.example.moacall.topElement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moacall.AcceptData;
import com.example.moacall.Address;
import com.example.moacall.DispatchAdapter;
import com.example.moacall.MainActivity;
import com.example.moacall.R;
import com.example.moacall.RetrofitExService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class completeFrag extends Fragment {
    ArrayList<AcceptData> acceptData;
    ListView completeView;
    private DispatchAdapter completeAdapter;
    ScrollView scrollView;
    MainActivity activity;
    Context context;
    AcceptData dataDTO;

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
        View rootView = inflater.inflate(R.layout.dispatch_activity, container, false);
        acceptData = new ArrayList<>();
        InitializeData(acceptData);

        completeView = (ListView) rootView.findViewById(R.id.listView_dispatch);
        completeAdapter = new DispatchAdapter(getContext(), acceptData);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView_dispatch);

        completeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


            }
        });

        completeView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        int totalHeight = 0;
        for (int i = 0; i< completeAdapter.getCount(); i++) {
            View listItem = completeAdapter.getView(i, null, completeView);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = completeView.getLayoutParams();
        params.height = totalHeight + (completeView.getDividerHeight() * (completeAdapter.getCount() -1));
        completeView.setLayoutParams(params);
        completeView.setAdapter(completeAdapter);


        return rootView;
    }

    public void InitializeData(ArrayList<AcceptData> acceptData) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitExService retrofitExService = retrofit.create(RetrofitExService.class);

        retrofitExService.getOrdersCompleteData().enqueue(new Callback<List<AcceptData>>() {
            @Override
            public void onResponse(Call<List<AcceptData>> call, Response<List<AcceptData>> response) {
                if(response.isSuccessful()) {
                    List<AcceptData> data = response.body();

                    if(data != null) {
                        for (int i = 0; i< data.size(); i++) {
                            String[] foodArray = data.get(i).getFoodAddress().split("/");
                            String[] clientArray = data.get(i).getClientAddress().split("/");

                            acceptData.add(new AcceptData(data.get(i).getId(), data.get(i).getStartTime(), data.get(i).getAcceptTime(), data.get(i).getFoodName(), new Address(foodArray[0], foodArray[1], foodArray[2], foodArray[3]), new Address(clientArray[0], clientArray[1], clientArray[2], clientArray[3]),data.get(i).getClientMemo(), data.get(i).getClientPrice(), data.get(i).getDeliveryPrice(), data.get(i).getStatus(), data.get(i).getPaymentType() ,data.get(i).getLatitude(), data.get(i).getLongitude(),data.get(i).getClientLatitude(), data.get(i).getClientLongitude()));
                            completeAdapter.notifyDataSetChanged();


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
