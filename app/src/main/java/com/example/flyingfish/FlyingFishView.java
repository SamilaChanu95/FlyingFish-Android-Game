package com.example.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;//for draw the contents
import android.widget.Toast;

public class FlyingFishView extends View
{
    private Bitmap fish[] = new Bitmap[2];

    private int fishX = 10;
    private int fishY ;
    private int fishSpeed ;

    private int canvasWidth , canvasHeight ;

    private int yellowX , yellowY , yellowSpeed = 8;
    private Paint yellowPaint = new Paint();


    private int greenX , greenY , greenSpeed = 11;
    private Paint greenPaint = new Paint();

    private int redX , redY , redSpeed = 14;
    private Paint redPaint = new Paint();

    private int score, lifeCounter;

    //use the boolean variable for touch
    private boolean touch = false;

    private Bitmap backgroundImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context)
    {
        super(context);

        //use the decodeResource to convert the png to bitmap picture for draw in the canvas
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(55);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY = 500;
        score = 0;
        lifeCounter = 3;



    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        //draw the fish image on the canvas

        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY = fish[0].getHeight() ;
        int maxFishY = canvasHeight - fish[0].getHeight() ;
        fishY = fishY + fishSpeed;
        if(fishY <  minFishY)
        {
            fishY = minFishY;
        }
        if(fishY >  maxFishY)
        {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;
        if (touch)
        {
            //if touch then change the fish icon by draw fish2
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else
        {
            //draw fish1
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX,yellowY)) {
            score = score + 10;
            yellowX = -100;
            yellowSpeed = yellowSpeed + (int) 0.1;

        }

        if (yellowX < 0)
        {
            yellowX = canvasWidth +21;
            if (yellowY > minFishY) {
                yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + (minFishY*2);
            }
            else {
                yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
            }
        }

        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX,greenY))
        {
            score = score + 30;
            greenX = -100;
            greenSpeed = greenSpeed + (int) 0.1;

        }

        if (greenX < 0)
        {
            greenX = canvasWidth +21;
            if (greenY > minFishY) {
                greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + (minFishY*2);
            }
            else {
                greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
            }
        }


        redX = redX - redSpeed;

        if (hitBallChecker(redX,redY))
        {
            redX = -100;
            lifeCounter--;
            redSpeed = redSpeed + (int) 0.1;

            if(lifeCounter == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent  = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX < 0)
        {
            redX = canvasWidth +21;
            if (redY > minFishY) {
                redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + (minFishY*2);
            }
            else {
                redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
            }
        }

        //draw circle
        canvas.drawCircle(redX, redY, 25, redPaint);

        //draw circle
        canvas.drawCircle(greenX, greenY, 20, greenPaint);

        //draw circle
        canvas.drawCircle(yellowX, yellowY, 20, yellowPaint);

        //draw text part
        canvas.drawText("Score : "+ score, 10 , 60,scorePaint);


        for (int i=0; i<3; i++)
        {
            int x = (int) (480 + life[0].getWidth() * 1.25 * i);
            int y = 10;

            if(i < lifeCounter)
            {
                canvas.drawBitmap(life[0] ,x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1] ,x,y,null);
            }
        }

    }

    //method for whenever fish eat ball then disappear the ball & add the mark

    public boolean hitBallChecker(int x,int y)
    {
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y <(fishY + fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;

            fishSpeed = -20;

        }

        return true;
    }
}
