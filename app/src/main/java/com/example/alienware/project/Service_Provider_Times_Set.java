package com.example.alienware.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;
import com.google.firebase.database.ValueEventListener;

public class Service_Provider_Times_Set extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceWeek;
    private DatabaseReference databaseReferenceName;

    private Button buttonStart;
    private Button buttonEnd;
    private Button buttonSave;
    private Button buttonClear;
    private Button buttonBack;


    private TextView textViewStart;
    private TextView textViewEnd;

    private Spinner spinnerDate;

    private TimePicker TP;

    String weekday;

    String startTime;
    String sh;
    String sm;
    int startHour;
    int startMinutes;

    String endTime;
    String eh;
    String em;
    int endHour;
    int endMinutes;

    private String username;

    ServiceWeek serviceWeek;
    private Service_Provider_Account service_provider_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__provider__times__set);

        buttonStart=(Button)findViewById(R.id.buttonStart);
        buttonEnd=(Button)findViewById(R.id.buttonEnd);
        buttonSave=(Button)findViewById(R.id.buttonSave);
        buttonClear=(Button)findViewById(R.id.buttonClear);
        buttonBack=(Button)findViewById(R.id.buttonBack);


        textViewStart=(TextView)findViewById(R.id.textViewStart);
        textViewEnd=(TextView)findViewById(R.id.textViewEnd);

        spinnerDate=(Spinner)findViewById(R.id.spinnerDate);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.Date,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(adapter);
        spinnerDate.setOnItemSelectedListener(this);

        TP=(TimePicker)findViewById(R.id.TP);
        TP.setIs24HourView(true);

        buttonStart.setOnClickListener(this);
        buttonEnd.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        buttonEnd.setEnabled(false);
        buttonSave.setEnabled(false);

        database=FirebaseDatabase.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();

        serviceWeek=new ServiceWeek();
        service_provider_account=new Service_Provider_Account();

    }


    private void start(){

        startHour=TP.getCurrentHour();
        startMinutes=TP.getCurrentMinute();
        sh=TP.getCurrentHour().toString().trim();
        sm=TP.getCurrentMinute().toString().trim();
        startTime=(TP.getCurrentHour().toString().trim()+" : "+TP.getCurrentMinute().toString().trim());
        weekday=spinnerDate.getSelectedItem().toString().trim();

        if(TextUtils.isEmpty(weekday)){
            Toast.makeText(this,"You have to select a day", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReference=database.getReference(user.getUid()+"time");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot timeSnapshot: dataSnapshot.getChildren()){
                    ServicesTime timeLists=timeSnapshot.getValue(ServicesTime.class);
                    if(weekday.equals(timeLists.getWeek())){
                        int a=Integer.parseInt(sh);
                        int aa=Integer.parseInt(timeLists.getStartTimeHour());
                        int b=Integer.parseInt(sm);
                        int bb=Integer.parseInt(timeLists.getStartTimeMin());
                        int c=Integer.parseInt(eh);
                        int cc=Integer.parseInt(timeLists.getEndTimeHour());
                        int d=Integer.parseInt(em);
                        int dd=Integer.parseInt(timeLists.getEndTimeMin());

                        if(a<=aa&&c>=cc){
                            return;
                        }


                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceName=FirebaseDatabase.getInstance().getReference("Service_Provider_Account").child(user.getUid());

        databaseReferenceName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                service_provider_account = dataSnapshot.getValue(Service_Provider_Account.class);
                username=service_provider_account.getName();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        textViewStart.setText(spinnerDate.getSelectedItem().toString().trim()+"                         "+TP.getCurrentHour().toString().trim()+" : "+TP.getCurrentMinute().toString().trim()+"      -   ");
        Toast.makeText(this,"Start time set successful", Toast.LENGTH_SHORT).show();
        spinnerDate.setEnabled(false);
        buttonSave.setEnabled(false);
        buttonEnd.setEnabled(true);

    }
    private void end(){
        endHour=TP.getCurrentHour();
        endMinutes=TP.getCurrentMinute();

        if(startHour!=0&&endHour==0){
            endHour=24;
        }

        eh=TP.getCurrentHour().toString().trim();
        em=TP.getCurrentMinute().toString().trim();
        endTime=(TP.getCurrentHour().toString().trim()+" : "+TP.getCurrentMinute().toString().trim());

        if(startTime.equals(endTime)){
            Toast.makeText(this,"You have to select a 'period' of time", Toast.LENGTH_SHORT).show();
            return;
        }
        if(startHour>endHour){
            Toast.makeText(this,"The start time should be early than the end time", Toast.LENGTH_SHORT).show();
            return;
        }
        if(startHour==endHour && startMinutes>endMinutes){
            Toast.makeText(this,"The start time should be early than the end time", Toast.LENGTH_SHORT).show();
            return;
        }


        textViewEnd.setText(TP.getCurrentHour().toString().trim()+" : "+TP.getCurrentMinute().toString().trim());

        Toast.makeText(this,"End time set successful, you are ready to upload", Toast.LENGTH_SHORT).show();

        spinnerDate.setEnabled(false);
        buttonSave.setEnabled(true);
        buttonStart.setEnabled(false);
        buttonEnd.setEnabled(false);

    }
    private void save(){

        FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReferenceWeek=FirebaseDatabase.getInstance().getReference("Week_Plan").child(weekday);




        databaseReference=FirebaseDatabase.getInstance().getReference("Service_Provider_Account").child(user.getUid()).child("time");

        String id=databaseReference.push().getKey();


        ServicesTime servicesTime=new ServicesTime(id,weekday,sh,sm,eh,em);

        serviceWeek=new ServiceWeek(user.getUid(),username,weekday);

        databaseReferenceWeek.child(id).setValue(serviceWeek);

        databaseReference.child(id).setValue(servicesTime);

        Toast.makeText(Service_Provider_Times_Set.this, "Save Successful", Toast.LENGTH_SHORT).show();

        spinnerDate.setEnabled(true);
        buttonStart.setEnabled(true);
        buttonEnd.setEnabled(false);

    }
    private void clear(){

        spinnerDate.setEnabled(true);
        buttonSave.setEnabled(true);
        buttonEnd.setEnabled(false);
        textViewStart.setText("");
        textViewEnd.setText("");

    }

    public void onClick(View view) {
        if(view==buttonStart){
            start();
        }
        if(view==buttonEnd){
            end();
        }
        if(view==buttonSave){
            save();

        }
        if(view==buttonClear){
           clear();
        }
        if(view==buttonBack){
            finish();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),type,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
