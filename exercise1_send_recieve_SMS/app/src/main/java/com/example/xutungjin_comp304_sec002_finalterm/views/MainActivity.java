package com.example.xutungjin_comp304_sec002_finalterm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xutungjin_comp304_sec002_finalterm.R;
import com.example.xutungjin_comp304_sec002_finalterm.data.*;
import com.example.xutungjin_comp304_sec002_finalterm.viewmodels.MainActivityViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // ------------------------SMS------------------------
    private static final int SMS_RECEIVE_PERMISSION_REQUEST = 1;
    private PendingIntent sentPI, deliveredPI;
    private BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    private IntentFilter intentFilter;

    // Receive intents sent by sendBroadcast()
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getBaseContext(),intent.getExtras().getString("sms"),Toast.LENGTH_LONG).show();
        }
    };

    public static final String SENT = "SMS_SENT";
    public static final String DELIVERED = "SMS_DELIVERED";

    String companyName="default name";
    String stockQuote="default quote";

    // ------------------------DB------------------------
    Room_database db;
    MainActivityViewModel main_viewModel;

    Button insert_btn,display_btn;
    TextView display_txt;
    RadioGroup stock_group;
    RadioButton stock_radioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --------------SEND/GET SMS--------------
        // Request permissions
        ActivityCompat.requestPermissions(this,
                                            new String[]{Manifest.permission.RECEIVE_SMS,
                                            Manifest.permission.SEND_SMS,
                                            Manifest.permission.READ_PHONE_STATE},
                                            SMS_RECEIVE_PERMISSION_REQUEST);

        // An action to take in the future with same permission as your application
        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        // Intent to filter the action for SMS messages received
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        // Register the receiver
        registerReceiver(intentReceiver, intentFilter);

        // --------------ROOM DATABASE--------------
        // Binding to main activity view model
        main_viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        // Binding to main activity xml file
        insert_btn = (Button)findViewById(R.id.insert_button);
        display_btn = (Button)findViewById(R.id.display_button);
        display_txt = (TextView)findViewById(R.id.reviece_text);
        stock_group = (RadioGroup)findViewById(R.id.stock_group);

        insert_btn.setOnClickListener(this);
        display_btn.setOnClickListener(this);
    }

    // Button onclick events
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*
             * When the user clicks on Insert Stock Info button:
             *       Populate table StockInfo table with two rows (hardcode this).
             *       Put this code in the onClick event of Insert Stocks button.
             */
            case R.id.insert_button:

                // Create Room DB
                db = Room_database.getDbInstance(this.getApplicationContext());
                db.stock_DAO().nukeTable();

                // Insert hardcore data
                StockInfo amazon = new StockInfo("AMZN","Amazon",980);
                StockInfo google = new StockInfo("GOOGL","Google",970);
                StockInfo samsung = new StockInfo("SSNLF","Samsung",990);
                db.stock_DAO().insertStock(amazon);
                db.stock_DAO().insertStock(google);
                db.stock_DAO().insertStock(samsung);
                Toast.makeText(getBaseContext(), "Hardcore data inserted.",Toast.LENGTH_SHORT).show();
                break;

            //  When the user clicks on Display Stock Info button:
            case R.id.display_button:

                // a.Select the stock symbol from the RadioButton controls. [10 marks]
                int selectedId = stock_group.getCheckedRadioButtonId();
                stock_radioBtn = (RadioButton)findViewById(selectedId);
                String selectedStock = (String) stock_radioBtn.getText();

                // b.Retrieve the company name and stock quote from StockInfo table.
                List<StockInfo> l = db.stock_DAO().getAll();

                // c.Display the Stock Info in a text view
                for(int i=0;i<l.size();i++){
                    if(l.get(i).stockSymbol.equals(selectedStock)){
                        companyName = l.get(i).companyName;
                        stockQuote = String.valueOf(l.get(i).stockQuote);
                        display_txt.setText("Company name: "+companyName+"\nStock Quote: "+stockQuote);
                    }
                }

                // d.Send an SMS message with the company name and stock quote to a phone device
                sendMessage(companyName,stockQuote);
                break;
        }
    }

    public void sendMessage(String companyName,String stockQuote)
    {
        String message = "\n---------------------------------------------\n+" +
                         "Stock Info received:"+
                         "\nCompany Name: "+companyName+
                         "\nStock Quote: "+stockQuote;
        sendSMS(message);
    }

    // Sends an SMS message to another device
    private void sendSMS(String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("5554", null, message, sentPI, deliveredPI);
    }

    // Create the BroadcastReceiver when the SMS is sent
    @Override
    public void onResume() {
        super.onResume();
        smsSentReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                //Retrieve the current result code, as set by the previous receiver
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        // Create the BroadcastReceiver when the SMS is delivered
        smsDeliveredReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        // Register the two BroadcastReceivers
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
    }

    // Unregister the two BroadcastReceivers
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    // Unregister the receiver
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(intentReceiver);
    }
}