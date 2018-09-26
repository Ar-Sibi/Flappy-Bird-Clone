package squad.loser.the.flappybird;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View implements Runnable {

    Bird bird;
    Pipes pipes;
    float screenHeight;
    float density;
    Context c;
    boolean isDead = false;
    boolean isGameOver = false;
    float score;
    Paint textPainter;


    public DrawingView(Context context) {
        super(context);
        initialize(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    void initialize(final Context ctx) {
        c=ctx;
        this.setLayerType(LAYER_TYPE_HARDWARE, null);
        bird = new Bird(ctx,100, 100, 0);
        pipes = new Pipes(ctx);
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && !isDead) {
                    bird.jump();
                }
                return true;
            }
        });
        screenHeight=ctx.getResources().getDisplayMetrics().heightPixels;
        density=ctx.getResources().getDisplayMetrics().density;

        score=0;

        textPainter = new Paint();
        textPainter.setTextSize(100);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#00ffff"));
        pipes.draw(canvas, density);
        bird.draw(canvas, density);
        if (!isDead)
            pipes.move();
        bird.move();

        if (!isDead) {
            score+=(2*(Constants.VELOCITY_HORIZONTAL)/(Constants.PIPE_WIDTH+Constants.PIPE_GAP));
            isDead = pipes.intersects(bird) || outOfBounds(bird);
            if (isDead)
                bird.die();
        }
        if (isGameOver(bird) && !isGameOver) {
            Intent i = new Intent(c,GameOverActivity.class);
            i.putExtra("score",(int)score);
            c.startActivity(i);
            getActivity(c).finish();
            isGameOver=true;
        }
        canvas.drawText(""+(int)score,0,100,textPainter);
        super.onDraw(canvas);
    }

    boolean isGameOver(Bird b){
        if(!isDead)
            return false;
        float radius = b.getRadius();
        float pos = b.getPosition().y;
        if(pos>screenHeight/density) {
            return true;
        }
        return false;
    }

    boolean outOfBounds(Bird b){
        float radius = b.getRadius();
        float pos = b.getPosition().y;
        if(pos-radius<0) {
            return true;
        }
        if(pos+radius>screenHeight){
            return true;
        }
        return false;
    }

    private Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000 / 90);
            } catch (InterruptedException e) {
                break;
            }
            invalidate();
        }
    }
}
