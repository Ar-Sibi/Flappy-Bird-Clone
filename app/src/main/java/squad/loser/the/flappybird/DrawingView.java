package squad.loser.the.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DrawingView extends View implements Runnable {

    Bird bird;

    public DrawingView(Context context) {
        super(context);
        initialize(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    void initialize(Context ctx) {
        bird = new Bird(100, 100, Constants.VELOCITY_HORIZONTAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bird.draw(canvas, 1);
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
