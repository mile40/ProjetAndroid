package fr.univpau.quelpriximmo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.univpau.quelpriximmo.DetailsActivity;
import fr.univpau.quelpriximmo.Models.ImmoModel;
import fr.univpau.quelpriximmo.R;

public class ImmoAdapter extends ArrayAdapter<ImmoModel> {

    public Context ctx;

    public ImmoAdapter(Context context, List<ImmoModel> list) {
        super(context,0, list);
        ctx = context;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        ImmoViewHolder viewHolder = (ImmoViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ImmoViewHolder();
            viewHolder.icon_item = (ImageView) convertView.findViewById(R.id.icon_item);
            viewHolder.type_item = (TextView) convertView.findViewById(R.id.type_bien_item);
            viewHolder.prix_item = (TextView) convertView.findViewById(R.id.prix_item);
            viewHolder.dist_item = (TextView) convertView.findViewById(R.id.dist_item);
            viewHolder.btn_stats = (ImageButton) convertView.findViewById(R.id.btn_stats);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        ImmoModel immo = getItem(pos);
        switch(immo.getType_bien()){
            case "Maison":
                viewHolder.icon_item.setImageResource(R.drawable.ic_house);
                break;
            case "Appartement":
                viewHolder.icon_item.setImageResource(R.drawable.ic_apps);
                break;
            case "Dependance":
                viewHolder.icon_item.setImageResource(R.drawable.ic_dep);
                break;
            case "Terrain":
                viewHolder.icon_item.setImageResource(R.drawable.ic_terrain);
                break;
            case "Inconnu":
                viewHolder.icon_item.setImageResource(R.drawable.ic_na);
                break;
            default:
                break;
        }

        viewHolder.type_item.setText(immo.getType_bien());
        if(immo.getPrix() < 0) {
            viewHolder.prix_item.setText(" Prix : N/A ");
        }else{
            viewHolder.prix_item.setText(" Prix : " + (int) immo.getPrix() + " € ");
        }
        viewHolder.dist_item.setText(" Distance : " + (int) immo.getDistance() + " m ");
        viewHolder.btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DetailsActivity.class);
                i.putExtra("IMMO_TYPE", immo.getType_bien());
                i.putExtra("IMMO_PIECES", Integer.toString(immo.getNb_pieces()));
                i.putExtra("IMMO_DIST", Integer.toString( (int)immo.getDistance() ));
                i.putExtra("IMMO_PRIX", Integer.toString((int)immo.getPrix() ));
                i.putExtra("IMMO_ADR", immo.getAdress());
                ctx.startActivity(i);
            }
        });
        return convertView;
    }

    private class ImmoViewHolder{
        public ImageView icon_item;
        public TextView type_item;
        public TextView prix_item;
        public TextView dist_item;
        public ImageButton btn_stats;
    }
}
