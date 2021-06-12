package com.example.xutungjin_comp304002_lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class complete_order extends AppCompatActivity {

    // Layout
    private order o;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);

        // Get intent from customer_info activity
        Intent i = getIntent();
        o = (order)i.getSerializableExtra("complete_order");

        // Set text
        display=(TextView)findViewById(R.id.order_text);
        display.setText(o.toString());
    }
}