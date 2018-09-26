package squad.loser.the.flappybird;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class MyCustomArrayAdapter extends ArrayAdapter<HighScore> {
    public Context context;
    public ArrayList<HighScore> subject = new ArrayList<HighScore>();
    public MyCustomArrayAdapter(Context context, ArrayList<HighScore> objects) {
        super(context ,0 , objects);
        subject = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item, null);
        TextView userName =(TextView) v.findViewById(R.id.nameUser);
        TextView scoreUser = (TextView)v.findViewById(R.id.scoreUser);
        userName.setText(subject.get(position)._username);
        scoreUser.setText(String.valueOf(subject.get(position)._Score));
        return v;

    }
}
