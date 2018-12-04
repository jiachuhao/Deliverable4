package com.example.alienware.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Service_Provider_Times extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button buttonSet;
    private Button buttonDelete;
    private Button buttonBack;

    private FirebaseAuth firebaseAuth;

    private String Tid;

    private ListView listViewTime;

    private List<ServicesTime> timeList;

    private FirebaseDatabase database;

    private DatabaseReference databaseReference;

    private String week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__provider__times);

        buttonSet=(Button)findViewById(R.id.buttonSet);
        buttonDelete=(Button)findViewById(R.id.buttonDelete);
        buttonBack=(Button)findViewById(R.id.buttonBack);

        buttonSet.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        database=FirebaseDatabase.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();

        final FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReference=database.getReference("Service_Provider_Account").child(user.getUid()).child("time");


        listViewTime=(ListView) findViewById(R.id.TimeSchedule);
        timeList=new ArrayList<>();

        listViewTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicesTime servicesTime=timeList.get(position);
                Tid=servicesTime.getId();
                week=servicesTime.getWeek();
                view.setSelected(true);
                //Toast.makeText(Service_Provider_Times.this, "Selected from time schedual", Toast.LENGTH_SHORT).show();
                Toast.makeText(Service_Provider_Times.this, "Selected from time schedual", Toast.LENGTH_SHORT).show();
            }
        });

        listViewTime.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ServicesTime sservicesTime=timeList.get(position);

                showChange(sservicesTime.getId(),sservicesTime.getWeek(),sservicesTime.getStartTimeHour(),sservicesTime.getStartTimeMin(),sservicesTime.getEndTimeHour(),sservicesTime.getEndTimeMin());
                return false;

            }
        });


    }
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timeList.clear();
                for(DataSnapshot timeSnapshot: dataSnapshot.getChildren()){
                    ServicesTime timeLists=timeSnapshot.getValue(ServicesTime.class);
                    timeList.add(timeLists);
                }
                listTime adapter=new listTime(Service_Provider_Times.this,timeList);
                listViewTime.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showChange(final String id, String weekday,String sh,String sm, String eh,String em){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.availability, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinnerWeekday=(Spinner)dialogView.findViewById(R.id.spinnerWeekday);

        final EditText editTextStartTimeHour=(EditText)dialogView.findViewById(R.id.editTextStartTimeHour);
        final EditText editTextStartTimeMin=(EditText)dialogView.findViewById(R.id.editTextStartTimeMin);
        final EditText editTextEndTimeHour=(EditText)dialogView.findViewById(R.id.editTextEndTimeHour);
        final EditText editTextEndTimeMin=(EditText)dialogView.findViewById(R.id.editTextEndTimeMin);

        final Button buttonChange=(Button)dialogView.findViewById(R.id.buttonChange);
        final Button buttonCancel=(Button)dialogView.findViewById(R.id.buttonCancel);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Date,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeekday.setAdapter(adapter);
        spinnerWeekday.setOnItemSelectedListener(this);


        dialogBuilder.setTitle("Updating Plan for: "+weekday);

        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
        int x=0;
        if(weekday.equals("Monday")){
            x=0;
        }
        if(weekday.equals("Tuesday")){
            x=1;
        }
        if(weekday.equals("Wednesday")){
            x=2;
        }
        if(weekday.equals("Thursday")){
            x=3;
        }
        if(weekday.equals("Friday")){
            x=4;
        }
        if(weekday.equals("Saturday")){
            x=5;
        }
        if(weekday.equals("Sunday")){
            x=6;
        }
        spinnerWeekday.setSelection(x);
        editTextStartTimeHour.setHint("("+sh+")");
        editTextStartTimeMin.setHint("("+sm+")");
        editTextEndTimeHour.setHint("("+eh+")");
        editTextEndTimeMin.setHint("("+em+")");

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weekday=spinnerWeekday.getSelectedItem().toString().trim();
                String sh=editTextStartTimeHour.getText().toString().trim();
                String sm=editTextStartTimeMin.getText().toString().trim();
                String eh=editTextEndTimeHour.getText().toString().trim();
                String em=editTextEndTimeMin.getText().toString().trim();

                if(TextUtils.isEmpty(sh)){
                    editTextStartTimeHour.setError("You have to enter the Start time hour");
                    return;
                }
                if(TextUtils.isEmpty(sm)){
                    editTextStartTimeMin.setError("You have to enter the start time minute");
                    return;
                }
                if(TextUtils.isEmpty(eh)){
                    editTextEndTimeHour.setError("You have to enter the end time hour");
                    return;
                }
                if(TextUtils.isEmpty(em)){
                    editTextEndTimeMin.setError("You have to enter the end time minutes");
                    return;
                }
                int a=Integer.parseInt(sh);
                int aa=Integer.parseInt(sm);
                int b=Integer.parseInt(eh);
                int bb=Integer.parseInt(em);

                if(a<0||a>23){
                    editTextStartTimeHour.setError("Enter the number from 0 to 23 ");
                    return;
                }
                if(aa<0||aa>60){
                    editTextStartTimeMin.setError("Enter the number from 0 to 60 ");
                    return;
                }
                if(b<0||b>23){
                    editTextEndTimeHour.setError("Enter the number from 0 to 23 ");
                    return;
                }
                if(b<0||bb>60){
                    editTextEndTimeMin.setError("Enter the number from 0 to 60 ");
                    return;
                }
                if(a>b){
                    editTextStartTimeHour.setError("Start hour should be early than the End hour");
                    return;
                }
                if(a==b&&aa>bb){
                    editTextStartTimeMin.setError("Start time(minutes) should be early than the End time");
                    return;
                }


                upDateTime(id ,weekday,sh,sm,eh,em);
                alertDialog.dismiss();

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



    }
    private boolean upDateTime(String id, String weekday, String sh,String sm,String eh,String em){

        FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReference=FirebaseDatabase.getInstance().getReference("Service_Provider_Account").child(user.getUid()).child("time").child(id);

        ServicesTime servicesTime=new ServicesTime(id,weekday,sh,sm,eh,em);

        databaseReference.setValue(servicesTime);

        Toast.makeText(this, "Time information updated Successful",Toast.LENGTH_SHORT).show();

        return true;


    }

    private void delete(){
        FirebaseUser user=firebaseAuth.getCurrentUser();
        DatabaseReference dt=FirebaseDatabase.getInstance().getReference("Service_Provider_Account").child(user.getUid()).child("time").child(Tid);

        DatabaseReference dw=FirebaseDatabase.getInstance().getReference("Week_Plan").child(week).child(Tid);

        dt.removeValue();
        dw.removeValue();
        Toast.makeText(this, "Time has been removed Successful",Toast.LENGTH_SHORT).show();
    }


    public void onClick(View view) {
        if(view==buttonSet){
            startActivity(new Intent(this,Service_Provider_Times_Set.class));
        }
        if(view==buttonDelete){
            delete();
        }
        if(view==buttonBack){
            finish();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),type,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
