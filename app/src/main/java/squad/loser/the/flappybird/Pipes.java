package squad.loser.the.flappybird;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pipes {
    Random random = new Random();
    float screenHeight;
    private List<Pipe> pipes = new ArrayList();


    Pipes(Context c) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inMutable=true;
        Bitmap wall = BitmapFactory.decodeResource(c.getResources(),R.drawable.brick_wall_tiled_perfect,options);

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;
        float density = displayMetrics.density;
        screenHeight = displayMetrics.heightPixels / density;

        wall=Bitmap.createScaledBitmap(wall,(int)(Constants.PIPE_WIDTH*density),(int)(Constants.PIPE_WIDTH*density)+1, false);

        int numpipes = (int) Math.floor((screenWidth / ((Constants.PIPE_GAP + Constants.PIPE_WIDTH) * density)) + 1);
        float startX = screenWidth / density;

        for (int i = 0; i < numpipes; i++) {
            pipes.add(new Pipe(startX + i * (Constants.PIPE_WIDTH + Constants.PIPE_GAP), random.nextInt((int) (screenHeight - (Constants.PIPE_PASS_GAP + 200))) + 100f, screenHeight,wall));
        }
    }

    public boolean intersects(Bird b) {
        for (int i = 0; i < pipes.size(); i++) {
            if (pipes.get(i).intersects(b)) {
                return true;
            }
        }
        return false;
    }

    public void move() {
        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).move();
            if (pipes.get(i).outOfBounds()) {
                pipes.get(i).reset(pipes.get(i == 0 ? pipes.size() - 1 : i - 1).left + Constants.PIPE_WIDTH + Constants.PIPE_GAP, random.nextInt((int) (screenHeight - (Constants.PIPE_PASS_GAP + 200))) + 100f);
            }
        }
    }

    public void draw(Canvas c, float density) {
        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).draw(c, density);
        }
    }
}
