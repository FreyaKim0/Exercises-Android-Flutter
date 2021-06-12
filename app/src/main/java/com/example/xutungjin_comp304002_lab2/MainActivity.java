package com.example.xutungjin_comp304002_lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Attributes
    private cart shoppingCart;
    private JSONArray cartJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingCart=new cart(new ArrayList<pizza>());
    }

    // Convert picture to byte
    public byte[] toByte(int drawableI){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),drawableI);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // Create options menu control
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pizza_menu,menu);
        return true;
    }

    // Create pizza items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.canadian_pizza:
                openCanadianPizza("Canadian Pizza","Cheese / Tomatoes / Olives",toByte(R.drawable.p1));
                return true;
            case R.id.chicken_caesar:
                openCanadianPizza("Chicken Caesar","Chicken / Mushroom / Olives",toByte(R.drawable.p2));
                return true;
            case R.id.hawaiian_pizza:
                openCanadianPizza("Hawaiian Pizza","Pineapple / bacon / Cheese",toByte(R.drawable.p3));
                return true;
            case R.id.veggie_lovers:
                openCanadianPizza("Veggie Lovers","Broccoli / Beans / Pepper",toByte(R.drawable.p4));
                return true;
            case R.id.smokey_maple_bacon:
                openCanadianPizza("Smokey Maple Bacon","Bacon / Beans / Mapple syrup",toByte(R.drawable.p5));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Create pizzas' intent
    public void openCanadianPizza(String name,String toppings,byte[] b){
        Intent eachPizza = new Intent(MainActivity.this,Canadian_pizza.class);

        // Parse cart to json for intent
        ArrayList<pizza> a;
        a=shoppingCart.getPizzaList();
        JSONArray j = new JSONArray();
        for(int i=0;i<a.size();i++){
            j.put(a.get(i).getJSONObject());
        }

        eachPizza.putExtra("jsonArray",j.toString());
        eachPizza.putExtra("name",name);
        eachPizza.putExtra("toppings",toppings);
        eachPizza.putExtra("picture",b);
        startActivity(eachPizza);
    }
}