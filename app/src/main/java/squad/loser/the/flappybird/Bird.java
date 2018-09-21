package squad.loser.the.flappybird;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Bird {
    private PointF velocity;
    private PointF position;
    Paint paint = new Paint();

    public Bird() {
        velocity = new PointF();
        position = new PointF();
        paint.setStyle(Paint.Style.STROKE);
    }

    Bird(float posX, float posY, float velX) {
        position = new PointF(posX, posY);
        velocity = new PointF(velX, 0);
        paint.setStyle(Paint.Style.STROKE);
    }

    void move() {
        velocity.offset(0, Constants.GRAVITY);
        if (velocity.y > Constants.MAX_VEL) {
            velocity.y = Constants.MAX_VEL;
        }
        position.offset(velocity.x, velocity.y);
    }

    void jump() {
        velocity.offset(0, -Constants.JUMP_VEL);
        if (velocity.y < -Constants.MAX_VEL) {
            velocity.y = -Constants.MAX_VEL;
        }
    }

    void draw(Canvas canvas, float density) {
        canvas.drawCircle(position.x, position.y, 60f * density, paint);
    }
}
