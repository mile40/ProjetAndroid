package fr.univpau.quelpriximmo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.univpau.quelpriximmo.Models.ImmoModel;
import fr.univpau.quelpriximmo.R;

public class ImmoAdapter extends ArrayAdapter<ImmoModel> {


    public ImmoAdapter(Context context, List<ImmoModel> list) {
        super(context,0, list);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item,parent, false);
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
                break;
            case "Appartement":
                break;
            case "Dependance":
                break;
            case "Terrain":
                break;
            case "Inconnu":
                break;
            default:
                break;
        }
        /*viewHolder.icon_item.setChecked(task.getChecked());
        viewHolder.task_name.setText(task.getTaskName());
        viewHolder.task_date.setText(task.getTaskDate().toString());
        viewHolder.button*/
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
