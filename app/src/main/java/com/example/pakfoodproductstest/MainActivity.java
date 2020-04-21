package com.example.pakfoodproductstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;
    private  EditText name;
    private EditText date;

    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchButton=findViewById(R.id.search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MainActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.search_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilderUserInput.setView(mView);



                name=mView.findViewById(R.id.name_search);
                date= mView.findViewById(R.id.date_search);







                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here


                                    //searching by name
                                    if (!name.getText().toString().equals("") && date.getText().toString().equals("")) {
                                        mRecyclerView = findViewById(R.id.recyclerview_invoices);
                                        //  LinearLayoutManager manager = new LinearLayoutManager(this);
                                        //  mRecyclerView.setLayoutManager(manager);
                                        //   mRecyclerView.setHasFixedSize(true);
                                        new FirebaseDatabaseHelper().readInvoices_SEARCH_NAME(name.getText().toString(), new FirebaseDatabaseHelper.DataStatus() {
                                            @Override
                                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                                new RecyclerView_config().setConfig(mRecyclerView, MainActivity.this, invoices, keys);
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



                                    //searching by date
                                    else if (name.getText().toString().equals("") && !date.getText().toString().equals("")) {
                                        mRecyclerView = findViewById(R.id.recyclerview_invoices);
                                        //  LinearLayoutManager manager = new LinearLayoutManager(this);
                                        //  mRecyclerView.setLayoutManager(manager);
                                        //   mRecyclerView.setHasFixedSize(true);
                                        new FirebaseDatabaseHelper().readInvoices_SEARCH_DATE(date.getText().toString(), new FirebaseDatabaseHelper.DataStatus() {
                                            @Override
                                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                                new RecyclerView_config().setConfig(mRecyclerView, MainActivity.this, invoices, keys);
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




                                //searching by both
                                    else if(!name.getText().toString().equals("") && !date.getText().toString().equals("")){
                                    mRecyclerView= findViewById(R.id.recyclerview_invoices);
                                    //  LinearLayoutManager manager = new LinearLayoutManager(this);
                                    //  mRecyclerView.setLayoutManager(manager);
                                    //   mRecyclerView.setHasFixedSize(true);
                                    new FirebaseDatabaseHelper().readInvoices_SEARCH_NAME_AND_DATE(name.getText().toString(),date.getText().toString(),new FirebaseDatabaseHelper.DataStatus() {
                                        @Override
                                        public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                            new RecyclerView_config().setConfig(mRecyclerView,MainActivity.this, invoices, keys);
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
                            else{
                                Toast.makeText(MainActivity.this,"Not criteria defined for searching.",Toast.LENGTH_LONG).show();
                                    }
                            }


                        })

                        .setNegativeButton("Display All",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        mRecyclerView= findViewById(R.id.recyclerview_invoices);
                                        //  LinearLayoutManager manager = new LinearLayoutManager(this);
                                        //  mRecyclerView.setLayoutManager(manager);
                                        //   mRecyclerView.setHasFixedSize(true);
                                        new FirebaseDatabaseHelper().readInvoices(new FirebaseDatabaseHelper.DataStatus() {
                                            @Override
                                            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                                                new RecyclerView_config().setConfig(mRecyclerView,MainActivity.this, invoices, keys);
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
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }});



        mAuth = FirebaseAuth.getInstance();

        mRecyclerView= findViewById(R.id.recyclerview_invoices);
      //  LinearLayoutManager manager = new LinearLayoutManager(this);
      //  mRecyclerView.setLayoutManager(manager);
     //   mRecyclerView.setHasFixedSize(true);
        new FirebaseDatabaseHelper().readInvoices(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Invoices> invoices, List<String> keys) {
                new RecyclerView_config().setConfig(mRecyclerView,MainActivity.this, invoices, keys);
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
            menu.getItem(0).setVisible(true);
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
                startActivity(new Intent(this, Reports.class));

        }
        return super.onOptionsItemSelected(item);
    }
}
