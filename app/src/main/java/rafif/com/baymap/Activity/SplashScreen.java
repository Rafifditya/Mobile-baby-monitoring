package rafif.com.baymap.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rafif.com.baymap.Controller.SessionManager;
import rafif.com.baymap.R;


public class SplashScreen extends AppCompatActivity {
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        session = new SessionManager(getApplicationContext());

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);

                    if (session.checkLogin()){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        myThread.start();
    }
}
