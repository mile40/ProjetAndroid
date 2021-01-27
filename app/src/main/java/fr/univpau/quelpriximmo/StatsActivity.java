package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;

public class StatsActivity extends Activity implements View.OnClickListener {

    protected ImageButton btn;
    protected BarChart chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = (ImageButton) findViewById(R.id.btn_retour_stats);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
