package tob.leis.darepong;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class IngameActivity extends AppCompatActivity {

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

        private boolean clickedAlready = false;

        @Override
        public void onClick(View view) {

            if(clickedAlready){

            } else {

            }

            //TODO:
            //1) Popup dialog menu with dare and fulfilled yes no and selected cup
            //2) Depending on yes no the cub will be removed
            //3) Somehow remember whether the button was already clicked once
            //4) If so popup with no dare available and Drink up

        }
    }
}
