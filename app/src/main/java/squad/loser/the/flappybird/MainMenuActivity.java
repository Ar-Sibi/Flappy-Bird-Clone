package squad.loser.the.flappybird;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startGame(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void startHighScore(View v){
        Intent i = new Intent(this,ScoreBoardActivity.class);
        startActivity(i);
    }
}
