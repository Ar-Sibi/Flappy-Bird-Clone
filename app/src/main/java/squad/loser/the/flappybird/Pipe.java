package squad.loser.the.flappybird;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Pipe {
    float left;
    float gapTop;
    RectF top;
    RectF bottom;
    Bitmap wall;
    Paint paint = new Paint();

    Pipe(float x, float y, float screenBottom, Bitmap wall) {
        //paint.setStyle(Paint.Style.STROKE);
        left = x;
        gapTop = y;
        top = new RectF(x, 0, x + Constants.PIPE_WIDTH, y);
        bottom = new RectF(x, y + Constants.PIPE_PASS_GAP, x + Constants.PIPE_WIDTH, screenBottom);
        this.wall=wall;
    }

    boolean intersects(Bird b) {
        float right = left + Constants.PIPE_WIDTH;
        PointF pos = b.getPosition();
        float radius = b.getRadius();
        if (left > pos.x + radius) {
            return false;
        }
        if (right < pos.x - radius) {
            return false;
        }
        if (left < pos.x && right > pos.x) {
            if (top.bottom > pos.y - radius) {
                return true;
            }
            if (bottom.top < pos.y + radius) {
                return true;
            }
            return false;
        } else {
            if (top.bottom > pos.y) {
                return left < pos.x + radius;
            }
            if (bottom.top < pos.y) {
                return right > pos.x - radius;
            }
            if (Math.sqrt((left - pos.x) * (left - pos.x) + (top.bottom - pos.y) * (top.bottom - pos.y)) < radius) {
                return true;
            }
            if (Math.sqrt((left - pos.x) * (left - pos.x) + (top.bottom + Constants.PIPE_PASS_GAP - pos.y) * (top.bottom + Constants.PIPE_PASS_GAP - pos.y)) < radius) {
                return true;
            }
            if (Math.sqrt((right - pos.x) * (right - pos.x) + (top.bottom - pos.y) * (top.bottom - pos.y)) < radius) {
                return true;
            }
            if (Math.sqrt((right - pos.x) * (right - pos.x) + (top.bottom + Constants.PIPE_PASS_GAP - pos.y) * (top.bottom + Constants.PIPE_PASS_GAP - pos.y)) < radius) {
                return true;
            }
            return false;
        }
    }

    void reset(float left, float y) {
        this.left = left;
        top = new RectF(top.left, 0, top.right, y);
        bottom = new RectF(bottom.left, y + Constants.PIPE_PASS_GAP, bottom.right, bottom.bottom);
    }

    public void move() {
        left -= Constants.VELOCITY_HORIZONTAL;
        top.left = left;
        top.right = left + Constants.PIPE_WIDTH;
        bottom.left = left;
        bottom.right = left + Constants.PIPE_WIDTH;
    }

    public boolean outOfBounds() {
        return top.left < -Constants.PIPE_WIDTH;
    }

    public void draw(Canvas c, float density) {
        for(int i=1;top.bottom-Constants.PIPE_WIDTH*(i-1)>0;i++)
            c.drawBitmap(wall,top.left*density,top.bottom*density-i*Constants.PIPE_WIDTH*density,new Paint());
        for(int i=0;bottom.top+Constants.PIPE_WIDTH*(i-1)<bottom.bottom;i++)
            c.drawBitmap(wall,bottom.left*density,bottom.top*density+i*Constants.PIPE_WIDTH*density,new Paint());
    }
}
