package squad.loser.the.flappybird;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.InternalTokenProvider;

public class SignUp extends AppCompatActivity {
    private TextInputEditText new_username;
    private TextInputEditText new_password;
    private TextInputLayout username_signUp_layout;
    private TextInputLayout password_signUp_layout;
    private FirebaseAuth mAuth;
    private Button submit;
    private boolean internetCheck = false;
    private String username = null;
    private String password = null;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        internetCheck = isNetworkAvaliable(this);
        setContentView(R.layout.activity_sign_up);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        new_username = findViewById(R.id.new_username);
        new_password = findViewById(R.id.new_password);
        username_signUp_layout = findViewById(R.id.username_signup_layout);
        password_signUp_layout = findViewById(R.id.password_signup_layout);
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = new_username.getText().toString().trim();
                password = new_password.getText().toString().trim();
                username_signUp_layout.setError("");
                password_signUp_layout.setError("");
                if (!internetCheck) {
                    Toast.makeText(SignUp.this, "Internet connectivity issue!",
                            Toast.LENGTH_LONG).show();

                } else {
                    if (username.equals("") && password.equals("")) {

                        username_signUp_layout.setError("Username field can't be empty!");
                        password_signUp_layout.setError("Password field can't be empty!");


                    } else if (username.equals("") || password.equals("")) {
                        if (username.equals("")) {
                            username_signUp_layout.setError("Username field can't be empty!");
                        } else {
                            password_signUp_layout.setError("Password field can't be empty!");
                        }


                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Success message", "createUserWithEmail:success");
                                    Toast.makeText(SignUp.this, "Account has been created",
                                            Toast.LENGTH_LONG).show();
                                    finish();



                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Failure message", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp.this, "Please try again!",
                                            Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);



                                }

                            }


                        });
                    }
                }
            }
        });


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
