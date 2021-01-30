package fr.univpau.quelpriximmo.Controlers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.prefs.PreferenceChangeEvent;

import fr.univpau.quelpriximmo.R;
import fr.univpau.quelpriximmo.utils.Variables;

//classe permettant d'abstraire la gestion des événemments relatifs aux sliders
public class SeekBarHandler implements SeekBar.OnSeekBarChangeListener {
    private SeekBar sb;
    private TextView tw;

    public SeekBarHandler(SeekBar sb, TextView tw) {
        this.sb = sb;
        this.sb.setOnSeekBarChangeListener(this);
        this.tw = tw;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            //slider de la page de recherche
            case R.id.slider_dist:
                tw.setText("0 m - "+String.valueOf(progress) + " m");
                Variables.distance = progress;
                break;
                //slider permettant de gerer la distance de recherrche par defaut
            case R.id.param_def_dist:
                tw.setText(String.valueOf(progress) + " m");
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(seekBar.getContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("rayon_defaut", progress);
                editor.apply();
                break;
            default:
                Log.e("Debug", "SeekBar "+seekBar.getId()+" inconnu.");
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
