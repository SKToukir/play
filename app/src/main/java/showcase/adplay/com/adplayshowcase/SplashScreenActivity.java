package showcase.adplay.com.adplayshowcase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splash();
    }

    private void splash() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }

            }
        });thread.start();

    }
}
