package squad.loser.the.flappybird;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Path;
import android.icu.text.RelativeDateTimeFormatter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

public class ScoreBoardActivity extends AppCompatActivity {
    public ArrayList<HighScore> scores;
    DatabaseReference databaseReference;
    public ListView list;
    public MyCustomArrayAdapter myCustomArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        list = findViewById(R.id.list);
        scores = new ArrayList<HighScore>();
        myCustomArrayAdapter = new MyCustomArrayAdapter(this , scores);
        databaseReference = FirebaseDatabase.getInstance().getReference("scoreboard");
        databaseReference.orderByChild("_Score").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    scores.add(new HighScore(postSnapshot.child("_Score").getValue(Integer.class),
                            postSnapshot.child("_username").getValue().toString()));
                    Log.d("name of the users" , postSnapshot.child("_username").toString());
                }

                Log.e("length of the arrayList" , String.valueOf(scores.size()));
                Collections.reverse(scores);
                Log.e("reversed name" , String.valueOf(scores.get(0)._username));
                list.setAdapter(myCustomArrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
