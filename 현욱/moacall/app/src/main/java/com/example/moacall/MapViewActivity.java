package com.example.moacall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class MapViewActivity extends AppCompatActivity {

    private Address address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.map_view);
        Intent intent = getIntent();


        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        String clientLatitude = intent.getStringExtra("clientLatitude");
        String clientLongitude = intent.getStringExtra("clientLongitude");

        TMapView tMapView = new TMapView(this);
        tMapView.setZoomLevel(17);

        tMapView.setSKTMapApiKey("l7xx740ca7e62b374e86b4634ff36d292130");



        tMapView.setCenterPoint(  Double.parseDouble(longitude), Double.parseDouble(latitude));
        TMapMarkerItem foodMarker = new TMapMarkerItem();
        TMapMarkerItem clientMarker = new TMapMarkerItem();

        TMapPoint foodPoint = new TMapPoint(Double.parseDouble(longitude), Double.parseDouble(latitude));
        TMapPoint clientPoint = new TMapPoint(Double.parseDouble(clientLongitude), Double.parseDouble(clientLatitude));

        foodMarker.setID("food");
        clientMarker.setID("client");

        foodMarker.setTMapPoint(foodPoint);
        clientMarker.setTMapPoint(clientPoint);
        foodMarker.setVisible(TMapMarkerItem.VISIBLE);
        clientMarker.setVisible(TMapMarkerItem.VISIBLE);
//        foodMarker.setName("음식점");
//        clientMarker.setName("도착지");
        foodMarker.setPosition(0.5f, 1.0f);
        clientMarker.setPosition(0.5f, 1.0f);
        tMapView.addMarkerItem("food", foodMarker);
        tMapView.addMarkerItem("client", clientMarker);

        ArrayList<TMapPoint> pointArrayList = new ArrayList<TMapPoint>();
        pointArrayList.add(foodPoint);
        pointArrayList.add(clientPoint);

        TMapPolyLine line = new TMapPolyLine();
        line.setLineColor(Color.BLUE);
        line.setLineWidth(3);
        for (int i = 0; i < pointArrayList.size(); i++) {
            line.addLinePoint(pointArrayList.get(i));
        }
        tMapView.addTMapPolyLine("line1",line);

        linearLayoutTmap.addView(tMapView);



    }
}
