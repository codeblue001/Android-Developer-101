package code.blue.androiddeveloper101.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import code.blue.androiddeveloper101.R;

public class SplashActivity extends AppCompatActivity {

    private static int time = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView imageView = findViewById(R.id.rotate);
        TextView textView = findViewById(R.id.text);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotateanim);
        imageView.startAnimation(animation);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.downanim);
        textView.startAnimation(animation1);

        //splash screen handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, time);
    }
}
