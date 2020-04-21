package com.example.pakfoodproductstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DriverView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public static String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view);

         name = getIntent().getStringExtra("name");



            mRecyclerView= findViewById(R.id.recyclerview_invoices);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setHasFixedSize(true);


            new FirebaseDatabaseHelper().readInvoicesDriver(new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                    new RecyclerView_config_DRIVER().setConfig(mRecyclerView, DriverView.this, invoices, keys);


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


        public String getName(){

        return this.name;
        }

}

