package com.example.xutungjin_comp304sec002_ex1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragment_satellite extends Fragment {

    private GoogleMap mMap;
    private String shop;
    private double lat=43.76783571435817;
    private double lng=79.41505047877773;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Initial view
        View view = inflater.inflate(R.layout.fragment_satellite,container,false);

        // Get shop name
        Bundle data = getArguments();
        if(data !=null ){
            shop = data.getString("shop");
            lat = Double.parseDouble(data.getString("lat"));
            lng = Double.parseDouble(data.getString("lng"));
        }

        // Generate map
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_satellite_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

                LatLng toronto = new LatLng(lat,lng);
                Marker m = mMap.addMarker(new MarkerOptions().position(toronto).title(shop));
                m.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto,18.0f));
                mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
            }
        });
        return view;
    }
}