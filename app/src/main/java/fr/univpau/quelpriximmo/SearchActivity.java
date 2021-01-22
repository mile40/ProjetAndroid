package fr.univpau.quelpriximmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import fr.univpau.quelpriximmo.Controlers.OnCLickHandler;
import fr.univpau.quelpriximmo.Controlers.SeekBarHandler;

public class SearchActivity extends AppCompatActivity{
    private ImageButton btn_param;
    private SeekBar dist_recherche;
    private TextView tw;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity);
        getSupportActionBar().hide();

        //récupération des éléments du layout nous intéressant
        btn_param =(ImageButton) findViewById(R.id.settingsButton);
        dist_recherche = (SeekBar) findViewById(R.id.slider_dist);
        tw = (TextView) findViewById(R.id.lab_dist_intersection);

        //affectation des éventuels éventHandlers
        SeekBarHandler sbh = new SeekBarHandler(dist_recherche, tw);
        OnCLickHandler och = new OnCLickHandler((View) btn_param);

    }
}