package squad.loser.the.flappybird;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    DrawingView drawingView;
    Thread runner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        drawingView = new DrawingView(this);
        runner = new Thread(drawingView);
        runner.start();

        setContentView(drawingView);
    }

    @Override
    protected void onStart() {
        if (drawingView != null && runner == null) {
            runner = new Thread(drawingView);
            runner.start();
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (drawingView != null && runner == null) {
            runner = new Thread(drawingView);
            runner.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (runner != null && !runner.isInterrupted()) {
            runner.interrupt();
            runner = null;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (runner != null && !runner.isInterrupted()) {
            runner.interrupt();
            runner = null;
        }
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}

