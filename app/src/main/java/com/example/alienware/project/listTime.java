package com.example.alienware.project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class listTime extends ArrayAdapter<ServicesTime>{
    private Activity context;
    private List<ServicesTime>timeList;

    public listTime(Activity context, List<ServicesTime>timeList){
        super(context,R.layout.list_times,timeList);

        this.context=context;
        this.timeList=timeList;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();

        View ViewTimeList=inflater.inflate(R.layout.list_times,null,true);
        TextView textViewDate=(TextView)ViewTimeList.findViewById(R.id.textViewDate);
        TextView textViewTime=(TextView)ViewTimeList.findViewById(R.id.textViewTime);

        ServicesTime servicesTime=timeList.get(position);

        textViewDate.setText(servicesTime.getWeek());
        textViewTime.setText(servicesTime.getStartTimeHour()+" : "+servicesTime.getStartTimeMin()+" - "+servicesTime.getEndTimeHour()+" : "+servicesTime.getEndTimeMin());

        return ViewTimeList;


    }
}
