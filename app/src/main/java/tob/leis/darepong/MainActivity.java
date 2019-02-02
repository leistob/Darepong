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
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    public static final String AMOUNT_CUPS = "AMOUT_CUPS";
    public static final String NAMES_PLAYERS = "NAMES_PLAYERS";

    private static final String[] possibleValues = {"10", "6", "3", "1"};

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
    }

    private void launchGameActivity(String names, int amountCups) {
        Log.d(LOG_TAG, "launchGameActivity");

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(NAMES_PLAYERS, names);
        intent.putExtra(AMOUNT_CUPS, amountCups);
        startActivity(intent);
    }

    private void launchSettingsActivity() {
        Log.d(LOG_TAG, "launchSettingsActivity");
    }

    private void launchManualActivity() {
        Log.d(LOG_TAG, "launchManualActivity");

        Intent intent = new Intent(this, ManualActivity.class);
        startActivity(intent);
    }

    private void launchContinueActivity() {
        Log.d(LOG_TAG, "launchContinueActivity");

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

        ImageButton cancelButton = dialog.findViewById(R.id.button_cancel);
        ImageButton successButton = dialog.findViewById(R.id.button_success);

        final NumberPicker np = dialog.findViewById(R.id.number_picker_start);

        np.setMinValue(1);
        np.setMaxValue(4);
        np.setDisplayedValues(possibleValues);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        successButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedAmountCups = possibleValues[np.getValue()-1];
                Log.d(LOG_TAG, selectedAmountCups);
                dialog.dismiss();
                launchGameActivity("TEST", Integer.parseInt(selectedAmountCups));
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}