package com.example.xutungjin_comp304002_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class customer_info extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Attributes
    private customer customerI;
    private order orderI;
    private address addressI;
    private cart shoppingCart;
    private JSONArray cartJson;
    private String payment_method;
    private String language="English"; //default
    private String city="Toronto"; //default
    private int age;

    // Layout
    private EditText name_in;
    private EditText mail_in;
    private EditText phone_in;
    private EditText street_in;
    private EditText post_in;

    private RadioGroup lan_ra;
    private RadioButton lan_btn;

    private NumberPicker age_in;
    private Spinner city_sp;
    private Button completeB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        // Get intent values
        Intent intent = getIntent();

        String s = intent.getStringExtra("jsonArray");
        try {
            cartJson = new JSONArray(s);
        } catch (
                JSONException e) {
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
        payment_method = intent.getStringExtra("payment");

        // EditText
        name_in=(EditText)findViewById(R.id.name_in);
        mail_in=(EditText)findViewById(R.id.mail_input);
        phone_in=(EditText)findViewById(R.id.phone_in);
        street_in=(EditText)findViewById(R.id.street_in);
        post_in=(EditText)findViewById(R.id.postal_in);

        // Spinner
        city_sp=(Spinner)findViewById(R.id.city_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_sp.setAdapter(adapter);
        city_sp.setOnItemSelectedListener(this);

        // Number Picker
        age_in = (NumberPicker) findViewById(R.id.age_in);
        age_in.setMinValue(1);
        age_in.setMaxValue(100);
        age_in.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                age = newVal;
            }
        });

        // Radios
        lan_ra = findViewById(R.id.language_group);
        lan_ra.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) {
                lan_btn = (RadioButton) findViewById(checkId);
                Toast.makeText(customer_info.this,"Saved: "+lan_btn.getText(),Toast.LENGTH_SHORT).show();
                language= (String)lan_btn.getText();
            }
        });

        // Button
        completeB=(Button)findViewById(R.id.customer_info_button);
    }

    // Spinner event
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        city = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),"Saved: "+city,Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

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

    // User Input validations
    private boolean validateEmailAdd(EditText email)
    {
       String inputemail = email.getText().toString();
       if(!inputemail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(inputemail).matches()){
           return  true;
       }else {
           Toast.makeText(customer_info.this,"InValid Email",Toast.LENGTH_LONG).show();
           return  false;
       }
    }

    private boolean validateAll(EditText e)
    {
        String inputemail = e.getText().toString();
        if(!inputemail.isEmpty()){
            return  true;
        }else {
            Toast.makeText(customer_info.this,"Form is uncompleted",Toast.LENGTH_LONG).show();
            return  false;
        }
    }

    public void completeEvent(View v)
    {

        if( validateAll(name_in) && validateAll(phone_in) && validateAll(street_in)
        && validateAll(post_in) &&validateEmailAdd(mail_in) )
        {
            addressI = new address(
                    street_in.getText().toString(),
                    city,post_in.
                    getText().toString());
            customerI = new customer(
                    name_in.getText().toString(),
                    phone_in.getText().toString(),
                    mail_in.getText().toString(),
                    addressI,
                    age,
                    language);

            orderI = new order(customerI,shoppingCart,payment_method);

            Intent complete_order = new Intent(customer_info.this,complete_order.class);
            complete_order.putExtra("complete_order",orderI);
            customer_info.this.startActivity(complete_order);
        }
    }
}