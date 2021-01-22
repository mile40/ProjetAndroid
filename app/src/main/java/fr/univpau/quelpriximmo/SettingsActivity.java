package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    SeekBar sb;
    TextView valTxt;
    String def_dist_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingactivity);

        sb = (SeekBar) findViewById(R.id.param_def_dist);
        valTxt = (TextView) findViewById(R.id.dist_default_value);
        valTxt.setText(String.valueOf(sb.getProgress()) + "m");

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        valTxt.setText(String.valueOf(progress) + "m");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar){
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar){
    }

}

