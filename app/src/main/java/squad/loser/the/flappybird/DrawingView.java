package squad.loser.the.flappybird;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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
    Bitmap backgroundImage;

    Rect sourceArea;
    Rect backgroundArea;

    Paint bitmapPainter;

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
        backgroundImage = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.bg_b);
        sourceArea = new Rect(0,0,backgroundImage.getWidth(),backgroundImage.getHeight());
        float screenWidth = ctx.getResources().getDisplayMetrics().widthPixels;

        DisplayMetrics metrics=new DisplayMetrics();
        if(Build.VERSION.SDK_INT>17)
            getActivity(ctx).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        else
            getActivity(ctx).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight=metrics.heightPixels;
        density=ctx.getResources().getDisplayMetrics().density;

        backgroundArea= new Rect(0,0,(int)screenWidth,(int)screenHeight);

        score=0;

        bitmapPainter = new Paint();

        textPainter = new Paint();
        textPainter.setTextSize(100);
    }

    void drawBackground(Canvas c){
        c.drawBitmap(backgroundImage,sourceArea,backgroundArea,bitmapPainter);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
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

            getActivity(c).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            });
        }
    }
}
