package tob.leis.darepong;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IngameActivity extends AppCompatActivity {

    private static final String LOG_TAG = "IngameActivity";

    private LinearLayout team1Pyramid, team2Pyramid;

    private int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        team1Pyramid = findViewById(R.id.team1_layout);
        team2Pyramid = findViewById(R.id.team2_layout);

        fillPyramidDown(team1Pyramid);
        fillPyramidUp(team2Pyramid);
    }




    private void fillPyramidUp(LinearLayout teamPyramid) {

        switch(size) {
            case 1:
                teamPyramid.addView(createPyramidColumn(1));
                break;
            case 3:
                teamPyramid.addView(createPyramidColumn(1));
                teamPyramid.addView(createPyramidColumn(2));
                break;
            case 6:
                teamPyramid.addView(createPyramidColumn(1));
                teamPyramid.addView(createPyramidColumn(2));
                teamPyramid.addView(createPyramidColumn(3));
                break;
            case 10:
                teamPyramid.addView(createPyramidColumn(1));
                teamPyramid.addView(createPyramidColumn(2));
                teamPyramid.addView(createPyramidColumn(3));
                teamPyramid.addView(createPyramidColumn(4));
                break;
        }
    }

    private void fillPyramidDown(LinearLayout teamPyramid) {

        switch(size) {
            case 10:
                teamPyramid.addView(createPyramidColumn(4));
            case 6:
                teamPyramid.addView(createPyramidColumn(3));
            case 3:
                teamPyramid.addView(createPyramidColumn(2));
            case 1:
                teamPyramid.addView(createPyramidColumn(1));
                break;
        }

    }

    private LinearLayout createPyramidColumn(int size) {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.HORIZONTAL);


        for(int i=0; i<size; i++) {
            ImageButton btn = new ImageButton(this);
            btn.setImageResource(R.drawable.group_work_24px);

            layout.addView(btn);
        }



        return layout;
    }

    class ButtonListener implements View.OnClickListener {

        private static final int DARE_FAILED = -1;
        private static final int DARE_SUCCESS = 0;
        private static final int DARE_CANCELED = 1;

        private boolean clickedAlready = false;

        @Override
        public void onClick(View view) {

            createDareDialogActivity();

            //TODO:
            //1) Popup dialog menu with dare and fulfilled yes no and selected cup
            //2) Depending on yes no the cub will be removed
            //3) Somehow remember whether the button was already clicked once
            //4) If so popup with no dare available and Drink up

        }
    }


    private void createDareDialogActivity() {
        Log.d(LOG_TAG, "Cup clicked!");

        Intent intent = new Intent(this, DareDialogActivity.class);
        startActivity(intent);
    }


    /*

    public void createDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_pong);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);

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
    }*/
}
