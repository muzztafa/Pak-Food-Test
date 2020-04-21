package com.example.pakfoodproductstest;

import android.service.autofill.Dataset;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

import static java.util.Collections.reverse;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceRoot;
    private List<Invoices> invoices = new ArrayList<Invoices>();

    public interface DataStatus{
        void DataIsLoaded(List<Invoices> invoices, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceRoot = mDatabase.getReference("invoice");

    }

    public void readInvoices(final DataStatus dataStatus){
        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);

                    invoices.add(invoice);

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoicesCustomer(final String no,final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(no.equals(invoice.getMobileNo())){

                    invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoicesDriver(final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(invoice.getStatus().equals("UNPAID")){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_UNPAID_BILLS_TODAY_ADMIN(final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    if(invoice.getStatus().equals("UNPAID") && invoice.getDate().equals(currentDate)){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void readInvoices_PAID_BILLS_LASTWEEK_ADMIN(final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    String toDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String fromDate = invoice.getDate();

                    long diff = 0;

                    try {

                        //Convert to Date
                        Date startDate = df.parse(fromDate);
                        Calendar c1 = Calendar.getInstance();
                        //Change to Calendar Date
                        c1.setTime(startDate);

                        //Convert to Date
                        Date endDate = df.parse(toDate);
                        Calendar c2 = Calendar.getInstance();
                        //Change to Calendar Date
                        c2.setTime(endDate);

                        //get Time in milli seconds
                        long ms1 = c1.getTimeInMillis();
                        long ms2 = c2.getTimeInMillis();
                        //get difference in milli seconds
                        diff = ms2 - ms1;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //Find number of days by dividing the mili seconds
                    int diffInDays = (int) (diff / (24 * 60 * 60 * 1000));

                    if(invoice.getStatus().equals("PAID") && diffInDays<=7){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_PAID_BILLS_LASTMONTH_ADMIN(final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    String toDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String fromDate = invoice.getDate();

                    long diff = 0;

                    try {

                        //Convert to Date
                        Date startDate = df.parse(fromDate);
                        Calendar c1 = Calendar.getInstance();
                        //Change to Calendar Date
                        c1.setTime(startDate);

                        //Convert to Date
                        Date endDate = df.parse(toDate);
                        Calendar c2 = Calendar.getInstance();
                        //Change to Calendar Date
                        c2.setTime(endDate);

                        //get Time in milli seconds
                        long ms1 = c1.getTimeInMillis();
                        long ms2 = c2.getTimeInMillis();
                        //get difference in milli seconds
                        diff = ms2 - ms1;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //Find number of days by dividing the mili seconds
                    int diffInDays = (int) (diff / (24 * 60 * 60 * 1000));

                    if(invoice.getStatus().equals("PAID") && diffInDays<=31){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_TOTAL_UNPAID_BILLS_ADMIN(final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(invoice.getStatus().equals("UNPAID")){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_TOTAL_PAID_BILLS_ADMIN(final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(invoice.getStatus().equals("PAID")){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }








    public void readInvoices_UNPAID_BILLS_TODAY_CUSTOMER(final String no,final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    if(invoice.getStatus().equals("UNPAID") && invoice.getDate().equals(currentDate) && no.equals(invoice.getMobileNo())){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void readInvoices_PAID_BILLS_LASTWEEK_CUSTOMER(final String no,final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    String toDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String fromDate = invoice.getDate();

                    long diff = 0;

                    try {

                        //Convert to Date
                        Date startDate = df.parse(fromDate);
                        Calendar c1 = Calendar.getInstance();
                        //Change to Calendar Date
                        c1.setTime(startDate);

                        //Convert to Date
                        Date endDate = df.parse(toDate);
                        Calendar c2 = Calendar.getInstance();
                        //Change to Calendar Date
                        c2.setTime(endDate);

                        //get Time in milli seconds
                        long ms1 = c1.getTimeInMillis();
                        long ms2 = c2.getTimeInMillis();
                        //get difference in milli seconds
                        diff = ms2 - ms1;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //Find number of days by dividing the mili seconds
                    int diffInDays = (int) (diff / (24 * 60 * 60 * 1000));

                    if(invoice.getStatus().equals("PAID") && diffInDays<=7 && no.equals(invoice.getMobileNo())){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_PAID_BILLS_LASTMONTH_CUSTOMER(final String no,final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    String toDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String fromDate = invoice.getDate();

                    long diff = 0;

                    try {

                        //Convert to Date
                        Date startDate = df.parse(fromDate);
                        Calendar c1 = Calendar.getInstance();
                        //Change to Calendar Date
                        c1.setTime(startDate);

                        //Convert to Date
                        Date endDate = df.parse(toDate);
                        Calendar c2 = Calendar.getInstance();
                        //Change to Calendar Date
                        c2.setTime(endDate);

                        //get Time in milli seconds
                        long ms1 = c1.getTimeInMillis();
                        long ms2 = c2.getTimeInMillis();
                        //get difference in milli seconds
                        diff = ms2 - ms1;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //Find number of days by dividing the mili seconds
                    int diffInDays = (int) (diff / (24 * 60 * 60 * 1000));

                    if(invoice.getStatus().equals("PAID") && diffInDays<=31 && no.equals(invoice.getMobileNo())){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_TOTAL_UNPAID_BILLS_CUSTOMER(final String no,final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(invoice.getStatus().equals("UNPAID") && no.equals(invoice.getMobileNo())){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_TOTAL_PAID_BILLS_CUSTOMER(final String no,final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(invoice.getStatus().equals("PAID") && no.equals(invoice.getMobileNo())){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    //SEARCHING QUERIES

    public void readInvoices_SEARCH_NAME(final String name,final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(invoice.getShopsName().toLowerCase().equals(name.toLowerCase()) ){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_SEARCH_DATE(final String date,final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(invoice.getDate().equals(date)){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readInvoices_SEARCH_NAME_AND_DATE(final String name, final String date, final DataStatus dataStatus){

        mReferenceRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Invoices invoice = keyNode.getValue(Invoices.class);
                    if(invoice.getShopsName().toLowerCase().equals(name.toLowerCase()) && invoice.getDate().equals(date)){

                        invoices.add(invoice);
                    }
                    else if(!keys.isEmpty()){
                        keys.remove(keys.size()-1);
                    }

                }
                Collections.reverse(invoices);
                dataStatus.DataIsLoaded(invoices,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





























    public void addInvoices(Invoices invoices, final DataStatus dataStatus){
      String key=mReferenceRoot.push().getKey();
      mReferenceRoot.child(key).setValue(invoices)
              .addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      dataStatus.DataIsInserted();
                  }
              });
    }

    public void updateInvoices(String key, Invoices invoice, final DataStatus dataStatus){
    mReferenceRoot.child(key).setValue(invoice)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dataStatus.DataIsUpdated();
                }
            });


    }

    public void deleteInvoices(String key, final DataStatus dataStatus){
            mReferenceRoot.child(key).setValue(null)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dataStatus.DataIsDeleted();
                        }
                    });
        
    }
}
