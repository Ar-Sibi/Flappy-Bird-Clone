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

    public Bird(float posX, float posY, float velX) {
        position = new PointF(posX, posY);
        velocity = new PointF(velX, 0);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void move() {
        velocity.offset(0, Constants.GRAVITY);
        position.offset(velocity.x, velocity.y);
    }

    public void draw(Canvas canvas, float density) {
        canvas.drawCircle(position.x, position.y, 60f * density, paint);
    }
}
