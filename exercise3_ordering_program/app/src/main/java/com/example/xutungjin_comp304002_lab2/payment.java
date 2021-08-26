package com.example.xutungjin_comp304002_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class payment extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup paymentGroup;
    private RadioButton paymentButton;
    private Button customerInfo;

    private cart shoppingCart;
    private JSONArray cartJson;

    private String selectedPayment="Credit card"; //default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        customerInfo = (Button) findViewById(R.id.customer_info_button);
        customerInfo.setOnClickListener(this);

        paymentGroup = findViewById(R.id.payment_group);
        paymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) {
                paymentButton = (RadioButton) findViewById(checkId);
                Toast.makeText(payment.this,"Saved: "+paymentButton.getText(),Toast.LENGTH_SHORT).show();
                selectedPayment = (String) paymentButton.getText();
            }
        });

        // Get intent values
        Intent intent = getIntent();

        // Cart array from order_preview activity
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

        shoppingCart=new cart(cartArray); // Got cart
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

    @Override
    public void onClick(View v) {
        Intent customerInfo = new Intent(payment.this,customer_info.class);

        // pass cart back to order_preview
        ArrayList<pizza> a =shoppingCart.getPizzaList();

        JSONArray j = new JSONArray();
        for(int i=0;i<a.size();i++){
            j.put(a.get(i).getJSONObject());
        }
        customerInfo.putExtra("jsonArray",j.toString());
        customerInfo.putExtra("payment",selectedPayment);
        payment.this.startActivity(customerInfo);
    }
}