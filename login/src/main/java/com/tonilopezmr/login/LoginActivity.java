package com.tonilopezmr.login;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends SignInActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "VS8IpmwlBd7VOWqsmmrAtX6eS";
    private static final String TWITTER_SECRET = "bIojcMjUiH30qruY9fUW5sPQkYuZeKAb79gsTSXebHujN82m8j";

    private SignInButton signInButton;
    private Button signOutButton;
    private ImageView imageView;
    private TextView userName;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        imageView = (ImageView)findViewById(R.id.user_image);
        userName = (TextView)findViewById(R.id.user_name);
        userEmail = (TextView)findViewById(R.id.user_email);

        //twitter
        prepareLogin((TwitterLoginButton) findViewById(R.id.twitter_login_button));

        //facebook
        this.callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton =   (LoginButton) findViewById(R.id.login_button);
        prepareLogin(callbackManager, loginButton);

        //Google
        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        signOutButton = (Button)findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        //Common
        if (signInManager.hasConnectedOnPhone()) {
            signInButton.setEnabled(false);
            loginButton.setEnabled(false);
            twitterLoginButton.setEnabled(false);
            signOutButton.setEnabled(true);
            connect();
        }else{
            loginButton.setEnabled(true);
            twitterLoginButton.setEnabled(true);
            signInButton.setEnabled(true);
            signOutButton.setEnabled(false);
        }
    }

    @Override
    protected void onConnectionComplete(PersonProfile person) {
        if (person != null){
            Toast.makeText(getApplicationContext(), "User: "+ person.getId(), Toast.LENGTH_LONG).show();
            Picasso.with(getApplicationContext()).load(person.getImageUri()).into(imageView);
            userName.setText(person.getName());
            userEmail.setText(person.getEmail());
            signInButton.setEnabled(false);
            twitterLoginButton.setEnabled(false);
            loginButton.setEnabled(false);
            signOutButton.setEnabled(true);
        }
    }

    private void signOut(){
        disconnect();
        twitterLoginButton.setEnabled(true);
        signInButton.setEnabled(true);
        loginButton.setEnabled(true);
        signOutButton.setEnabled(false);
    }

    /**
     * Call this method inside onCreate once to get your hash key
     */
    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.tonilopezmr.login", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("SHA: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
