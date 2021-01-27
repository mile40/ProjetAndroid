package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.quelpriximmo.utils.DataBaseHandler;
import fr.univpau.quelpriximmo.utils.Variables;

public class StatsActivity extends Activity implements View.OnClickListener {

    protected ImageButton btn;
    protected BarChart chart;
    private DataBaseHandler db;
    private List<Double> prix;
    private BarDataSet dataSet;
    private ArrayList values, labels;
    private int rep[] = {0,0,0,0,0,0,0,0};
    private BarData data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statsactivity);
        db = new DataBaseHandler(this);
        db.openDatabase();
        setTitle("Répartition des prix");
        btn = (ImageButton) findViewById(R.id.btn_retour_stats);
        btn.setOnClickListener(this);
        chart = findViewById(R.id.stats_barchart);
        prix = new ArrayList<>();
        prix = db.getPrix(Variables.type_bien, Variables.distance);
        values = new ArrayList();

        //instanciation des labels
        labels = new ArrayList();
        labels.add("<50k €");
        labels.add("50-100k €");
        labels.add("100-150k €");
        labels.add("150-200k €");
        labels.add("200-250k €");
        labels.add("250-300k €");
        labels.add("300-350k €");
        labels.add("350-400k €");
        labels.add("400-450k €");
        labels.add("450-500k €");
        labels.add(">500k €");

        for(int i = 0; i < prix.size(); i++){
            if(prix.get(i)< 50000){
                rep[0] ++;
            }
            if(prix.get(i)>= 50000 && prix.get(i)<100000) {
                rep[1] ++;
            }
            if(prix.get(i)>= 100000 && prix.get(i)<150000) {
                rep[2] ++;
            }
            if(prix.get(i)>= 150000 && prix.get(i)<200000) {
                rep[3] ++;
            }
            if(prix.get(i)>= 250000 && prix.get(i)<300000) {
                rep[4] ++;
            }
            if(prix.get(i)>= 350000 && prix.get(i)<400000) {
                rep[5] ++;
            }
            if(prix.get(i)>= 450000 && prix.get(i)<500000) {
                rep[6] ++;
            }
            if(prix.get(i)>= 500000 ) {
                rep[7] ++;
            }
        }

        for(int i = 0; i < 8; i++){
            values.add(new BarEntry(rep[i], i));
        }

        dataSet = new BarDataSet(values, "répartition des prix");
        dataSet.setValueTextSize(10f);
        chart.animateY(1000);
        chart.getLegend().setTextSize(10f);
        chart.getXAxis().setTextSize(8f);
        chart.setDrawGridBackground(false);
        data = new BarData((IBarDataSet) labels, dataSet);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
