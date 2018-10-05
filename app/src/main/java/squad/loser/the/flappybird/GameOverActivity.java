package squad.loser.the.flappybird;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GameOverActivity extends AppCompatActivity {
    TextView score;
    Button retry;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference fire_baseDb;
    int checkHighScore;
    public String uId;
    public int score_of_the_user = 0;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.myButton) {
            Intent myIntent = new Intent(this, ScoreBoardActivity.class);
            startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_game_over);
        score = findViewById(R.id.score);
        retry = findViewById(R.id.retry);
        score_of_the_user = getIntent().getIntExtra("score", 0);
        score.setText("Your Score : " + score_of_the_user);
        if (!getSharedPreferences(Constants.shared_string, MODE_PRIVATE).getBoolean("anonymous", false)) {
            uId = user.getUid();
            fire_baseDb = FirebaseDatabase.getInstance().getReference("scoreboard").child(uId);

            String _nameOf_user = user.getEmail();
            Log.d("user name", _nameOf_user);

            fire_baseDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int test = 0;


                    if (dataSnapshot.exists()) {
                        test = dataSnapshot.child("_Score").getValue(Integer.class);
                        Log.d("test value", String.valueOf(test));
                        checkHighScore = test;
                        //Log.e("previous score", String.valueOf(checkHighScore));
                    } else {
                        Log.d("empty dataSnapshot", "null");
                    }
                    if (score_of_the_user > checkHighScore && (score_of_the_user != checkHighScore)) {
                        fire_baseDb.setValue(new HighScore(score_of_the_user, user.getEmail()));
                        Toast.makeText(getApplicationContext(), "Stored", Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        // Log.e("score_of the user", String.valueOf(score_of_the_user));
        // Log.e("previous score", String.valueOf(checkHighScore));


        final Intent intent = new Intent(this, MainActivity.class);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainMenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
