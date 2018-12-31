package com.avardonigltd.mobilemedicalaid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avardonigltd.mobilemedicalaid.R;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = new Intent(PaymentActivity.this,WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_URL,"https://checkout.paystack.com/9u8g63h6xambcod");
        startActivityForResult(intent,234);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (requestCode == 234){
            Log.i("TAG","it is successful");
        }
    }
}
