package id.co.ewalabs.nongki_nongki;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 12345; //semacam kunci
    SignInButton signInButton;
    Button btnOut,btnRevoke;
    RelativeLayout lProfil;
    TextView txNama,txEmail;
    ImageView imgProfile;
    String nama,email,url;

    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        txNama=findViewById(R.id.tx_nama);
        txEmail=findViewById(R.id.tx_email);
        imgProfile=findViewById(R.id.im_user);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        lProfil=findViewById(R.id.lprofil);
        btnOut=findViewById(R.id.btn_signout);
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        btnRevoke=findViewById(R.id.btn_revoke);
        btnRevoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revokeAccess();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            updateUI(true);
        }
    }



    private void updateUI(final boolean isSignedIn) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSignedIn) {
                    signInButton.setVisibility(View.GONE);
                    btnOut.setVisibility(View.VISIBLE);
                    btnRevoke.setVisibility(View.VISIBLE);
                    lProfil.setVisibility(View.VISIBLE);
                } else {
                    signInButton.setVisibility(View.VISIBLE);
                    btnOut.setVisibility(View.GONE);
                    btnRevoke.setVisibility(View.GONE);
                    lProfil.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("RESPON", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("SUKSES", "display name: " + acct.getDisplayName());
            nama= acct.getDisplayName().toString();
            email = acct.getEmail().toString();
            if (acct.getPhotoUrl() !=null){
                url  = acct.getPhotoUrl().toString();
            }

            Toast.makeText(this, "Gambar : "+url, Toast.LENGTH_LONG).show();

            txNama.setText(nama);
            txEmail.setText(email);
            Picasso.get().load(url).into(imgProfile);
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

}
