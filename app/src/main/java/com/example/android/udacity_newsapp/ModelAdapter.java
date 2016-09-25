package com.example.android.udacity_newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by temp on 25/09/2016.
 */
public class ModelAdapter extends ArrayAdapter<Model> {

    public ModelAdapter(Context context, List<Model> modelList) {
        super(context, 0, modelList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Model model= getItem(position);

        TextView webTitle= (TextView) listItemView.findViewById(R.id.tv_listItme_webTitle);
        TextView authors= (TextView) listItemView.findViewById(R.id.tv_listItme_author);

        webTitle.setText(model.getWebTitle().toString());
        authors.setText(model.getAuthors().toString());


        return listItemView;
    }
}
