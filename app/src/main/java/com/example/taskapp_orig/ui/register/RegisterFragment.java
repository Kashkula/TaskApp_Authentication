package com.example.taskapp_orig.ui.register;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskapp_orig.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class RegisterFragment extends Fragment {
    protected EditText edNum, edCode;
    protected TextView textViewTimer, textViewCheck;
    protected Button btnNum, btnCode;
    protected FirebaseAuth mAuth;
    protected LinearLayout l1, l2;
    private String codeSent;
    protected boolean timerBoolean = false;

    protected CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initClickListener();
        onBack();
    }

    private void init(View view) {
        edNum = view.findViewById(R.id.number);
        edCode = view.findViewById(R.id.code);
        btnNum = view.findViewById(R.id.btn_phone);
        btnCode = view.findViewById(R.id.btn_login);
        l1 = view.findViewById(R.id.liner1);
        l2 = view.findViewById(R.id.liner2);
        textViewTimer = view.findViewById(R.id.textViewCountDownTimer);
        textViewCheck = view.findViewById(R.id.textViewCheck);
    }

    private void onBack() {
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    private void initClickListener() {
        btnNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerBoolean) {
                    textViewCheck.setVisibility(View.GONE);
                    timerBoolean = false;
                }
                if (edNum.getText().toString().trim().length() == 13) {
                    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {

                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);
                            codeSent = s;
                        }
                    };
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.VISIBLE);
                    sendVerificationCode(mCallbacks);
                    startTimer();
                } else
                    edNum.setError("Заполните строку!");
            }
        });
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifySignInCode();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                textViewTimer.setText("Попробуйте снова!");
                l2.setVisibility(View.GONE);
                l1.setVisibility(View.VISIBLE);
                textViewCheck.setVisibility(View.VISIBLE);
                timerBoolean = true;
            }
        }.start();
    }

    private void verifySignInCode() {
        String numCode = edCode.getText().toString();
        if (TextUtils.isEmpty(numCode)) {
            edCode.setError("Code is required");
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, numCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                            navController.navigateUp();
                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void sendVerificationCode(PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                edNum.getText().toString().trim(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                requireActivity(),               // Activity (for callback binding)
                mCallbacks);        //
    }
}