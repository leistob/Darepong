package tob.leis.darepong;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    public static final String AMOUNT_CUPS = "AMOUT_CUPS";
    public static final String NAMES_PLAYERS = "NAMES_PLAYERS";

    private static final String[] possibleValues = {"10", "6", "3", "1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences sharedPref = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean(SettingsActivity.KEY_TEST, false);
    }

    private void launchGameActivity(String names, int amountCups) {
        Log.d(LOG_TAG, "launchGameActivity");

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(NAMES_PLAYERS, names);
        intent.putExtra(AMOUNT_CUPS, amountCups);
        startActivity(intent);
    }

    public void launchSettingsActivity(View view) {
        Log.d(LOG_TAG, "launchSettingsActivity");

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void launchManualActivity(View view) {
        Log.d(LOG_TAG, "launchManualActivity");

        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void createNewGameDialog(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_start);
        dialog.setTitle("Start");

        /*TextView text = dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");*/

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