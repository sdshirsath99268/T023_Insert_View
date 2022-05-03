package com.example.t023_insert_view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class UserAdapter extends ArrayAdapter<User> {

    Activity context ;
    List<User> list ;

    public UserAdapter(Activity context, List<User> list) {
        super(context, R.layout.activity_user,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.user_list_layout , null , true);

        TextView name = (TextView) convertView.findViewById(R.id.lst_name);
        TextView email = (TextView) convertView.findViewById(R.id.lst_email);
        TextView contact = (TextView) convertView.findViewById(R.id.lst_contact);
        TextView city = (TextView) convertView.findViewById(R.id.lst_city);
        TextView lang = (TextView) convertView.findViewById(R.id.lst_lang);

        User user = list.get(position);

        name.setText(user.getName());
        email.setText(user.getEmail());
        contact.setText(user.getContact());
        city.setText(user.getCity());
        lang.setText(user.getLang());

        return convertView ;
    }
}
