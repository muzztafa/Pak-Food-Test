package com.example.pakfoodproductstest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_config {

    private static FirebaseUser user;
    FirebaseAuth mAuth;

    private Context mContext;

    private InvoicesAdapter mInvoicesAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Invoices> invoices, List<String> keys){

        mAuth= FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();

        mContext = context;
        mInvoicesAdapter = new InvoicesAdapter(invoices,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mInvoicesAdapter);

    }

    class InvoiceItemView extends RecyclerView.ViewHolder{
        private TextView mdate;
        private TextView minvoiceNo;
        private TextView mshopsName;
        private TextView mstatus;
        private TextView mmobileNo;
        private TextView mtotal_bill;
        private TextView mpaidAmount;
        private TextView mpaidDate;

        private TextView mtimeGen;
        private TextView mtimePay;

        private TextView mreceivedBy;



        private String key;


        public InvoiceItemView(ViewGroup parent){
           super(LayoutInflater.from(mContext).
                   inflate(R.layout.invoice_list_items_customer,parent,false));

           mdate = itemView.findViewById(R.id.date);
            minvoiceNo= itemView.findViewById(R.id.invoiceNo);
            mshopsName = itemView.findViewById(R.id.shopsName);
            mstatus = itemView.findViewById(R.id.status);
            mmobileNo = itemView.findViewById(R.id.mobileNo);
            mtotal_bill = itemView.findViewById(R.id.bill);
            mpaidAmount = itemView.findViewById(R.id.paidAmount);
            mpaidDate = itemView.findViewById(R.id.paidDate);

            mtimeGen =itemView.findViewById(R.id.genTime_textView);
            mtimePay =itemView.findViewById(R.id.timePay_textView);

            mreceivedBy = itemView.findViewById(R.id.receivedBy_textView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 //   if(user!=null){
                    Intent intent = new Intent(mContext, InvoiceDetailsActivity.class);
                    intent.putExtra("key",key);
                    intent.putExtra("shopsName",mshopsName.getText().toString());
                    intent.putExtra("date", mdate.getText().toString());
                    intent.putExtra("invoiceNo", minvoiceNo.getText().toString());
                    intent.putExtra("status",mstatus.getText().toString());
                    intent.putExtra("mobileNo", mmobileNo.getText().toString());
                    intent.putExtra("bill",mtotal_bill.getText().toString());
                    intent.putExtra("timePay",mtimePay.getText().toString());
                    intent.putExtra("timeGen",mtimeGen.getText().toString());

                    intent.putExtra("receivedBy",mreceivedBy.getText().toString());


                    intent.putExtra("paidAmount",mpaidAmount.getText().toString());
                    intent.putExtra("paidDate",mpaidDate.getText().toString());


                    mContext.startActivity(intent);
             //   }

                }
            });


        }

        public void bind(Invoices invoice, String key){
            mdate.setText("Date: "+invoice.getDate());
            minvoiceNo.setText("Invoice No: "+invoice.getInvoiceNo());
            mshopsName.setText(invoice.getShopsName());
            mstatus.setText(invoice.getStatus());
            mmobileNo.setText("Mobile No: "+invoice.getMobileNo());
            mtotal_bill.setText("Total Amount: "+invoice.getTotal_bill());
            mpaidAmount.setText("Paid Amount: "+invoice.getPaidAmount());
            mpaidDate.setText("Payment Date: "+invoice.getPaidDate());
            mtimePay.setText("Payment Time: "+invoice.getInvoicePayTime());
            mtimeGen.setText("Generation Time: "+invoice.getInvoiceGenTime());
            mreceivedBy.setText("Received By: "+invoice.getReceivedBy());
            this.key = key;

            if(mstatus.getText().toString().equals("UNPAID")){

                mpaidDate.setVisibility(View.INVISIBLE);
                mpaidAmount.setVisibility(View.INVISIBLE);
                mtimePay.setVisibility(View.INVISIBLE);
                mreceivedBy.setVisibility(View.INVISIBLE);
            }
            else{
                mpaidAmount.setVisibility(View.VISIBLE);
                mpaidAmount.setVisibility(View.VISIBLE);
                mtimePay.setVisibility(View.VISIBLE);

                mreceivedBy.setVisibility(View.VISIBLE);
            }

        if(mstatus.getText().equals("unpaid")||mstatus.getText().equals("UNPAID")||mstatus.getText().equals("Unpaid")){
            mstatus.setTextColor(Color.RED);}
        else{
        mstatus.setTextColor(Color.GREEN);
        }}

    }

    class InvoicesAdapter extends RecyclerView.Adapter<InvoiceItemView>{
        private List<Invoices> mInvoiceList;
        private List<String> mKeys;

        public InvoicesAdapter(List<Invoices> mInvoiceList, List<String> mKeys) {
            this.mInvoiceList = mInvoiceList;
            this.mKeys = mKeys;
        }


        @NonNull
        @Override
        public InvoiceItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new InvoiceItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull InvoiceItemView holder, int position) {
            holder.bind(mInvoiceList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mInvoiceList.size();
        }
    }





}
