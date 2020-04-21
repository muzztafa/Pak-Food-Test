package com.example.pakfoodproductstest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    private EditText mpaymentamount;

    private Button paybtn;
   // private CardView paybtn;

    private TextView mshopsName;
    private TextView minvoiceNo;
    private TextView mdate;
    private TextView mstatus;
    private TextView mmobileNo;
    private TextView mbill;
    private TextView mtimeGen;
    private TextView mtimePay;


    private String key;
    private String shopsName;
    private String invoiceNo;
    private String date;
    private String status;
    private String mobileNo;
    private String bill;
    private String timeGen;
    private String timePay;

    private String nameDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final Context c = this;

        mpaymentamount = findViewById(R.id.pay_editText);
        paybtn = findViewById(R.id.pay_button);

        key= getIntent().getStringExtra("key");
      //  Toast.makeText(PaymentActivity.this, key.toString(),Toast.LENGTH_LONG ).show();
       // Toast.makeText(PaymentActivity.this, key.toString(),Toast.LENGTH_LONG ).show();



        shopsName= getIntent().getStringExtra("shopsName");
        invoiceNo=getIntent().getStringExtra("invoiceNo");
        date = getIntent().getStringExtra("date");
        status=getIntent().getStringExtra("status");
        mobileNo=getIntent().getStringExtra("mobileNo");
        bill=getIntent().getStringExtra("bill");
        timeGen=getIntent().getStringExtra("timeGen");
        timePay=getIntent().getStringExtra("timePay");

        nameDriver=getIntent().getStringExtra("nameDriver");

        //Toast.makeText(PaymentActivity.this,nameDriver,Toast.LENGTH_LONG).show();


        mshopsName = findViewById(R.id.shopsName);
        minvoiceNo = findViewById(R.id.invoiceNo);
        mdate = findViewById(R.id.date);
        mstatus = findViewById(R.id.status);
        mmobileNo = findViewById(R.id.mobileNo);
        mbill = findViewById(R.id.bill);
        mtimeGen=findViewById(R.id.timeGen_textView);
        mtimePay=findViewById(R.id.timePay_textView);


        mshopsName.setText(shopsName);
        minvoiceNo.setText(invoiceNo);
        mdate.setText(date);
        mstatus.setText(status);
        mmobileNo.setText(mobileNo);
        mbill.setText(bill);
        mtimeGen.setText(timeGen);

        //Toast.makeText(this, mtimePay.getText().toString(),Toast.LENGTH_LONG).show();
        if(mtimePay.getText().toString().equals("") || mtimePay.getText()==null || mtimePay.getText().toString().equals("TextView")){

            mtimePay.setVisibility(View.INVISIBLE);
        }
        else{mtimePay.setVisibility(View.VISIBLE);}

        mstatus.setTextColor(Color.RED);
        if(status.equals("PAID")){
            paybtn.setEnabled(false);
        }

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mpaymentamount == null || mpaymentamount.getText().toString().equals("")){
                    mpaymentamount.setError("Field cant be blank!");
                    return;

                }

                if(mpaymentamount.getText().toString().equals("0")){
                    mpaymentamount.setError("Field cant be zero!");
                    return;

                }
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(PaymentActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.payment_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(PaymentActivity.this);
                alertDialogBuilderUserInput.setView(mView);


                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                final Invoices invoice= new Invoices();
                             //   invoice.setShopsName(shopsName);
                             //   invoice.setTotal_bill(bill);
                              //  invoice.setInvoiceNo(invoiceNo);
                              //  invoice.setMobileNo(mobileNo);


                                //setting values to the previous values so the user only changes the ones he wants to
                                String arr_mob []=mobileNo.split(" ");
                                String arr_bill []=bill.split(" ");
                                String arr_invoice []=invoiceNo.split(" ");
                                String arr_date []=date.split(" ");
                                String arr_timeGen [] = timeGen.split(" ");

                                invoice.setMobileNo(arr_mob[2]);
                                invoice.setInvoiceNo(arr_invoice[2]);
                                invoice.setTotal_bill(arr_bill[2]);
                                invoice.setDate(arr_date[1]);
                                invoice.setShopsName(shopsName);
                                invoice.setInvoiceGenTime(arr_timeGen[2]);
                                invoice.setInvoicePayTime(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                invoice.setReceivedBy(nameDriver);




                                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                invoice.setStatus("PAID");
                                invoice.setPaidDate(currentDate);
                                invoice.setPaidAmount(mpaymentamount.getText().toString());


                                new FirebaseDatabaseHelper().updateInvoices(key, invoice, new FirebaseDatabaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {
                                        Toast.makeText(PaymentActivity.this, "PAYMENT is made successfully!",Toast.LENGTH_LONG).show();
                                      finish(); return;
                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
    }
}
