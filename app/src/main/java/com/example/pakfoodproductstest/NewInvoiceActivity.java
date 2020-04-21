package com.example.pakfoodproductstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewInvoiceActivity extends AppCompatActivity {

    private EditText invoice_no;
    private EditText bill;
    private EditText shops_name;
    private EditText mobile_no;
   // private Button add_invoice;
   // private Button back;

    private CardView add_invoice;
    private CardView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_invoice);
        invoice_no=findViewById(R.id.invoiceNo_text);
        bill=findViewById(R.id.bill_text);
        mobile_no=findViewById(R.id.mobileNo_text2);
        shops_name=findViewById(R.id.shopsName_text);
        add_invoice=findViewById(R.id.updateInvoice_btn);
        back=findViewById(R.id.back_btn);

        add_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(mobile_no.getText().toString().isEmpty() || mobile_no.getText().toString().length()!=11){mobile_no.setError("");}
                if(invoice_no.getText().toString().isEmpty()){invoice_no.setError("Required");}
                if(bill.getText().toString().isEmpty()){bill.setError("Required");}
                else {
                Invoices invoice = new Invoices();
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                //String currentTime = Calendar.getInstance().getTime().toString();

                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                invoice.setDate(currentDate);
                invoice.setInvoiceNo(invoice_no.getText().toString());
                invoice.setShopsName(shops_name.getText().toString());
                invoice.setStatus("UNPAID");
                invoice.setMobileNo(mobile_no.getText().toString());
                invoice.setTotal_bill(bill.getText().toString());

                //creating paid date and paid amount with null values bcs we dont need to show them right now

                invoice.setPaidAmount("");
                invoice.setPaidDate("");

                //creating Time variables in our database
                invoice.setInvoiceGenTime(currentTime);
                invoice.setInvoicePayTime("");

                //adding driver to the database
                invoice.setReceivedBy("");

                new FirebaseDatabaseHelper().addInvoices(invoice, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewInvoiceActivity.this, "This invoice has been added successfully.", Toast.LENGTH_LONG).show();
                        finish(); return;
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

            }}
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();  return;
            }
        });
    }
}
