package com.example.xutungjin_comp304sec002_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class restaurant extends AppCompatActivity {

    ListView listView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Get intent values
        Intent intent = getIntent();
        String cuisine =intent.getStringExtra("name");

        // Listview
        listView=(ListView)findViewById(R.id.listView);
        if(cuisine.equals("italian")){listItem=getResources().getStringArray(R.array.array_italian);}
        if(cuisine.equals("korean")){listItem=getResources().getStringArray(R.array.array_korean);}

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,listItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Send shop coordinate to map page
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapter.getItem(i);
                Intent eachShop = new Intent(restaurant.this,display_maps.class);
                eachShop.putExtra("name",value);
                startActivity(eachShop);
            }
        });
    }
}