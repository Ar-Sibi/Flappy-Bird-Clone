package squad.loser.the.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DrawingView extends View implements Runnable{

    Bird bird;
    Pipes pipes;
    GestureDetector gestureDetector;

    public DrawingView(Context context) {
        super(context);
        initialize(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    void initialize(final Context ctx) {
        bird = new Bird(100, 100, 0);
        pipes = new Pipes();
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    bird.jump();
                }
                return true;
            }
        });
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        pipes.draw(canvas,1);
        bird.draw(canvas, 1);
        pipes.move();
        bird.move();
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                break;
            }
            invalidate();
        }
    }
}
