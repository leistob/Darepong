package tob.leis.darepong;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IngameActivity extends AppCompatActivity {

    private static final String LOG_TAG = "IngameActivity";

    private int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout team1Pyramid = findViewById(R.id.team1_layout);
        LinearLayout team2Pyramid = findViewById(R.id.team2_layout);

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

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(150,150);

        for(int i=0; i<size; i++) {
            ImageButton btn = new ImageButton(this);
            btn.setImageResource(R.drawable.group_work_24px);
            btn.setOnClickListener(new ButtonListener());
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setPadding(0,0,0,0);
            btn.setLayoutParams(btnParams);
            btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            layout.addView(btn);
        }
        return layout;
    }

    class ButtonListener implements View.OnClickListener {

        private static final int DARE_FAILED = -1;
        private static final int DARE_SUCCESS = 0;
        private static final int DARE_CANCELED = 1;

        private boolean clickedAlready = false;

        private ImageButton btn;

        @Override
        public void onClick(View view) {

            this.btn = (ImageButton) view;

            if(clickedAlready) {
                createDareFinishedToast();
                makeCupInvisible();
            } else {
                createDareDialog(this);
            }
            //TODO:
            //1) Popup dialog menu with dare and fulfilled yes no and selected cup
        }

        private void setFeedbackFromDialog(int response) {
            switch(response) {
                case DARE_SUCCESS:
                    clickedAlready = true;
                    makeCupUsed();
                    break;
                case DARE_CANCELED:
                    break;
                case DARE_FAILED:
                    createDareFinishedToast();
                    makeCupInvisible();
                    break;
                default:
                    break;
            }
        }

        private void makeCupUsed() {
            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        private void makeCupInvisible() {
            btn.setVisibility(View.INVISIBLE);
        }
    }

    public void createDareFinishedToast() {
        Context context = getApplicationContext();
        CharSequence text = getResources().getString(R.string.drink);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void createDareDialog(final ButtonListener list) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_pong);
        dialog.setTitle("Title...");

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
                list.setFeedbackFromDialog(ButtonListener.DARE_CANCELED);
            }
        });

        successButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                list.setFeedbackFromDialog(ButtonListener.DARE_SUCCESS);
            }
        });

        failureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                list.setFeedbackFromDialog(ButtonListener.DARE_FAILED);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
