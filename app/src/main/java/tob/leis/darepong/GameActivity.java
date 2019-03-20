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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static tob.leis.darepong.MainActivity.AMOUNT_CUPS;

public class GameActivity extends AppCompatActivity {

    private static final String LOG_TAG = "GameActivity";

    private static final int TEAM_1 = 1;
    private static final int TEAM_2 = 2;

    private int pyramidSize;

    private TextView team1Counter, team2Counter;
    LinearLayout team1Pyramid, team2Pyramid;

    public TextView rearrangeCounter;
    public ImageButton rearrangeBtn;

    private DareGenerator dareGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pyramidSize = extras.getInt(AMOUNT_CUPS);
        } else {
            pyramidSize = 10;
        }

        team1Pyramid = findViewById(R.id.team1_layout);
        team2Pyramid = findViewById(R.id.team2_layout);

        fillPyramidDown(team1Pyramid, true);
        fillPyramidUp(team2Pyramid, true);

        team1Counter = findViewById(R.id.team1_counter);
        team2Counter = findViewById(R.id.team2_counter);

        team1Counter.setText(String.valueOf(pyramidSize));
        team2Counter.setText(String.valueOf(pyramidSize));

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

    private void fillPyramidUp(LinearLayout teamPyramid, boolean cupsEnabled) {
        switch(pyramidSize) {
            case 1:
                teamPyramid.addView(createPyramidRow(1, cupsEnabled));
                break;
            case 3:
                teamPyramid.addView(createPyramidRow(1, cupsEnabled));
                teamPyramid.addView(createPyramidRow(2, cupsEnabled));
                break;
            case 6:
                teamPyramid.addView(createPyramidRow(1, cupsEnabled));
                teamPyramid.addView(createPyramidRow(2, cupsEnabled));
                teamPyramid.addView(createPyramidRow(3, cupsEnabled));
                break;
            case 10:
                teamPyramid.addView(createPyramidRow(1, cupsEnabled));
                teamPyramid.addView(createPyramidRow(2, cupsEnabled));
                teamPyramid.addView(createPyramidRow(3, cupsEnabled));
                teamPyramid.addView(createPyramidRow(4, cupsEnabled));
                break;
        }
    }

    private void fillPyramidDown(LinearLayout teamPyramid, boolean cupsEnabled) {
        switch(pyramidSize) {
            case 10:
                teamPyramid.addView(createPyramidRow(4, cupsEnabled));
            case 6:
                teamPyramid.addView(createPyramidRow(3, cupsEnabled));
            case 3:
                teamPyramid.addView(createPyramidRow(2, cupsEnabled));
            case 1:
                teamPyramid.addView(createPyramidRow(1, cupsEnabled));
                break;
        }
    }

    private LinearLayout createPyramidRow(int rowSize, boolean enabled) {
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
            if(enabled) {
                btn.setImageResource(R.drawable.cup_white_24px);
                btn.setOnClickListener(new ButtonListener());
            } else {
                btn.setImageResource(R.drawable.cup_grey_24px);
                btn.setOnClickListener(new RearrangeListener());
            }

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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////// Rearrange functions /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void rearrangeTeam1(View view) {
        Log.d(LOG_TAG, "rearrange1");
        rearrange(TEAM_1);
    }

    public void rearrangeTeam2(View view) {
        Log.d(LOG_TAG, "rearrange2");
        rearrange(TEAM_2);
    }

    private void rearrange(int team) {

        int currentAmount = team == 1 ?
                                Integer.parseInt(team1Counter.getText().toString()) :
                                Integer.parseInt(team2Counter.getText().toString());

        startRearrangeDialog(team, currentAmount);
    }


    class RearrangeListener implements View.OnClickListener {

        private boolean activated = false;
        private ImageButton btn;

        @Override
        public void onClick(View view) {

            this.btn = (ImageButton) view;
            int currentAmount = Integer.parseInt(rearrangeCounter.getText().toString());

            if( (currentAmount == 0 && !activated)) {
                return;
            }

            int newAmount;
            if(activated) {
                newAmount = currentAmount + 1;
                makeCupDeactivated();

            } else {
                newAmount = currentAmount -1;
                makeCupActivated();
            }
            activated = !activated;

            if(newAmount == 0) {
                rearrangeBtn.setBackgroundColor(Color.WHITE);
            } else {
                rearrangeBtn.setBackgroundColor(Color.BLACK);
            }
            rearrangeCounter.setText(String.valueOf(newAmount));
        }

        //TODO catch possibilities for the different counter

        private void makeCupActivated() {
            btn.setImageResource(R.drawable.cup_white_24px);
        }
        private void makeCupDeactivated() {
            btn.setImageResource(R.drawable.cup_grey_24px);
        }
        private void makeCupInvisible() {
            btn.setVisibility(View.INVISIBLE);
        }
    }

    private void startRearrangeDialog(int team, int cupAmount) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_rearrange);
        dialog.setTitle("Title...");
        rearrangeCounter = dialog.findViewById(R.id.amount_cups);
        rearrangeBtn = dialog.findViewById(R.id.button_success);
        ImageButton failureButton = dialog.findViewById(R.id.button_failure);
        LinearLayout pyramidLayout = dialog.findViewById(R.id.rearrange_pyramid);

        if(team == TEAM_1) {
            fillPyramidDown(pyramidLayout, false);
        } else {
            fillPyramidUp(pyramidLayout, false);
        }

        rearrangeBtn.setEnabled(false);
        rearrangeCounter.setText(String.valueOf(cupAmount));
        rearrangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO Swap pyramid layouts
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
