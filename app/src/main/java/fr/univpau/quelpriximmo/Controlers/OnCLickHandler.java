package fr.univpau.quelpriximmo.Controlers;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import fr.univpau.quelpriximmo.R;
import fr.univpau.quelpriximmo.SearchActivity;
import fr.univpau.quelpriximmo.SettingsActivity;

public class OnCLickHandler implements View.OnClickListener{
    private View btn;

    public OnCLickHandler(View v) {
        this.btn = v;
        this.btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.settingsButton:
                final Intent i = new Intent(btn.getContext() , SettingsActivity.class);
                btn.getContext().startActivity(i);
        }
    }
}
