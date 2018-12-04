package com.example.alienware.project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceOrderCheck extends ArrayAdapter<Service_In_Order> {

    private Activity context;
    private List<Service_In_Order> service_in_orders;

    public ServiceOrderCheck(Activity context, List<Service_In_Order>service_in_orders){
        super(context, R.layout.user_info,service_in_orders);
        this.context=context;
        this.service_in_orders=service_in_orders;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();

        View ViewServicesList=inflater.inflate(R.layout.user_info,null,true);
        TextView textViewName=(TextView)ViewServicesList.findViewById(R.id.userInfo);

        Service_In_Order serviceInOrder=service_in_orders.get(position);

        textViewName.setText(serviceInOrder.getService_name()+" : "+serviceInOrder.getService_provider_name());

        return ViewServicesList;


    }
}
