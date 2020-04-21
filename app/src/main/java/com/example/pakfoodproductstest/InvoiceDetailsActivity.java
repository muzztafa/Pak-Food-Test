package com.example.pakfoodproductstest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvoiceDetailsActivity extends AppCompatActivity {
    private EditText minvoice_no;
    private EditText mbill;
    private EditText mmobileNo;
    private EditText mshops_name;
   // private Button mupdate_invoice;
  //  private Button mback;
  //  private Button mdelete;

    private CardView mupdate_invoice;
    private CardView mback;
    private CardView mdelete;


    private String key;
    private String shopsName;
    private String invoiceNo;
    private String date;
    private String status;
    private String mobileNo;
    private String bill;
    private String timeGen;
    private String timePay;

    private String paidAmount;
    private String paidDate;

    private String receivedBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_details);

        final Context c = this;

        key= getIntent().getStringExtra("key");
        shopsName= getIntent().getStringExtra("shopsName");
        invoiceNo=getIntent().getStringExtra("invoiceNo");
        date = getIntent().getStringExtra("date");
        status=getIntent().getStringExtra("status");
        mobileNo=getIntent().getStringExtra("mobileNo");
        bill=getIntent().getStringExtra("bill");
        timeGen=getIntent().getStringExtra("timeGen");
        timePay=getIntent().getStringExtra("timePay");

        receivedBy=getIntent().getStringExtra("receivedBy");

        paidAmount=getIntent().getStringExtra("paidAmount");
        paidDate=getIntent().getStringExtra("paidDate");

        mmobileNo=findViewById(R.id.mobileNo_text2);
        minvoice_no=findViewById(R.id.invoiceNo_text);
        mbill=findViewById(R.id.bill_text);
        mshops_name=findViewById(R.id.shopsName_text);
        mupdate_invoice=findViewById(R.id.updateInvoice_btn);
       mback=findViewById(R.id.back_btn);
        mdelete=findViewById(R.id.deleteInvoice_btn);


        //setting values to the previous values so the user only changes the ones he wants to
        String arr_mob []=mobileNo.split(" ");
        String arr_bill []=bill.split(" ");
        String arr_invoice []=invoiceNo.split(" ");


            mmobileNo.setText(arr_mob[2]);
            minvoice_no.setText(arr_invoice[2]);
            mbill.setText(arr_bill[2]);
            mshops_name.setText(shopsName);



        mupdate_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);



                if(minvoice_no.getText().toString().isEmpty()){minvoice_no.setError("Required");}
                else if(mshops_name.getText().toString().isEmpty() || mshops_name.getText().toString()==null){mshops_name.setError("Required");}
                else if(mmobileNo.getText().toString().isEmpty() || mmobileNo.getText().toString().length()!=11){mmobileNo.setError(""); }
                else if(mbill.getText().toString().isEmpty()){mbill.setError("Required");}
                else  {

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                Invoices invoice= new Invoices();
                                invoice.setShopsName(mshops_name.getText().toString());
                                invoice.setTotal_bill(mbill.getText().toString());
                                invoice.setInvoiceNo(minvoice_no.getText().toString());
                                invoice.setMobileNo(mmobileNo.getText().toString());
                                invoice.setStatus(status);
                                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                invoice.setDate(currentDate);
                                invoice.setInvoiceGenTime(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));


                                String arr1[]=timePay.split(" ");
                                String arr2[]=paidAmount.split(" ");
                                String arr3[]=paidDate.split(" ");

                                String arr4[]=receivedBy.split( " ");


                               if(arr1.length==3) {invoice.setInvoicePayTime(arr1[2]);}
                                if(arr1.length==3){invoice.setPaidAmount(arr2[2]);}
                                if(arr1.length==3) {invoice.setPaidDate(arr3[2]);}
                                if(arr1.length==3) {invoice.setReceivedBy(arr4[2]);}

                                new FirebaseDatabaseHelper().updateInvoices(key, invoice, new FirebaseDatabaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {
                                        Toast.makeText(InvoiceDetailsActivity.this, "This record has been updated successfully",Toast.LENGTH_LONG).show();
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

                }}
        });



        mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.delete_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);


                if(mmobileNo.getText().toString().isEmpty() || mmobileNo.getText().toString().length()!=11){mmobileNo.setError("");}
                else if(minvoice_no.getText().toString().isEmpty()){minvoice_no.setError("Required");}
                else if(mbill.getText().toString().isEmpty()){mbill.setError("Required");}
                else {
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here


                                new FirebaseDatabaseHelper().deleteInvoices(key, new FirebaseDatabaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {

                                    }

                                    @Override
                                    public void DataIsDeleted() {
                                        Toast.makeText(InvoiceDetailsActivity.this, "This record has been deleted successfully",Toast.LENGTH_LONG).show();
                                        finish(); return;

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

            }}
        });






        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); return;
            }
        });
    }
}
