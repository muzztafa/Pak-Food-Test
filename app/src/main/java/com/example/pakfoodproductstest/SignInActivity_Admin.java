package com.example.pakfoodproductstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity_Admin extends AppCompatActivity {
    private EditText email;
    private EditText pw;

   private TextView switchCustomer;

   // private Button signIn;
    private CardView signIn;

    private ProgressBar progBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__admin);

        mAuth=FirebaseAuth.getInstance();



        email=findViewById(R.id.email_editText);
        pw=findViewById(R.id.passwod_editText2);

        signIn=findViewById(R.id.signIn_btn);

        progBar=findViewById(R.id.progressBar);
        progBar.setVisibility(View.GONE);

    switchCustomer=findViewById(R.id.switchCust_textview);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty())return;
                inProgress(true);
                mAuth.signInWithEmailAndPassword(email.getText().toString()
                ,pw.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignInActivity_Admin.this,"Logged in Successfully!",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignInActivity_Admin.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish(); return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(SignInActivity_Admin.this,"Login Failed!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


       String text = "Not an Admin? Sign in as CUSTOMER or DRIVER!";
        SpannableString ss = new SpannableString(text);
        ClickableSpan cp=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                    Intent intent = new Intent(SignInActivity_Admin.this,PhoneAuth.class);
              //  Intent intent = new Intent(SignInActivity_Admin.this,CustView.class);
               startActivity(intent);
                finish(); return;
            }
        };

        ClickableSpan cp1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(SignInActivity_Admin.this,SignInActivity_Driver.class);
                //  Intent intent = new Intent(SignInActivity_Admin.this,CustView.class);
                startActivity(intent);
                finish(); return;
            }
        };

        ss.setSpan(cp,25,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(cp1,37,44, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        switchCustomer.setText(ss);
        switchCustomer.setMovementMethod(LinkMovementMethod.getInstance());

    }


    private void inProgress(boolean x){
        if(x) {
            progBar.setVisibility(View.VISIBLE);
            signIn.setEnabled(false);
        }

        else{
            progBar.setVisibility(View.GONE);
            signIn.setEnabled(true);
        }
    }

    private boolean isEmpty(){
        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("REQUIRED!");
            return true;
        }

        if(TextUtils.isEmpty(pw.getText().toString())){
           pw.setError("REQUIRED!");
            return true;
        }
        return false;
    }
}
