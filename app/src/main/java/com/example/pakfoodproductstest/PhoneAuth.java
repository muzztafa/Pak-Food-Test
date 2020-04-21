package com.example.pakfoodproductstest;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PhoneAuth extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    EditText phoneNumber, codeEnter;
   Button nextBtn;
    //private CardView nextBtn;
    ProgressBar progressBar;
    TextView state;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;

    private TextView switchCustomer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneauth);

        fAuth = FirebaseAuth.getInstance();
        phoneNumber = findViewById(R.id.phone);
        codeEnter = findViewById(R.id.codeEnter);
        progressBar = findViewById(R.id.progressBar);
        nextBtn = findViewById(R.id.nextBtn);
        state = findViewById(R.id.state);

        switchCustomer = findViewById(R.id.authChange_textView);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificationInProgress) {
                    if (!phoneNumber.getText().toString().isEmpty() && phoneNumber.getText().toString().length() == 11) {

                        String phoneNum = phoneNumber.getText().toString().replaceFirst("0", "+92");
                        Log.d(TAG, "onClick: Phone NO -> " + phoneNum);
                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("Sending OTP..");
                        state.setVisibility(View.VISIBLE);
                        requestOTP(phoneNum);


                    } else {
                        phoneNumber.setError("Phone Number Is Not Valid");
                    }

                } else {
                    String userOTP = codeEnter.getText().toString();
                    if (!userOTP.isEmpty() && userOTP.length() == 6) {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, userOTP);
                        verifyAuth(credential);
                    } else {
                        codeEnter.setError("Valid OTP is required");
                    }
                }


            }

        });
        String text = "Not a Customer? Sign in as ADMIN or DRIVER!";
        SpannableString ss = new SpannableString(text);

        ClickableSpan cp=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(PhoneAuth.this,SignInActivity_Admin.class);
                //Intent intent = new Intent(SignInActivity_Admin.this,CustView.class);
                startActivity(intent);
                finish(); return;
            }
        };

        ClickableSpan cp1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(PhoneAuth.this,SignInActivity_Driver.class);
                //Intent intent = new Intent(SignInActivity_Admin.this,CustView.class);
                startActivity(intent);
                finish(); return;
            }
        };


        ss.setSpan(cp,27,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(cp1,36,43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        switchCustomer.setText(ss);
        switchCustomer.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PhoneAuth.this, "Authentication is successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PhoneAuth.this, CustView.class);
                    intent.putExtra("PhoneNo",phoneNumber.getText().toString());
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(PhoneAuth.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void requestOTP(final String phoneNum) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                verificationId = s;
                token = forceResendingToken;
                nextBtn.setText("Verify");
                verificationInProgress = true;

            }
            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(PhoneAuth.this, "OTP expired, re-request the OTP", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(PhoneAuth.this, "Cannot create account " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}





