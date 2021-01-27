package fr.univpau.quelpriximmo.Controlers;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Spinner;

import fr.univpau.quelpriximmo.R;
import fr.univpau.quelpriximmo.utils.Variables;

public class SpinnerHandler implements AdapterView.OnItemSelectedListener {
    Spinner e;

    public SpinnerHandler(Spinner item){
        e = item;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (e.getId()){
            case R.id.spin_type_biens:
                String val = parent.getItemAtPosition(position).toString();
                Variables.type_bien = val;
                break;
            case R.id.spinner_pieces:
                switch(parent.getItemAtPosition(position).toString()){
                    case "Tout":
                        Variables.nb_pieces = -1;
                        break;
                    case "1 piece":
                        Variables.nb_pieces = 1;
                        break;
                    case "2 pieces":
                        Variables.nb_pieces = 2;
                        break;
                    case "3 pieces":
                        Variables.nb_pieces = 3;
                        break;
                    case "4 pieces":
                        Variables.nb_pieces = 4;
                        break;
                    case "+ de 4 pieces":
                        Variables.nb_pieces= 5;
                        break;
                    default:
                        Log.e("DEBUG_SPINNER", "Type inconnu: "+parent.getItemAtPosition(position).toString());
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
