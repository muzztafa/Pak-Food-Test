package com.example.pakfoodproductstest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;

    private TextView balance;
    private String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custview);

        mAuth = FirebaseAuth.getInstance();

        balance = findViewById(R.id.balance_textView);

        no= getIntent().getStringExtra("PhoneNo");
        //no ="03343916139";

        mRecyclerView= findViewById(R.id.recyclerview_invoices);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);


        new FirebaseDatabaseHelper().readInvoicesCustomer(no,new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                new RecyclerView_config_CUSTOMER().setConfig(mRecyclerView, CustView.this, invoices, keys);

                //setting up current balance each time data is laoded
                String str= balance.getText().toString();
                int temp=0;

                for (int i = 0; i < invoices.size(); i++){
                    if(!invoices.get(i).getStatus().equals("UNPAID") && !invoices.isEmpty()){
                        String a,b;
                        a=invoices.get(i).getPaidAmount();
                        b=invoices.get(i).getTotal_bill();
                        if(a!=null && b!=null){
                    int j=Integer.parseInt(a)- Integer.parseInt(b);
                    temp+=j;}}

                    else if(!invoices.isEmpty() && invoices.get(i).getStatus().equals("UNPAID") ){
                        int j = Integer.parseInt(invoices.get(i).getTotal_bill());
                        temp-=j;
                    }
                }
                balance.setText(""+temp);

                if(Integer.parseInt(balance.getText().toString())<0){
                    balance.setTextColor(Color.RED);
                }
                else{balance.setTextColor(Color.GREEN);}
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
        FirebaseUser user = mAuth.getCurrentUser();

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
        FirebaseUser user = mAuth.getCurrentUser();
       // if(user!=null){
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(true);
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
                startActivity(new Intent(this, NewInvoiceActivity.class));
                return true;
            case R.id.logout_id:
                mAuth.signOut();
                invalidateOptionsMenu();
                startActivity(new Intent(this, SignInActivity_Admin.class));
                finish();return true;
            case R.id.reports:
                Intent in=new Intent(CustView.this, Reports.class);
                in.putExtra("no",no);
                startActivity(in);


        }
        return super.onOptionsItemSelected(item);
    }
}
