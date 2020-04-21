package com.example.pakfoodproductstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.core.Repo;

import java.util.List;

public class Reports extends AppCompatActivity {
    private String no;
    private Spinner spinner;
    private String s;

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        no=getIntent().getStringExtra("no");

        spinner = findViewById(R.id.spinner);




        mRecyclerView= findViewById(R.id.recyclerview_invoices);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);

                s= selectedItemText;


                //if no == null this means the admin has asked for reports
                if (no==null){



                    if(s.equals("Unpaid Bills For Today")){
                        new FirebaseDatabaseHelper().readInvoices_UNPAID_BILLS_TODAY_ADMIN(new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_DRIVER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });

                    }
                    else if(s.equals("Total Unpaid Bills")){

                        new FirebaseDatabaseHelper().readInvoices_TOTAL_UNPAID_BILLS_ADMIN(new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_DRIVER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
                    else if(s.equals("Total Paid Bills")){

                        new FirebaseDatabaseHelper().readInvoices_TOTAL_PAID_BILLS_ADMIN(new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_CUSTOMER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
                    else if(s.equals("Paid Bills For Last Week")){

                        new FirebaseDatabaseHelper().readInvoices_PAID_BILLS_LASTWEEK_ADMIN(new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_DRIVER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
                    else if(s.equals("Paid Bills For Last Month")){

                        new FirebaseDatabaseHelper().readInvoices_PAID_BILLS_LASTMONTH_ADMIN(new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_DRIVER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }


                }
                else{


                    if(s.equals("Unpaid Bills For Today")){
                        new FirebaseDatabaseHelper().readInvoices_UNPAID_BILLS_TODAY_CUSTOMER(no,new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_DRIVER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });

                    }
                    else if(s.equals("Total Unpaid Bills")){

                        new FirebaseDatabaseHelper().readInvoices_TOTAL_UNPAID_BILLS_CUSTOMER(no,new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_DRIVER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
                    else if(s.equals("Total Paid Bills")){

                        new FirebaseDatabaseHelper().readInvoices_TOTAL_PAID_BILLS_CUSTOMER(no,new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_CUSTOMER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
                    else if(s.equals("Paid Bills For Last Week")){

                        new FirebaseDatabaseHelper().readInvoices_PAID_BILLS_LASTWEEK_CUSTOMER(no,new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_DRIVER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
                    else if(s.equals("Paid Bills For Last Month")){

                        new FirebaseDatabaseHelper().readInvoices_PAID_BILLS_LASTMONTH_CUSTOMER(no,new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                new RecyclerView_config_DRIVER().setConfig(mRecyclerView, Reports.this, invoices, keys);


                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }

                }











            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Reports.this,"No Selection",Toast.LENGTH_LONG).show();
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.invoicelist_acivity_menu, menu);
        // if(user!=null){
        //   menu.getItem(0).setVisible(true);
        //  menu.getItem(1).setVisible(true);
        // }
        //else{
        //   menu.getItem(0).setVisible(false);
        //  menu.getItem(1).setVisible(false);
        // }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // if(user!=null){
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(true);
        menu.getItem(2).setVisible(false);

        //}
        //else{
        //  menu.getItem(0).setVisible(false);
        // menu.getItem(1).setVisible(false);
        // }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_invoice:
                // startActivity(new Intent(this, NewInvoiceActivity.class));
                // return true;
            case R.id.logout_id:

                invalidateOptionsMenu();
                startActivity(new Intent(this, SignInActivity_Admin.class));
                finish();return true;

        }
        return super.onOptionsItemSelected(item);
    }




}
