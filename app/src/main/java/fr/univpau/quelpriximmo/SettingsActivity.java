package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import fr.univpau.quelpriximmo.Controlers.SeekBarHandler;

public class SettingsActivity extends Activity {
    SeekBar sb;
    TextView valTxt;
    String def_dist_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingactivity);

        //récupération des éléments du layout
        sb = (SeekBar) findViewById(R.id.param_def_dist);
        valTxt = (TextView) findViewById(R.id.dist_default_value);
        def_dist_text = (String) valTxt.getText();

        //initialiation de certaines valeurs
        valTxt.setText(def_dist_text + " " + String.valueOf(sb.getProgress()) + "m");

        //abonnements aux éventuels événements
        SeekBarHandler sbh = new SeekBarHandler(sb, valTxt);

    }



}

