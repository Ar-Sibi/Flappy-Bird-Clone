package squad.loser.the.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DrawingView extends View{

    Paint circlePaint=new Paint();

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
        canvas.drawCircle(100f,100f,100f,circlePaint);
        super.onDraw(canvas);
    }
}
