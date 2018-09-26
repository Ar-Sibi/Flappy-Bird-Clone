package squad.loser.the.flappybird;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    public TextView newAccount;
    public TextInputEditText usernameText;
    public TextInputEditText passwordText;
    public TextInputLayout username_layout;
    public TextInputLayout password_layout;
    public String signIn_username = null;
    private FirebaseAuth myAuth;
    public String signIn_password = null;
    public Boolean check_internet = false;
    public ProgressBar myProgress;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        check_internet = isNetworkAvaliable(this);
        newAccount = findViewById(R.id.newAccount);
        usernameText = findViewById(R.id.username);
        myAuth = FirebaseAuth.getInstance();
        myProgress = findViewById(R.id.progressBar2);
        myProgress.setVisibility(View.GONE);
        passwordText = findViewById(R.id.password);
        username_layout = findViewById(R.id.username_layout);
        password_layout = findViewById(R.id.password_layout);


        final Intent sign_upIntent = new Intent(this, SignUp.class);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newAccount.setPaintFlags(newAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                startActivity(sign_upIntent);


            }
        });
    }

    public void startGame(View v) {

        signIn_username = usernameText.getText().toString().trim();
        signIn_password = passwordText.getText().toString().trim();
        final Intent i = new Intent(this, MainActivity.class);
        username_layout.setError("");
        password_layout.setError("");
        if (!check_internet) {
            Toast.makeText(HomeActivity.this, "Internet connectivity issue!",
                    Toast.LENGTH_LONG).show();

        } else {
            if (signIn_username.equals("") && signIn_password.equals("")) {

                username_layout.setError("Username field can't be empty!");
                password_layout.setError("Password field can't be empty!");


            } else if (signIn_username.equals("") || signIn_password.equals("")) {
                if (signIn_username.equals("")) {
                    username_layout.setError("Username field can't be empty!");
                } else {
                    password_layout.setError("Password field can't be empty!");
                }


            } else {
                myProgress.setVisibility(View.VISIBLE);
                myAuth.signInWithEmailAndPassword(signIn_username, signIn_password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("success", "signInWithEmail:success");
                                    Toast.makeText(HomeActivity.this, "Login successful",
                                            Toast.LENGTH_LONG).show();
                                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(i);
                                    finish();
                                    myProgress.setVisibility(View.GONE);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("failure", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(HomeActivity.this, "Please try again!",
                                            Toast.LENGTH_LONG).show();
                                    myProgress.setVisibility(View.GONE);

                                }

                                // ...
                            }
                        });
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        newAccount.setPaintFlags(newAccount.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }

}
