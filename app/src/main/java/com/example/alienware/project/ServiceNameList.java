package com.example.alienware.project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceNameList extends ArrayAdapter<Service_Name> {

    private Activity context;
    private List<Service_Name> ServiceName;

    public ServiceNameList(Activity context, List<Service_Name>ServiceName){
        super(context, R.layout.user_info,ServiceName);
        this.context=context;
        this.ServiceName=ServiceName;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();

        View ViewServicesList=inflater.inflate(R.layout.user_info,null,true);
        TextView textViewName=(TextView)ViewServicesList.findViewById(R.id.userInfo);

        Service_Name service_name=ServiceName.get(position);

        textViewName.setText(service_name.getName()+" : "+service_name.getService());

        return ViewServicesList;


    }
}
