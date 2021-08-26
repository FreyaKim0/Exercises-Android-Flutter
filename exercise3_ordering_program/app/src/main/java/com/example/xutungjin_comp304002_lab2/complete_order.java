package com.example.xutungjin_comp304002_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class complete_order extends AppCompatActivity {

    private order o;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);

        Intent i = getIntent();
        o = (order)i.getSerializableExtra("complete_order");

        display=(TextView)findViewById(R.id.order_text);
        display.setText(o.toString());
    }
}