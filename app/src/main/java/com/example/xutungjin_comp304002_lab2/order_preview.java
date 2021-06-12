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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class order_preview extends AppCompatActivity implements View.OnClickListener{

    // Layouts
    private TextView show_order;


    private Button payment;

    // Attributes
    private cart shoppingCart;
    private JSONArray cartJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_preview);

        show_order= (TextView) findViewById(R.id.show_order);
        payment =  (Button) findViewById(R.id.payment);
        payment.setOnClickListener(this);

        // Get intent values from Canadian_pizza activity
        Intent intent = getIntent();

        String s = intent.getStringExtra("jsonArray");
        try {
            cartJson = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<pizza> cartArray=new ArrayList<pizza>();
        if(cartJson !=null){
            for(int i =0;i<cartJson.length();i++){
                try {
                    pizza temp = convertJSONToPizza(cartJson.getJSONObject(i));
                    cartArray.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        shoppingCart=new cart(cartArray); // Got cart instance
        show_order.setText(shoppingCart.toString());
    }

    public pizza convertJSONToPizza(JSONObject json) throws JSONException {
        if(json==null){
            return null;
        }
        String type = (String)json.get("type");
        String size = (String)json.get("size");
        String dough = (String)json.get("dough");
        pizza result = new pizza(type,size,dough);
        return result;
    }

    // --------option menu start---------
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

    // Create items
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
        Intent eachPizza = new Intent(order_preview.this,Canadian_pizza.class);

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
    // --------option menu End---------

    @Override
    public void onClick(View v) {
        Intent payment = new Intent(order_preview.this,payment.class);

        // pass cart back to order_preview
        ArrayList<pizza> a =shoppingCart.getPizzaList();

        JSONArray j = new JSONArray();
        for(int i=0;i<a.size();i++){
            j.put(a.get(i).getJSONObject());
        }
        payment.putExtra("jsonArray",j.toString());
        order_preview.this.startActivity(payment);
    }
}