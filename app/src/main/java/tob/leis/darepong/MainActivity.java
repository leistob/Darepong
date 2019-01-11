package tob.leis.darepong;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private Button startButton, continueButton, settingsButton, manualButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button_start);
        continueButton = findViewById(R.id.button_continue);
        settingsButton = findViewById(R.id.button_settings);
        manualButton = findViewById(R.id.button_manual);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchStartActivity();
            }

        });

        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchSettingsActivity();
            }

        });

        manualButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchManualActivity();
            }

        });

        System.out.println(FileHelper.loadCSVFromAsset(this));
    }

    public void launchStartActivity() {
        Log.d(LOG_TAG, "StartButton clicked!");

        Intent intent = new Intent(this, IngameActivity.class);
        //intent.putExtra()
        startActivity(intent);
    }

    public void launchSettingsActivity() {
        Log.d(LOG_TAG, "SettingsButton clicked!");

        //Intent intent = new Intent(this, AppCompatPreferenceActivity.class);
        //startActivity(intent);
    }

    public void launchManualActivity() {
        Log.d(LOG_TAG, "ManualButton clicked!");

        Intent intent = new Intent(this, ManualActivity.class);
        startActivity(intent);
    }

    public void launchContinueActivity() {
        Log.d(LOG_TAG, "ContinueButton clicked!");

        Intent intent = new Intent(this, ManualActivity.class);
        startActivity(intent);
    }
}
