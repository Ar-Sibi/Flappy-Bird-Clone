package squad.loser.the.flappybird;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Pipe {
    float left;
    float gapTop;
    RectF top;
    RectF bottom;

    Paint paint=new Paint();

    Pipe(float x,float y,float screenBottom){
        paint.setStyle(Paint.Style.STROKE);
        left=x;
        gapTop=y;
        top = new RectF(x,0,x+Constants.PIPE_WIDTH,y);
        bottom = new RectF(x,y+Constants.PIPE_PASS_GAP,x+Constants.PIPE_WIDTH,screenBottom);
    }

    public void move(){
        top.left-=Constants.VELOCITY_HORIZONTAL;
        top.right-=Constants.VELOCITY_HORIZONTAL;
        bottom.left-=Constants.VELOCITY_HORIZONTAL;
        bottom.right-=Constants.VELOCITY_HORIZONTAL;
    }

    public void draw(Canvas c,float density){
        c.drawRect(top.left*density,top.top*density,top.right*density,top.bottom*density,paint);
        c.drawRect(bottom.left*density,bottom.top*density,bottom.right*density,bottom.bottom*density,paint);
    }
}
