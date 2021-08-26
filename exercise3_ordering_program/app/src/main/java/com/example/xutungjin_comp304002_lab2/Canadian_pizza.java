package com.example.xutungjin_comp304002_lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Canadian_pizza extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,View.OnClickListener {

    private pizza thisPizza;
    private String size="small";  //default
    private String dough="thin";   //default

    private cart shoppingCart;
    private JSONArray cartJson;

    private ImageView pic ;
    private Button keepShopping;
    private Spinner size_sp;

    private RadioGroup doughGroup;
    private RadioButton doughButton;

    private CheckBox addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canadian_pizza);

        keepShopping = (Button) findViewById(R.id.keep_shopping);
        keepShopping.setOnClickListener(this);

        // Get intent values
        Intent intent = getIntent();

        // name
        String name =intent.getStringExtra("name");
        TextView pizza_name = (TextView) findViewById(R.id.pizza_name);
        pizza_name.setText(name);

        // toppings
        String toppings = intent.getStringExtra("toppings");
        TextView pizza_toppings = (TextView) findViewById(R.id.pizza_toppings);
        pizza_toppings.setText(toppings);

        // jsonArray
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
        shoppingCart=new cart(cartArray); // Got cart from choose_type activity

        // picture
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        ImageView image = (ImageView) findViewById(R.id.pizza_picture);
        image.setImageBitmap(bmp);

        // Add to cart
        addToCart =(CheckBox)findViewById(R.id.add_to_cart);
        addToCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b){
                    thisPizza = new pizza(name,size,dough);
                    shoppingCart.add(thisPizza);
                    Toast.makeText(Canadian_pizza.this,"Add to cart: "+thisPizza.toString(),Toast.LENGTH_SHORT).show();
                }
                else{
                    shoppingCart.delete(thisPizza);
                    Toast.makeText(Canadian_pizza.this,"Deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Spinner
        size_sp=(Spinner)findViewById(R.id.size_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size_sp.setAdapter(adapter);
        size_sp.setOnItemSelectedListener(this);

        // Set Radio groups' events
        doughGroup = findViewById(R.id.language_group);
        doughGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) {
                doughButton = (RadioButton) findViewById(checkId);
                Toast.makeText(Canadian_pizza.this,"Save size: "+doughButton.getText(),Toast.LENGTH_SHORT).show();
                dough=(String)doughButton.getText();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        size = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),"Saved: "+size,Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }


    @Override
    public void onClick(View v) {
        Intent order_preview = new Intent(Canadian_pizza.this,order_preview.class);

        // pass cart back to order_preview
        ArrayList<pizza> a =shoppingCart.getPizzaList();

        JSONArray j = new JSONArray();
        for(int i=0;i<a.size();i++){
            j.put(a.get(i).getJSONObject());
        }

        order_preview.putExtra("jsonArray",j.toString());
        Canadian_pizza.this.startActivity(order_preview);
    }

     public static pizza convertJSONToPizza(JSONObject json) throws JSONException {
        if(json==null){
            return null;
        }
        String type = (String)json.get("type");
        String size = (String)json.get("size");
        String dough = (String)json.get("dough");
        pizza result = new pizza(type,size,dough);
        return result;
     }

}