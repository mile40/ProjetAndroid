package fr.univpau.quelpriximmo.Controlers;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarHandler implements SeekBar.OnSeekBarChangeListener {
    private SeekBar sb;
    private TextView tw;

    public SeekBarHandler(SeekBar sb, TextView tw) {
        this.sb = sb;
        this.tw = tw;
    }

    public void SeekBarHandler(SeekBar sb, TextView tw){
        this.sb = sb;
        this.tw = tw;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.i("Debug", String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
