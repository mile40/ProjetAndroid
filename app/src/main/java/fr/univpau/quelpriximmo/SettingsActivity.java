package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import fr.univpau.quelpriximmo.Controlers.OnCLickHandler;
import fr.univpau.quelpriximmo.Controlers.SeekBarHandler;

public class SettingsActivity extends Activity implements View.OnClickListener{
    SeekBar sb;
    TextView valTxt;
    String def_dist_text;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingactivity);

        //récupération des éléments du layout
        sb = (SeekBar) findViewById(R.id.param_def_dist);
        valTxt = (TextView) findViewById(R.id.dist_default_value);
        def_dist_text = (String) valTxt.getText();
        btn_save = (Button) findViewById(R.id.save_btn);

        //initialiation de certaines valeurs
        SharedPreferences params = PreferenceManager.getDefaultSharedPreferences(this);
        int rayon = params.getInt("rayon_defaut", 500);
        sb.setProgress(rayon);
        valTxt.setText(sb.getProgress()+"m");
        Log.i("DEBUG_SLIDER", Integer.toString(rayon));

        //abonnements aux éventuels événements
        SeekBarHandler sbh = new SeekBarHandler(sb, valTxt);
        btn_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

