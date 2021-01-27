package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.quelpriximmo.Adapter.ImmoAdapter;
import fr.univpau.quelpriximmo.Models.ImmoModel;
import fr.univpau.quelpriximmo.utils.DataBaseHandler;
import fr.univpau.quelpriximmo.utils.Variables;

public class ResultsActivity extends Activity implements View.OnClickListener {

    protected DataBaseHandler db;
    protected List<ImmoModel> listImmo;
    protected ListView list;
    protected ImageButton btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultsactivity);
        btn = (ImageButton) findViewById(R.id.btn_retour);
        btn.setOnClickListener(this);
        try{
            db = new DataBaseHandler(this);
            db.openDatabase();
        }catch(Exception e){
            Log.e("DEBUG_LIST", Log.getStackTraceString(e));
        }
        listImmo = new ArrayList<ImmoModel>();
        listImmo = db.getImmo(Variables.type_bien, Variables.distance, Variables.nb_pieces, (int) Variables.prix_min);
        ImmoAdapter adapter = new ImmoAdapter(this, listImmo);
        list = (ListView) findViewById(R.id.result_list);
        list.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
