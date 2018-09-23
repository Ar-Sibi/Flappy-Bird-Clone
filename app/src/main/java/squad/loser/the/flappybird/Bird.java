package squad.loser.the.flappybird;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Bird {
    private final float radius = 60f;
    private PointF velocity;
    private PointF position;
    private Paint paint = new Paint();

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

    public PointF getPosition() {
        return new PointF(position.x, position.y);
    }

    public float getRadius() {
        return radius;
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

    void die() {
        velocity.set(0, -Constants.MAX_VEL);
    }

    void draw(Canvas canvas, float density) {
        canvas.drawCircle(position.x * density, position.y * density, radius * density, paint);
    }
}
