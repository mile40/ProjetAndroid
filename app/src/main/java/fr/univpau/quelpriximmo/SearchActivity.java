package fr.univpau.quelpriximmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton ib;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity);
        getSupportActionBar().hide();
        ib = findViewById(R.id.settingsButton);
        ib.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.settingsButton:
                final Intent i = new Intent(SearchActivity.this, SettingsActivity.class);
                startActivity(i);
        }
    }
}