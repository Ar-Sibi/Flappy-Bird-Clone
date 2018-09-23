package squad.loser.the.flappybird;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

public class Bird {
    private final float radius = 20f;
    private PointF velocity;
    private PointF position;
    private Paint paint = new Paint();
    private int count=0;
    Bitmap[] bmp = new Bitmap[8];
    public Bird() {
        velocity = new PointF();
        position = new PointF();
        paint.setStyle(Paint.Style.STROKE);
    }

    Bird(Context ctx, float posX, float posY, float velX) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable=true;
        bmp[0]= BitmapFactory.decodeResource(ctx.getResources(),R.drawable.frame_1,options);
        bmp[1]= BitmapFactory.decodeResource(ctx.getResources(),R.drawable.frame_2,options);
        bmp[2]= BitmapFactory.decodeResource(ctx.getResources(),R.drawable.frame_3,options);
        bmp[3]= BitmapFactory.decodeResource(ctx.getResources(),R.drawable.frame_4,options);
        bmp[4]= BitmapFactory.decodeResource(ctx.getResources(),R.drawable.frame_5,options);
        bmp[5]= BitmapFactory.decodeResource(ctx.getResources(),R.drawable.frame_6,options);
        bmp[6]= BitmapFactory.decodeResource(ctx.getResources(),R.drawable.frame_7,options);
        bmp[7]= BitmapFactory.decodeResource(ctx.getResources(),R.drawable.frame_8,options);
        float density=ctx.getResources().getDisplayMetrics().density;
        Log.d("TAG","waasda"+bmp[0]);
        for(int i=0;i<8;i++){
            bmp[i]=Bitmap.createScaledBitmap(bmp[i],(int)(radius*2*density),(int)(radius*2*density), false);
        }
        position = new PointF(posX, posY);
        velocity = new PointF(velX, 0);
        //paint.setStyle(Paint.Style.STROKE);
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
        canvas.save();
        canvas.translate((position.x)*density,(position.y)*density);
        canvas.rotate((float)(Math.toDegrees(Math.atan2(velocity.y,velocity.x+6*Constants.VELOCITY_HORIZONTAL))));
        canvas.drawBitmap(bmp[count/4],-radius*density,-radius*density,paint);
        canvas.restore();
        count++;
        if(count>7*4)
            count=0;
    }
}
