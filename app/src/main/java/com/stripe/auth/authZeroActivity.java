package com.stripe.auth;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class authZeroActivity extends AppCompatActivity implements View.OnClickListener {

    Auth0 mAccount;
    Lock mLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_zero);
        mAccount = new Auth0(this);
        mAccount.setOIDCConformant(true);

        ((Button) findViewById(R.id.sign_in_hosted)).setOnClickListener(this);

        mLock = Lock.newBuilder(mAccount, callback)
                .withScheme("stripeshop")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .loginAfterSignUp(true)
                .build(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLock.onDestroy(this);
        mLock = null;
    }

    private void signInHosted() {
        startActivity(mLock.newIntent(this));
    }

    private LockCallback callback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(Credentials credentials) {
            //Authenticated
        }

        @Override
        public void onCanceled() {
            //User pressed back
        }

        @Override
        public void onError(LockException error) {
            //Exception occurred
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_hosted:
                signInHosted();
            case R.id.sign_in_google:
                // do something
            case R.id.sign_out_authzero:
                // do something
            default:
                // do nothing
        }
    }
}
