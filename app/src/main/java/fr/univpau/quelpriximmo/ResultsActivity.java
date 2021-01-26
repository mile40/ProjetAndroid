package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import fr.univpau.quelpriximmo.Models.ImmoModel;
import fr.univpau.quelpriximmo.utils.DataBaseHandler;

public class ResultsActivity extends Activity {

    protected DataBaseHandler db;
    protected List<ImmoModel> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DataBaseHandler(this);
        db.openDatabase();

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
