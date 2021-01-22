package fr.univpau.quelpriximmo.Controlers;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import fr.univpau.quelpriximmo.R;

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
                //TODO: faire le code
                break;
                //slider permettant de gerer la distance de recherrche par defaut
            case R.id.param_def_dist:
                //TODO: faire le code
                    tw.setText(tw + " " + String.valueOf(progress) + "m");
                break;
            default:
                Log.e("Debug", "SeekBar "+seekBar.getId()+" inconnu.");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
