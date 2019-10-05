package com.example.pitneybowes.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pitneybowes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.zxing.Result;



import java.util.concurrent.TimeUnit;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class Signup extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks ;
    String mVerificationId;


    private TextInputLayout mCode, mCode2, mPhoneNumber , mEmail , mPassword , mConfirmPassword;
    private TextView mSignUp, mLogin;
    private View flag;
    private TextView mText1, mText2 , textVerify1 , textVerify2;
    private TextView mCreate, mAccount ;
    private ImageView icon;
    int userLogged = 0;


    //    private static final int REQUEST_CAMERA = 1;
//    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseApp.initializeApp(this);


        icon = findViewById(R.id.scrollback);
        mCode =findViewById(R.id.edit_text_name);
        mCode2 = findViewById(R.id.edit_text_otp);
        mCreate = findViewById(R.id.title_create);
        mAccount = findViewById(R.id.title_account);
        mText1 = findViewById(R.id.text1);
        mText2 = findViewById(R.id.text2);
        mEmail = findViewById(R.id.edit_text_email);
        mPassword = findViewById(R.id.edit_text_password);
        mConfirmPassword = findViewById(R.id.edit_text_password_confirm);
        textVerify1 = findViewById(R.id.text_verify1);
        textVerify2 = findViewById(R.id.text_verify2);
        mPhoneNumber = findViewById(R.id.edit_text_phone);
        mSignUp = findViewById(R.id.sign_up_button);
        textVerify1.setVisibility(View.GONE);
        textVerify2.setVisibility(View.GONE);
        icon.setVisibility(View.INVISIBLE);





        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
                Log.i("Verification Complete", phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                e.printStackTrace();

            }

            @Override
            public void onCodeSent(String VerificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(VerificationId, forceResendingToken);
                Log.i("OnCodeSent", "" + VerificationId);
                mVerificationId= VerificationId;
            }
        };

        mSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mSignUp.setText("Verify");
//                mCode.getEditText().setText("");

                mText1.setText("Go Back");
                mText2.setVisibility(View.GONE);
                mCreate.setText("OTP");
                mAccount.setText("verify");
                icon.setVisibility(View.GONE);
                mCode.setVisibility(View.VISIBLE);
                mPhoneNumber.setVisibility(View.GONE);
                mEmail.setVisibility(View.GONE);
                mPassword.setVisibility(View.GONE);
                mConfirmPassword.setVisibility(View.GONE);


                mCode.setVisibility(View.GONE);
                mCode2.setVisibility(View.VISIBLE);



                if(mVerificationId!= null)
                {
                    Log.i("DEBUG", mCode2.getEditText().getText().toString());
                    verifyPhoneNumberWithCode(mVerificationId,mCode2.getEditText().getText().toString());
                }
                else
                    startPhoneNumberVerification();
            }
        });
        mText2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                userLogged = 1;

                mSignUp.setText("Log In");

                mText1.setText("Don't have an account?");
                mText2.setText("Sign Up");
                mCreate.setText("Log in");
                mAccount.setVisibility(View.VISIBLE);
                mCode.setVisibility(View.VISIBLE);
                mPhoneNumber.setVisibility(View.GONE);





//                if(mVerificationId!= null)
//                {
//                    Log.i("DEBUG", mCode.getText().toString());
//                    verifyPhoneNumberWithCode(mVerificationId,mCode.getText().toString());
//                }
//                else
//                    startPhoneNumberVerification();


            }
        });

//        mScannerView = new ZXingScannerView(this);
//        setContentView(mScannerView);
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

    }


    private void verifyPhoneNumberWithCode(String verificationId , String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful())
                {

                    textVerify1.setVisibility(View.VISIBLE);
                    textVerify2.setVisibility(View.VISIBLE);
                    textVerify1.setText("Verification failed! ");
                    textVerify1.setText("Send OTP again");

                    mSignUp.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {


//                            Intent intent = new Intent(Registration.this,Registration.class);
//                            startActivity(intent);
//                            finish();
                        }
                    });

                }
                if(task.isSuccessful())
                    userIsLoggedIn();

            }
        });
    }

    private void userIsLoggedIn()
    {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            mCreate.setText("OTP");
            mAccount.setText("verified");

            mSignUp.setText("Next");
            textVerify1.setVisibility(View.VISIBLE);
            textVerify1.setText("Verification Successful!");


//            mContinue.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if(userLogged==1)
//                    {
//
//                    }
//                    else {
//
//                        }
                }
//            });
//        }
    }

    private void startPhoneNumberVerification()
    {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                mPhoneNumber.getEditText().getText().toString(),

                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);

//        mPhoneNumber.getEditText().setText("");
    }

}






//    @Override
//    public void onResume() {
//        super.onResume();
//
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
//
//                if(mScannerView == null) {
//                    mScannerView = new ZXingScannerView(this);
//                    setContentView(mScannerView);
//                }
//                mScannerView.setResultHandler(this);
//                mScannerView.startCamera();
//        }
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mScannerView.stopCamera();
//    }
//
//    @Override
//    public void handleResult(Result rawResult) {
//
//        final String result = rawResult.getText();
//        Log.d("QRCodeScanner", rawResult.getText());
//        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mScannerView.resumeCameraPreview(Signup.this);
//            }
//        });
//        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
//                startActivity(browserIntent);
//            }
//        });
//        builder.setMessage(rawResult.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();
//    }
//}
