package tob.leis.darepong;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static tob.leis.darepong.MainActivity.AMOUNT_CUPS;

public class GameActivity extends AppCompatActivity {

    private static final String LOG_TAG = "GameActivity";

    private int size;

    private TextView team1Counter, team2Counter;
    LinearLayout team1Pyramid, team2Pyramid;

    private DareGenerator dareGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            size = extras.getInt(AMOUNT_CUPS);
        } else {
            size = 10;
        }

        team1Pyramid = findViewById(R.id.team1_layout);
        team2Pyramid = findViewById(R.id.team2_layout);

        fillPyramidDown(team1Pyramid);
        fillPyramidUp(team2Pyramid);

        team1Counter = findViewById(R.id.team1_counter);
        team2Counter = findViewById(R.id.team2_counter);

        team1Counter.setText(String.valueOf(size));
        team2Counter.setText(String.valueOf(size));

        dareGenerator = new DareGenerator(this);
    }

    public void subtractCup(ImageButton btn) {
        if(btn.getParent().getParent().equals(team1Pyramid)) {
            subtractTeam1();
        } else {
            subtractTeam2();
        }
    }

    public void subtractTeam1() {
        int currentAmount = Integer.parseInt(team1Counter.getText().toString());
        if(currentAmount == 1) {
            //TODO: trigger ending
            return;
        }
        team1Counter.setText(String.valueOf(currentAmount-1));
    }

    public void subtractTeam2() {
        int currentAmount = Integer.parseInt(team2Counter.getText().toString());
        if(currentAmount == 1) {
            //TODO: trigger ending
            return;
        }
        team2Counter.setText(String.valueOf(currentAmount-1));
    }

    private void fillPyramidUp(LinearLayout teamPyramid) {

        switch(size) {
            case 1:
                teamPyramid.addView(createPyramidRow(1));
                break;
            case 3:
                teamPyramid.addView(createPyramidRow(1));
                teamPyramid.addView(createPyramidRow(2));
                break;
            case 6:
                teamPyramid.addView(createPyramidRow(1));
                teamPyramid.addView(createPyramidRow(2));
                teamPyramid.addView(createPyramidRow(3));
                break;
            case 10:
                teamPyramid.addView(createPyramidRow(1));
                teamPyramid.addView(createPyramidRow(2));
                teamPyramid.addView(createPyramidRow(3));
                teamPyramid.addView(createPyramidRow(4));
                break;
        }
    }

    private void fillPyramidDown(LinearLayout teamPyramid) {

        switch(size) {
            case 10:
                teamPyramid.addView(createPyramidRow(4));
            case 6:
                teamPyramid.addView(createPyramidRow(3));
            case 3:
                teamPyramid.addView(createPyramidRow(2));
            case 1:
                teamPyramid.addView(createPyramidRow(1));
                break;
        }
    }

    private LinearLayout createPyramidRow(int rowSize) {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(150,150);

        for(int i=0; i<rowSize; i++) {
            ImageButton btn = new ImageButton(this);
            btn.setImageResource(R.drawable.cup_white_24px);
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
                deleteCup();
            } else {
                createDareDialog(this);
            }
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
                    deleteCup();
                    break;
                default:
                    break;
            }
        }

        private void deleteCup() {
            createDareFinishedToast();
            makeCupInvisible();
            subtractCup(this.btn);
        }


        private void makeCupUsed() {
            btn.setImageResource(R.drawable.cup_grey_24px);
        }

        private void makeCupInvisible() {
            //TODO Animation to drink (flickering?)
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
        //TODO: get dare depending on selected level
        String randomDare = dareGenerator.getRandomDare();
        text.setText(randomDare);

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


    public void rearrangeTeam1(View view) {
        Log.d(LOG_TAG, "rearrange1");
        rearrange(1);
    }

    public void rearrangeTeam2(View view) {
        Log.d(LOG_TAG, "rearrange2");
        rearrange(2);
    }

    private void rearrange(int team) {

        int currentAmount = team == 1 ?
                                Integer.parseInt(team1Counter.getText().toString()) :
                                Integer.parseInt(team2Counter.getText().toString());

        startRearrangeDialog();
    }

    private void startRearrangeDialog() {
        //Get linearlayout from dialog
        //fill up or down, but grey out
        //wait for btn click
        //
        //TODO
        //Create Textview with cupcounter
        //When one cup is selected subtract one from cupcounter
        //only accept when cupcounter is 0
        //OK and CANCEL
        //When OK: make unselected cups invisible, leave the rest
        //btn.setVisibility(false);

        //take this LinearLayout and replace old one with this one
    }
}
