package com.example.xutungjin_comp304sec002_ex1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragment_map extends Fragment{

    private GoogleMap mMap;
    private String shop;
    private double lat;
    private double lng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Initial view
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        // Get shop name
        Bundle data = getArguments();
        if(data !=null ){
            shop = data.getString("shop");
            lat = Double.parseDouble(data.getString("lat"));
            lng = Double.parseDouble(data.getString("lng"));
        }

        // Generate map
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

                LatLng toronto = new LatLng(lat,lng);
                Marker m = mMap.addMarker(new MarkerOptions().position(toronto).title(shop));
                m.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto,16.0f));
            }
        });
        return view;
    }
}