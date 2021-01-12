package com.factor.gymnasium.Activities;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.factor.gymnasium.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;
public class MembershipActivity extends AppCompatActivity implements PaymentResultListener {
    private static int AMMOUNT = 0;
    Toolbar toolbar;
    Dialog dialog;
    LinearLayout l1,l2,l3,l4;
    boolean buttonClick1=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        l4=findViewById(R.id.l4);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("Membership");
    }
    public void  one_monthmembeship(View view){
        buttonClick1=true;
        AMMOUNT=1130;
        if(buttonClick1){
            l1.setBackgroundColor(Color.RED);
            l2.setBackgroundColor(Color.GREEN);
            l3.setBackgroundColor(Color.GREEN);
            l4.setBackgroundColor(Color.GREEN);
            buttonClick1=false;
        }
        else {
            l1.setBackgroundColor(Color.GREEN);
            buttonClick1=true;
        }
//        l1.setBackgroundColor(Color.YELLOW);
    }
    public void  three_monthmembership(View view){
        buttonClick1=true;

        AMMOUNT=3390;
        if(buttonClick1){
            l2.setBackgroundColor(Color.RED);
            l1.setBackgroundColor(Color.GREEN);
            l3.setBackgroundColor(Color.GREEN);
            l4.setBackgroundColor(Color.GREEN);
            buttonClick1=false;
        }
        else {
            l2.setBackgroundColor(Color.GREEN);
            buttonClick1=true;
        }
//        l2.setBackgroundColor(Color.YELLOW);

    }
    public void  sixMonthMembership(View view){
        AMMOUNT=4390;
        buttonClick1=true;
        if(buttonClick1){
            l3.setBackgroundColor(Color.RED);
            l1.setBackgroundColor(Color.GREEN);
            l2.setBackgroundColor(Color.GREEN);
            l4.setBackgroundColor(Color.GREEN);
            buttonClick1=false;
        }
        else {
            l3.setBackgroundColor(Color.GREEN);
            buttonClick1=true;
        }
//        l3.setBackgroundColor(Color.YELLOW);

    }
    public void  OneYearMembership(View view){
        buttonClick1=true;
        AMMOUNT=5390;
        if(buttonClick1){
            l4.setBackgroundColor(Color.RED);
            l1.setBackgroundColor(Color.GREEN);
            l2.setBackgroundColor(Color.GREEN);
            l3.setBackgroundColor(Color.GREEN);
            buttonClick1=false;
        }
        else {
            l4.setBackgroundColor(Color.GREEN);
            buttonClick1 = true;
        }
//        l4.setBackgroundColor(Color.YELLOW);

    }
    public void Pay_Now(View view){
        if(AMMOUNT==0){
       Toast.makeText(MembershipActivity.this,"Please select membership ammount",Toast.LENGTH_LONG).show();
        }else{
            startPayment();
        }
    }

    private void startPayment() {
        final Context activity = MembershipActivity.this;
        final Checkout co = new Checkout();
//        rzp_live_vvoOs9BBgOVndU   //Live razor API Key
//        co.setKeyID("rzp_test_Dqd0xxkbTD0XWM ");
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            double total1=Double.parseDouble(String.valueOf(AMMOUNT));
            total1=total1*100;
            options.put("amount",total1);
            //  options.put("amount","100");
            JSONObject preFill = new JSONObject();
            options.put("prefill", preFill);
            co.open((Activity) activity, options);
        } catch (Exception e) {
            Toast.makeText(MembershipActivity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } }
    @Override
    public void onPaymentSuccess(String s) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.registration_successfull);
        builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent= new Intent(MembershipActivity.this,MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        dialog = builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        if(i== Checkout.NETWORK_ERROR){
            Toast.makeText(MembershipActivity.this, "Payment Failure: " + s, Toast.LENGTH_LONG).show();
        }else if(i== Checkout.INVALID_OPTIONS){
            Toast.makeText(MembershipActivity.this, "Payment Failure: " + s, Toast.LENGTH_LONG).show();
        }else if(i== Checkout.PAYMENT_CANCELED){
            Toast.makeText(MembershipActivity.this, "Payment Failure: " + s, Toast.LENGTH_LONG).show();
        }else if(i== Checkout.TLS_ERROR){
            Toast.makeText(MembershipActivity.this, "Payment Failure: " + s, Toast.LENGTH_LONG).show();

        }

    }
}