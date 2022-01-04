package kr.ac.mjc.myapplicationproject;


import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;


public class Splashscreen extends AppCompatActivity {

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBX_8888);
    }

    ImageView logo,splash;
    LottieAnimationView lottieAnimationView;

    Thread splashTread;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        StartAnimations();
    }

    private void StartAnimations(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

        logo = findViewById(R.id.logo_iv);
        splash = findViewById(R.id.splash_iv);
        lottieAnimationView = findViewById(R.id.lottie);


        splashTread = new Thread(){
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 5500) {
                        sleep(300);
                        waited += 300;
                    }
                    Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {

                } finally {
                    Splashscreen.this.finish();
                }
            }
        };
        splashTread.start();
    }
}
