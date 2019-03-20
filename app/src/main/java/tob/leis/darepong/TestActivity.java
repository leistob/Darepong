package tob.leis.darepong;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private int pyramidSize;
    int cupsToPlace;

    LinearLayout team1Pyramid;
    TextView team1Counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        team1Pyramid = findViewById(R.id.team1_layout_test);
        team1Counter = findViewById(R.id.team1_counter_test);

        pyramidSize = 10;
        cupsToPlace = 5;
        team1Counter.setText(String.valueOf(cupsToPlace));

        fillPyramidDown(team1Pyramid);
    }

    public int getCurrentAmount() {
        return Integer.parseInt(team1Counter.getText().toString());
    }

    public void subtract() {
        int currentAmount = Integer.parseInt(team1Counter.getText().toString());
        team1Counter.setText(String.valueOf(currentAmount-1));
    }

    public void add() {
        int currentAmount = Integer.parseInt(team1Counter.getText().toString());
        team1Counter.setText(String.valueOf(currentAmount+1));
    }

    private void fillPyramidDown(LinearLayout teamPyramid) {

        switch(pyramidSize) {
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
            btn.setImageResource(R.drawable.cup_grey_24px);
            btn.setOnClickListener(new TestActivity.ButtonListener());
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setPadding(0,0,0,0);
            btn.setLayoutParams(btnParams);
            btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            layout.addView(btn);
        }
        return layout;
    }

    class ButtonListener implements View.OnClickListener {

        private boolean markSelected = false;

        private ImageButton btn;

        @Override
        public void onClick(View view) {

            this.btn = (ImageButton) view;

            markCupSelected();
        }
        private void markCupSelected() {
            if(markSelected) {
                markSelected = !markSelected;
                btn.setImageResource(R.drawable.cup_grey_24px);
                add();
            } else {
                if(getCurrentAmount()<=0) {
                    //TODO make OK button clickable
                    return;
                }
                markSelected = !markSelected;
                btn.setImageResource(R.drawable.cup_white_24px);
                subtract();
            }
        }
    }

    public List<List<Integer>> getArr() {
        //TODO
        //Get every object from pyramid
        //if resource is grey: put 0, if resource is white, put 1
        //return this list back to gameactivity that the linearlayout can be filled with it
        return null;
    }
}
