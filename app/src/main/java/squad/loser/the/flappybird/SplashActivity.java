package squad.loser.the.flappybird;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        SharedPreferences pref = getSharedPreferences(Constants.shared_string,MODE_PRIVATE);
        String signInUsername = pref.getString("username","");
        String signInPassword = pref.getString("password","");
        final Intent i = new Intent(this, MainMenuActivity.class);

        if(!isNetworkAvaliable(this)){
            Intent toMainActivity = new Intent(this,MainMenuActivity.class);
            pref.edit().putBoolean("anonymous",true).apply();
            startActivity(toMainActivity);
        }
        if(signInUsername != ""){
            pref.edit().putBoolean("anonymous",false).apply();
            myAuth.signInWithEmailAndPassword(signInUsername, signInPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "signInWithEmail:success");
                                Toast.makeText(SplashActivity.this, "Login successful",
                                        Toast.LENGTH_LONG).show();
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("failure", "signInWithEmail:failure", task.getException());
                                Toast.makeText(SplashActivity.this, "Sign in failed high score will not be updated",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Intent toLoginActivity = new Intent(this,HomeActivity.class);
            startActivity(toLoginActivity);
            finish();
        }
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
