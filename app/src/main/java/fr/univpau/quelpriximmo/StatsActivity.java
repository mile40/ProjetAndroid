package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.quelpriximmo.Models.ImmoModel;
import fr.univpau.quelpriximmo.utils.DataBaseHandler;
import fr.univpau.quelpriximmo.utils.Variables;

public class StatsActivity extends Activity implements View.OnClickListener {

    protected ImageButton btn;
    protected BarChart chart;
    private DataBaseHandler db;
    private List<ImmoModel> prix;
    private BarDataSet dataSet;
    private ArrayList values, labels;
    private int rep[] = {0,0,0,0,0,0,0,0,0,0,0};
    private BarData data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("DEBUG_STAT", "ALLO");
        setContentView(R.layout.statsactivity);
        db = new DataBaseHandler(this);
        db.openDatabase();
        setTitle("Répartition des prix");
        btn = (ImageButton) findViewById(R.id.btn_retour_stats);
        btn.setOnClickListener(this);
        chart = findViewById(R.id.stats_barchart);
        values = new ArrayList();
        prix = ResultsActivity.getImmos();
        Log.i("DEBUG_STAT", ""+prix.size());
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
            if(prix.get(i).getPrix()< 50000){
                Log.i("DEBUG_STAT", "<50k €");
                rep[0] ++;
            }
            if(prix.get(i).getPrix()>= 50000 && prix.get(i).getPrix()<100000) {
                Log.i("DEBUG_STAT", "50-100k €");
                rep[1] ++;
            }
            if(prix.get(i).getPrix()>= 100000 && prix.get(i).getPrix()<150000) {
                Log.i("DEBUG_STAT", "100-150k €");
                rep[2] ++;
            }
            if(prix.get(i).getPrix()>= 150000 && prix.get(i).getPrix()<200000) {
                Log.i("DEBUG_STAT", "150-200k €");
                rep[3] ++;
            }
            if(prix.get(i).getPrix()>= 200000 && prix.get(i).getPrix()<250000) {
                Log.i("DEBUG_STAT", "200-250k €");
                rep[4] ++;
            }
            if(prix.get(i).getPrix()>= 250000 && prix.get(i).getPrix()<300000) {
                Log.i("DEBUG_STAT", "250-300k €");
                rep[5] ++;
            }
            if(prix.get(i).getPrix()>= 300000 && prix.get(i).getPrix()<350000) {
                Log.i("DEBUG_STAT", "300-350k €");
                rep[6] ++;
            }
            if(prix.get(i).getPrix()>= 350000 && prix.get(i).getPrix()<400000) {
                Log.i("DEBUG_STAT", "350-400k €");
                rep[7] ++;
            }
            if(prix.get(i).getPrix()>= 400000 && prix.get(i).getPrix()<450000) {
                Log.i("DEBUG_STAT", "400-450k €");
                rep[8] ++;
            }
            if(prix.get(i).getPrix()>= 450000 && prix.get(i).getPrix()<500000) {
                Log.i("DEBUG_STAT", "450-500k €");
                rep[9] ++;
            }
            if(prix.get(i).getPrix()>= 500000 ) {
                Log.i("DEBUG_STAT", ">500k €");
                rep[10] ++;
            }
        }

        for(int i = 0; i < 11; i++){
            values.add(new BarEntry(rep[i], i));
        }

        dataSet = new BarDataSet(values, "nombre de biens");
        dataSet.setValueTextSize(10f);
        chart.animateY(1000);
        chart.getLegend().setTextSize(10f);
        chart.getXAxis().setTextSize(8f);
        chart.setDrawGridBackground(false);
        chart.setDescription("répartition des prix");
        data = new BarData(labels, dataSet);
        chart.setData(data);


    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
