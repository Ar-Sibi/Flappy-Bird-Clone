package squad.loser.the.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DrawingView extends View implements Runnable{

    Paint circlePaint=new Paint();
    float posx=0;

    public DrawingView(Context context) {
        super(context);
        initialize(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    void initialize(Context ctx){
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(posx,100f,100f,circlePaint);
        posx++;
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void run(){
        while(!Thread.interrupted()){
            try{
                Thread.sleep(1000/60);
            }catch (InterruptedException e){
                break;
            }
            invalidate();
        }
    }
}
