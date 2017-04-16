package pl.krakow.nordic.gym_shepherd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends Activity {

    private static final int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Starter start = new Starter();
        start.start();

    }

    private class Starter extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(DELAY);
            } catch (Exception e){
                Log.e("SplashScreen", e.getMessage());
            }
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            SplashScreen.this.startActivity(intent);
            SplashScreen.this.finish();
        }
    }
}

