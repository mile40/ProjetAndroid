package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity implements View.OnClickListener {

    protected ImageView immo_img;
    protected TextView immo_type, immo_prix, immo_dist, immo_pieces, immo_adr;
    protected ImageButton btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsactivity);

        btn = (ImageButton) findViewById(R.id.btn_retour);
        btn.setOnClickListener(this);

        Intent i = getIntent();
        immo_type = (TextView) findViewById(R.id.type_bien);
        immo_type.setText(i.getStringExtra("IMMO_TYPE"));

        immo_prix = (TextView) findViewById(R.id.prix_bien);
        immo_prix.setText(i.getStringExtra("IMMO_PRIX"));

        immo_dist = (TextView) findViewById(R.id.text_dist_bien);
        immo_dist.setText("A " + i.getStringExtra("IMMO_DIST") + "m de votre position");

        immo_pieces = (TextView) findViewById(R.id.nb_piece_txt);
        immo_pieces.setText(i.getStringExtra("IMMO_PIECES"));

        immo_adr = (TextView) findViewById(R.id.adr_bien);
        immo_adr.setText(i.getStringExtra("IMMO_ADR"));

        immo_img = (ImageView) findViewById(R.id.icone_bien);
        switch(i.getStringExtra("IMMO_TYPE")){
            case "Maison":
                immo_img.setImageResource(R.drawable.ic_house);
                break;
            case "Appartement":
                immo_img.setImageResource(R.drawable.ic_apps);
                break;
            case "Dependance":
                immo_img.setImageResource(R.drawable.ic_dep);
                break;
            case "Terrain":
                immo_img.setImageResource(R.drawable.ic_terrain);
                break;
            case "Inconnu":
                immo_img.setImageResource(R.drawable.ic_na);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
