package com.uh4t.imotocareapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uh4t.imotocareapp.Models.Personell;
import com.uh4t.imotocareapp.R;

import java.util.ArrayList;

public class PersonelAdapter  extends ArrayAdapter<Personell> {
    private Context context;
    private ArrayList<Personell> _personels;
    private static class ViewHolder {
        TextView Name, GarageName, Type;
        ImageView PeronelImage;
    }

    public PersonelAdapter(Context context, ArrayList<Personell> _people) {
        super(context, R.layout.personel_list_row, _people);
        _personels = _people;
        this.context = context;
    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return _personels.size();
    }

    @Override
    public Personell getItem(int position) {
        return _personels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.personel_list_row, parent, false);

            viewHolder.Name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.GarageName = (TextView) convertView.findViewById(R.id.garage_name);
            viewHolder.Type = (TextView) convertView.findViewById(R.id.peronel_type);
            viewHolder.PeronelImage = convertView.findViewById(R.id.imgView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.Name.setText(_personels.get(position).getFname() + " " + _personels.get(position).getLname() );
        viewHolder.GarageName.setText(_personels.get(position).getGarageName());
        viewHolder.Type.setText(_personels.get(position).getType());
        Picasso.with(context).load(_personels.get(position).getImageUri()).into(viewHolder.PeronelImage);

        return convertView;
    }
}
