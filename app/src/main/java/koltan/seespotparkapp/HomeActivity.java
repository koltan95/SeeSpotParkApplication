package koltan.seespotparkapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final MediaPlayer dogBark = MediaPlayer.create(this,R.raw.woof);

        dogBark.start();

       // Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        //finish();

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        }, 3000);


    }


}
