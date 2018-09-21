package squad.loser.the.flappybird;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pipes {
    private List<Pipe> pipes = new ArrayList();
    Random random = new Random();

    Pipes() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;
        float density = 1;//displayMetrics.density;
        float screenHeight = displayMetrics.heightPixels/density;
        int numpipes = (int) Math.floor((screenWidth / ((Constants.PIPE_GAP + Constants.PIPE_WIDTH) * density)) + 1);
        float startX=screenWidth/density;
        for (int i = 0; i < numpipes; i++) {
            pipes.add(new Pipe(startX+i*(Constants.PIPE_WIDTH+Constants.PIPE_GAP),random.nextInt((int)(screenHeight-500f))+250f,screenHeight));
        }
    }

    public void move(){
        for(int i=0;i<pipes.size();i++){
            pipes.get(i).move();
        }
    }

    public void draw(Canvas c, float density){
        for(int i=0;i<pipes.size();i++){
            pipes.get(i).draw(c,density);
        }
    }
}
