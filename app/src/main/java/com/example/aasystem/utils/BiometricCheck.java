package com.example.aasystem.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class BiometricCheck {

    Context context;
    String TAG = BiometricCheck.class.getCanonicalName();
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
   BiometricListener listener;

    public BiometricCheck(Context context, BiometricListener listener){
        this.context = context;
        this.listener = listener;
        checkBiometric();
    }

    private void checkBiometric() {

        BiometricManager biometricManager = BiometricManager.from(context);
        String error;
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(TAG, "App can authenticate using biometrics.");
                showBiometricDialog();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                error = "No biometric features available on this device";
                showToast(error);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                error = "Biometric features are currently unavailable.";
                showToast(error);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                error = "The user hasn't associated any biometric credentials with their account.";
                showToast(error);
                break;
        }
    }

    private void showBiometricDialog() {
        //initialize everything needed for authentication
        initBiometrics();

        //show biometric dialog for authentication
        biometricPrompt.authenticate(promptInfo);
    }

    private void initBiometrics() {
        executor = ContextCompat.getMainExecutor(context);

        biometricPrompt = new BiometricPrompt((FragmentActivity) context, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                showToast("Authentication error: " + errString);
                listener.onFailed();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                listener.onSuccess();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                showToast("Authentication failed");
                listener.onFailed();
            }
        });

        //create prompt dialog
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build();
    }


    //show toast to user
    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
