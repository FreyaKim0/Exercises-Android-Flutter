package com.example.xutungjin_comp304sec002_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;

import java.security.Provider;

public class display_maps extends AppCompatActivity  {

    private Double lat;
    private Double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_maps);

        // Get intent values
        Intent intent = getIntent();
        String shop =intent.getStringExtra("name");
        Toast.makeText(getApplicationContext(),shop,Toast.LENGTH_SHORT).show();

        switch (shop){
            case "Mezza Notte Trattoria":
                lat = 43.77652332451043;
                lng =-79.40475077976403;
                break;
            case "Pizza Hut Toronto":
                lat = 43.790651615928745;
                lng =-79.4090423138393;
                break;
            case "Napoli Vince Pizza":
                lat = 43.78296822247306;
                lng =-79.43324656602381;
                break;
            case "The Famous Owl of Minerva":
                lat = 43.77589289868355;
                lng =-79.4133338651476;
                break;
            case "KoSam Korean Restaurant and Bar":
                lat = 43.76783571435817;
                lng =-79.4114455901545;
                break;
            case "Kayagum Restaurant":
                lat = 43.7803548721554;
                lng =-79.41505047877773;
                break;
        }

        // Pass value into fragments
        Bundle bundle = new Bundle();
        bundle.putString("shop",shop);
        bundle.putString("lat",lat.toString());
        bundle.putString("lng",lng.toString());

        fragment_satellite f2 = new fragment_satellite();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        f2.setArguments(bundle);
        transaction2.replace(R.id.satellite_map_fragment,f2).commit();

        fragment_map f = new fragment_map();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        f.setArguments(bundle);
        transaction.replace(R.id.map_fragment,f).commit();
    }
}