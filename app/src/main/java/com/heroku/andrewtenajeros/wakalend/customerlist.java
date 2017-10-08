package com.heroku.andrewtenajeros.wakalend;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrew Tenajeros on 9/4/2017.
 */

public class customerlist extends ArrayAdapter<Client> {

    private Activity context;
    private List<Client> customerlists;

    public customerlist(Activity context, List<Client> customerlists){
        super(context, R.layout.list_customer, customerlists);
        this.context = context;
        this.customerlists = customerlists;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_customer,null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textView_customername);
        TextView textViewBalance = (TextView) listViewItem.findViewById(R.id.textView_balanceval);

        Client client = customerlists.get(position);
        String cname = client.getFirstname() + " " + client.getLastname();
        textViewName.setText(cname);
        textViewBalance.setText(client.getBalance());

        return listViewItem;
    }
}
