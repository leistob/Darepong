package tob.leis.darepong;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    Button startButton, continueButton, settingsButton, manualButton;

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
                createNewGameDialog();
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

    private void launchIngameActivity() {
        Log.d(LOG_TAG, "StartButton clicked!");

        Intent intent = new Intent(this, IngameActivity.class);
        //intent.putExtra()
        startActivity(intent);
    }

    private void launchSettingsActivity() {
        Log.d(LOG_TAG, "SettingsButton clicked!");

        //Intent intent = new Intent(this, AppCompatPreferenceActivity.class);
        //startActivity(intent);
    }

    private void launchManualActivity() {
        Log.d(LOG_TAG, "ManualButton clicked!");

        Intent intent = new Intent(this, ManualActivity.class);
        startActivity(intent);
    }

    private void launchContinueActivity() {
        Log.d(LOG_TAG, "ContinueButton clicked!");

        Intent intent = new Intent(this, ManualActivity.class);
        startActivity(intent);
    }

    private void createNewGameDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_start);
        dialog.setTitle("Start");

        TextView text = dialog.findViewById(R.id.text);
        //TODO: get random dare
        text.setText("Android custom dialog example!");

        ImageButton cancelButton = dialog.findViewById(R.id.dialogButtonOK);
        ImageButton successButton = dialog.findViewById(R.id.button_success);
        ImageButton failureButton = dialog.findViewById(R.id.button_failure);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        successButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                launchIngameActivity();
            }
        });

        failureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
