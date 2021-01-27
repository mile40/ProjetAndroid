package fr.univpau.quelpriximmo.Controlers;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import fr.univpau.quelpriximmo.R;
import fr.univpau.quelpriximmo.ResultsActivity;
import fr.univpau.quelpriximmo.SearchActivity;
import fr.univpau.quelpriximmo.SettingsActivity;

//cette classe est une classe permettant de gérer les clics sur les boutons
public class OnCLickHandler implements View.OnClickListener{
    private View btn;


    public OnCLickHandler(View v) {
        this.btn = v;
        this.btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Intent i;
        switch(v.getId()){
            //si c'est le bouton permettant d'acceder à la page de paramètres
            case R.id.settingsButton:
                i = new Intent(btn.getContext() , SettingsActivity.class);
                btn.getContext().startActivity(i);
                break;

                //bouton permettant de lancer la requette http et d'aller sur la page de resultat
            case R.id.btn_recherche:
                i = new Intent(btn.getContext() , ResultsActivity.class);
                btn.getContext().startActivity(i);
                break;

            default:
                Log.e("Debug", "Bouton "+v.getId()+" inconnu.");
                break;

        }
    }
}
