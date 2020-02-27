package com.example.fingerprint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal,0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("" + errString);
    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Fingerprint Authentication failed.");

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("" + helpString);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        ((Activity) context).finish();
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);

    }

    private void update(String s) {

        TextView description = (TextView) ((Activity)context).findViewById(R.id.description);
        description.setTextColor(ContextCompat.getColor(context, R.color.errorText));
        description.setText(s);

    }

}
