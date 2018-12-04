package com.example.alienware.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class Home_Owner_Book_service extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceForAll;
    private DatabaseReference databaseReferenceForAllselect;
    private DatabaseReference databaseReferenceForRate;
    private DatabaseReference databaseReferenceForRateToName;
    private DatabaseReference databaseReferenceForPTime;
    private DatabaseReference databaseReferenceForSaveService;

    private Button buttonBook;
    private Button buttonBack;
    private Button buttonSearch;


    private Spinner spinnerType;
    private Spinner spinnerTime;
    private Spinner spinnerRate;
    private Spinner spinnerHour;

    private List<String> type=new ArrayList<String>();

    String hotype;
    String hotime;
    String hohour;
    String provider_name;

    String horate;
    double thorate;
    double phorate;
    String theid;
    String thename;

    String tname;
    String tid;

    ListView listViewOwnerServices;
    List<Service_Name>ServiceName;
    List<Service_Name>ServiceNameTwo;


    List<String>ServiceNameForRemove;
    List<String>ServiceNameForKeep;
    List<String>ServiceNameForRate;

    List<String>hoid;

    boolean checks;
    boolean blcokertime;


    Service_Name service_name;
    ServiceWeek serviceWeek;
    Service_In_Order service_in_order;
    Service_Provider_Rate service_provider_rate;
    Service_Provider_Account service_provider_account;

    String service_name_selected;
    String provider_name_selected;
    String provider_id_selected;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__owner__book_service);

        type.add("All");

        database=FirebaseDatabase.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();

        final FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReferenceForAll=database.getReference("services");

        listViewOwnerServices=(ListView) findViewById(R.id.Home_Owner_List);

        ServiceName=new ArrayList<>();
        ServiceNameTwo=new ArrayList<>();
        ServiceNameForRemove=new ArrayList<>();
        ServiceNameForKeep=new ArrayList<>();
        ServiceNameForRate=new ArrayList<>();

        hoid=new ArrayList<>();




        buttonBook=(Button)findViewById(R.id.buttonBook);
        buttonBack=(Button)findViewById(R.id.buttonBack);
        buttonSearch=(Button)findViewById(R.id.buttonSearch);

        spinnerType=(Spinner)findViewById(R.id.spinnerType);
        spinnerTime=(Spinner)findViewById(R.id.spinnerTime);
        spinnerRate=(Spinner)findViewById(R.id.spinnerRate);
        spinnerHour=(Spinner)findViewById(R.id.spinnerHour);


        ArrayAdapter<String> adaptertype=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,type);

        //ArrayAdapter<CharSequence>adaptertype=ArrayAdapter.createFromResource(this,R.array.Home_Owner_Services,android.R.layout.simple_spinner_item);
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adaptertype);
        spinnerType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence>adaptertime=ArrayAdapter.createFromResource(this,R.array.Home_Owner_Time,android.R.layout.simple_spinner_item);
        adaptertime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adaptertime);
        spinnerTime.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence>adapterrate=ArrayAdapter.createFromResource(this,R.array.Home_Owner_Rate,android.R.layout.simple_spinner_item);
        adapterrate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRate.setAdapter(adapterrate);
        spinnerRate.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence>adapterhour=ArrayAdapter.createFromResource(this,R.array.Home_Owner_Hour,android.R.layout.simple_spinner_item);
        adapterrate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(adapterhour);
        spinnerHour.setOnItemSelectedListener(this);



        buttonBook.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);






        listViewOwnerServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service_Name service_name=ServiceName.get(position);
                service_name_selected=service_name.getService();
                provider_name_selected=service_name.getName();
                provider_id_selected=service_name.getId();

                view.setSelected(true);

            }
        });


    }
    private void book(){

        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReferenceForSaveService=database.getReference("Services_Order").child(user.getUid()).child(service_name_selected);

        service_in_order =new Service_In_Order(service_name_selected,user.getUid(),user.getEmail(),provider_id_selected,provider_name_selected,"No");

        databaseReferenceForSaveService.child(provider_id_selected).setValue(service_in_order);

        Toast.makeText(Home_Owner_Book_service.this, "Updated", Toast.LENGTH_SHORT).show();




    }
    private void search(){
        hotype=spinnerType.getSelectedItem().toString().trim();
        hotime=spinnerTime.getSelectedItem().toString().trim();
        hohour=spinnerHour.getSelectedItem().toString().trim();

        horate=spinnerRate.getSelectedItem().toString().trim();

        if(horate.equals("All")){
            thorate=0;
        }
        else if(horate.equals("1")){
            thorate=1;
        }
        else if(horate.equals("2")){
            thorate=2;
        }
        else if(horate.equals("3")){
            thorate=3;
        }
        else if(horate.equals("4")){
            thorate=4;
        }
        else if(horate.equals("5")){
            thorate=5;
        }



        if(!horate.equals("All")){
            databaseReferenceForRate=database.getReference("Provider_Rate");

            service_provider_rate =new Service_Provider_Rate();

            databaseReferenceForRate.addValueEventListener(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    //ServiceNameForKeep.clear();
                    for(DataSnapshot firstSnapshot: dataSnapshot.getChildren()) {


                        service_provider_rate =firstSnapshot.getValue(Service_Provider_Rate.class);
                        phorate=service_provider_rate.getFinalrate();

                        if(phorate>=thorate){
                            ServiceNameForRate.add(service_provider_rate.getName());
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }


        if(!hotime.equals("All")){
            databaseReferenceForPTime=database.getReference("Week_Plan").child(hotime);
            serviceWeek =new ServiceWeek();
            databaseReferenceForPTime.addValueEventListener(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    //ServiceNameForKeep.clear();
                    for(DataSnapshot firstSnapshot: dataSnapshot.getChildren()) {

                        serviceWeek =firstSnapshot.getValue(ServiceWeek.class);
                        ServiceNameForKeep.add(serviceWeek.getName());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }

        service_name=new Service_Name();

        if(hotype.equals("All")){
            databaseReferenceForAllselect=database.getReference("services_with_provider_list");

            databaseReferenceForAllselect.addValueEventListener(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    //ServiceName.clear();
                    for(DataSnapshot firstSnapshot: dataSnapshot.getChildren()) {

                        for(DataSnapshot secondSnapshot: firstSnapshot.getChildren()) {
                            Service_Name service_name=secondSnapshot.getValue(Service_Name.class);

                            if(!hotime.equals("All")){
                                if(ServiceNameForKeep.contains(service_name.getName())) {

                                    if(thorate!=0){

                                        if(ServiceNameForRate.contains(service_name.getName())){
                                            ServiceName.add(service_name);
                                        }
                                    }

                                    else{
                                        ServiceName.add(service_name);
                                    }




                                    }
                            }
                            else{
                                if(thorate!=0){

                                    if(ServiceNameForRate.contains(service_name.getName())){

                                        ServiceName.add(service_name);
                                    }
                                }
                                else{

                                    ServiceName.add(service_name);
                                }

                                }
                            }

                        }

                        ServiceNameList serviceNameListAdapter=new ServiceNameList(Home_Owner_Book_service.this,ServiceName);
                        listViewOwnerServices.setAdapter(serviceNameListAdapter);


                    }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        if(!hotype.equals("All")){
            databaseReferenceForAllselect=database.getReference("services_with_provider_list").child(hotype);

            databaseReferenceForAllselect.addValueEventListener(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    //ServiceName.clear();
                    for(DataSnapshot firstSnapshot: dataSnapshot.getChildren()) {

                        Service_Name service_name=firstSnapshot.getValue(Service_Name.class);

                        if(!hotime.equals("All")){
                            if(ServiceNameForKeep.contains(service_name.getName())) {

                                if(!horate.equals("All")){

                                    if(ServiceNameForRate.contains(service_name.getName())){
                                        ServiceName.add(service_name);
                                    }
                                }

                                else{
                                    ServiceName.add(service_name);
                                }




                            }
                        }
                        else{
                            if(!horate.equals("All")){

                                if(ServiceNameForRate.contains(service_name.getName())){

                                    ServiceName.add(service_name); }
                            }
                            else{

                                ServiceName.add(service_name);
                            }

                        }

                    }


                    ServiceNameList serviceNameListAdapter=new ServiceNameList(Home_Owner_Book_service.this,ServiceName);
                    listViewOwnerServices.setAdapter(serviceNameListAdapter);



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        ServiceName.clear();
        ServiceNameForRate.clear();
        ServiceNameForKeep.clear();



    }

    protected void onStart() {
        super.onStart();

        databaseReferenceForAll.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot servicesSnapshot: dataSnapshot.getChildren()){
                    ServicesList servicesList=servicesSnapshot.getValue(ServicesList.class);
                    type.add(servicesList.getName());

                }
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });





    }

    public void onClick(View view) {
        if(view==buttonBook){
            book();
        }
        if(view==buttonSearch){
            search();
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
