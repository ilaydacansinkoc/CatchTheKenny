package com.ilaydacansin.catchthekenny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView scoreText;
    TextView timeText;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imgArray;

    int score;
    Handler handler;
    Runnable runnable;

    CountDownTimer timer;
    long milliLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = 0;

        imageView1 = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView1);
        imageView3 = (ImageView) findViewById(R.id.imageView2);
        imageView4 = (ImageView) findViewById(R.id.imageView3);
        imageView5 = (ImageView) findViewById(R.id.imageView4);
        imageView6 = (ImageView) findViewById(R.id.imageView5);
        imageView7 = (ImageView) findViewById(R.id.imageView6);
        imageView8 = (ImageView) findViewById(R.id.imageView7);
        imageView9 = (ImageView) findViewById(R.id.imageView8);

        imgArray = new ImageView[]{
                imageView1,imageView2,imageView3,imageView4,imageView5,imageView6
                ,imageView7,imageView8,imageView9
        };

        hideImages();
        startTimer(5000);


    }

    public void startTimer(long milliTime){


        timer = new CountDownTimer(milliTime,1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                milliLeft=millisUntilFinished;
                timeText = (TextView) findViewById(R.id.textTime);
                timeText.setText("Time "+ millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {

                timeText = (TextView) findViewById(R.id.textTime);
                timeText.setText("Time's Up!");
                disableImages(false);

            }
        }.start();
    }

    public void resumeTimer(){
        startTimer(milliLeft);
    }


    public void increaseScore(View view){
        scoreText = (TextView) findViewById(R.id.textScore);
        score++;
        scoreText.setText("Score "+ score);
    }

    public void hideImages(){

        handler = new Handler();

        runnable = new Runnable() {

            @Override
            public void run() {
                for(ImageView img : imgArray){
                    img.setVisibility(View.INVISIBLE);
                }
                Random rand = new Random();
                int i = rand.nextInt(8-0);
                imgArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this,750);
            }
        };

        handler.post(runnable);

    }

    public void restartGame(View view){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);



        disableImages(false);


        alert.setTitle("Game is restarting...");
        alert.setMessage("Are you sure to restart game?");

        timer.cancel();


        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Game is restarted",Toast.LENGTH_SHORT).show();

                Intent intent= getIntent();
                finish();
                startActivity(intent);
            }
        });

        alert.setNegativeButton("NO",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Process cancelled",Toast.LENGTH_SHORT).show();
                //System.out.println(milliLeft);



                if(timeText.getText().toString() == "Time's Up!"){
                    disableImages(false);
                    timer.cancel();

                }else{
                    activateImages(true);
                    resumeTimer();
                }

                //System.out.println(milliLeft);

            }
        });

        alert.setCancelable(false);

        alert.show();

    }

    public void disableImages(boolean isEnabled){
        for(ImageView imageView: imgArray){
            imageView.setEnabled(isEnabled);
        }
        handler.removeCallbacks(runnable);
    }
    public void activateImages(boolean isEnabled){
        for(ImageView imageView: imgArray){
            imageView.setEnabled(isEnabled);
        }
        handler.post(runnable);
    }
}
