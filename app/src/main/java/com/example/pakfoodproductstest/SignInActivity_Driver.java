package com.example.pakfoodproductstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity_Driver extends AppCompatActivity {
    private EditText email;
    private EditText pw;

    private TextView switchCustomer;
    private CardView signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__driver);
        email=findViewById(R.id.email_editText);
        pw=findViewById(R.id.passwod_editText2);

        signIn=findViewById(R.id.signIn_btn);


        switchCustomer=findViewById(R.id.switchCust_textview);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!email.getText().toString().isEmpty() && !pw.getText().toString().isEmpty()){
                    if(email.getText().toString().equals("driver1") && pw.getText().toString().equals("driver")){
                        Toast.makeText(SignInActivity_Driver.this,"Logged in successfully!",Toast.LENGTH_LONG).show();
                        //start a new intent

                        Intent in=new Intent(SignInActivity_Driver.this, DriverView.class);
                        in.putExtra("name",email.getText().toString());
                        startActivity(in);
                        finish();return;

                    }

                    else if(email.getText().toString().equals("driver2") && pw.getText().toString().equals("driver")){
                        Toast.makeText(SignInActivity_Driver.this,"Logged in successfully!",Toast.LENGTH_LONG).show();
                        //start a new intent)
                        Intent in=new Intent(SignInActivity_Driver.this, DriverView.class);
                        in.putExtra("name",email.getText().toString());
                        startActivity(in);
                        finish();return;
                }
                    else {Toast.makeText(SignInActivity_Driver.this,"Wrong id or password!", Toast.LENGTH_LONG).show();}
                }

                else {email.setError("");pw.setError("");}
            }
        });



        String text = "Not an Admin? Sign in as CUSTOMER or ADMIN!";
        SpannableString ss = new SpannableString(text);

        ClickableSpan cp1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(SignInActivity_Driver.this,PhoneAuth.class);
                //  Intent intent = new Intent(SignInActivity_Admin.this,CustView.class);
                startActivity(intent);
                finish(); return;
            }
        };

        ClickableSpan cp2=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(SignInActivity_Driver.this,SignInActivity_Admin.class);
                //  Intent intent = new Intent(SignInActivity_Admin.this,CustView.class);
                startActivity(intent);
                finish(); return;
            }
        };

        ss.setSpan(cp1,25,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(cp2,37,43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        switchCustomer.setText(ss);
        switchCustomer.setMovementMethod(LinkMovementMethod.getInstance());

    }
    }

