package com.example.alienware.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Service_Provider_Services extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private Button buttonAdd;
    private Button buttonDelete;
    private Button buttonBack;

    ListView listViewServices;
    ListView listViewSPServices;

    List<ServicesList> servicesLists;
    List<ServicesList> spservicesLists;

    FirebaseDatabase database;
    DatabaseReference databaseReferenceA;
    DatabaseReference databaseReferenceS;
    DatabaseReference databaseReferenceC;
    DatabaseReference databaseReferenceD;
    private DatabaseReference databaseReferenceAddRate;


    String Sid;
    String SPid;
    String Sname;
    String SPname;
    String ShourlyRate;
    String SPhourlyRate;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__provider__services);


        database=FirebaseDatabase.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();

        final FirebaseUser user=firebaseAuth.getCurrentUser();


        databaseReferenceA=database.getReference("services");
        databaseReferenceS=FirebaseDatabase.getInstance().getReference("Service_Provider_Account").child(user.getUid()).child("services");

        databaseReferenceC=FirebaseDatabase.getInstance().getReference(Sname+"services_provider_list");

        databaseReferenceD=FirebaseDatabase.getInstance().getReference("Service_Provider_Account").child(user.getUid());

        listViewServices=(ListView) findViewById(R.id.ASlist);
        servicesLists=new ArrayList<>();

        listViewSPServices=(ListView) findViewById(R.id.SPSlist);
        spservicesLists=new ArrayList<>();

        buttonAdd=(Button)findViewById(R.id.buttonAdd);
        buttonDelete=(Button)findViewById(R.id.buttonDelete);
        buttonBack=(Button)findViewById(R.id.buttonBack);


        buttonAdd.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonBack.setOnClickListener(this);






        listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicesList servicesList=servicesLists.get(position);
                Sid=servicesList.getId();
                Sname=servicesList.getName();
                ShourlyRate=servicesList.getHourlyRate();
                view.setSelected(true);
                Toast.makeText(Service_Provider_Services.this, "Selected from admin services list", Toast.LENGTH_SHORT).show();
            }
        });
//
        listViewSPServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicesList spservicesList=spservicesLists.get(position);
                SPid=spservicesList.getId();
                SPname=spservicesList.getName();
                SPhourlyRate=spservicesList.getHourlyRate();
                view.setActivated(true);
                Toast.makeText(Service_Provider_Services.this, "Selected from your proflie", Toast.LENGTH_SHORT).show();
            }
        });


    }
    protected void onStart() {
        super.onStart();

        databaseReferenceA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesLists.clear();
                for(DataSnapshot servicesSnapshot: dataSnapshot.getChildren()){
                    ServicesList servicesList=servicesSnapshot.getValue(ServicesList.class);
                    servicesLists.add(servicesList);
                }
                listservices adapter=new listservices(Service_Provider_Services.this,servicesLists);
                listViewServices.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                spservicesLists.clear();
                for(DataSnapshot spservicesSnapshot: dataSnapshot.getChildren()){
                    ServicesList spservicesList=spservicesSnapshot.getValue(ServicesList.class);
                    spservicesLists.add(spservicesList);
                }
                listservices adapter=new listservices(Service_Provider_Services.this,spservicesLists);
                listViewSPServices.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot serviceAccountSnapshot) {

                Service_Provider_Account service_provider_account=serviceAccountSnapshot.getValue(Service_Provider_Account.class);
                username=service_provider_account.getName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void add(){

        FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReferenceS=FirebaseDatabase.getInstance().getReference("Service_Provider_Account").child(user.getUid()).child("services");

        String rate="0";

        ServicesList sp=new ServicesList(Sid,Sname,ShourlyRate);

        databaseReferenceS.child(Sid).setValue(sp);

        databaseReferenceC=FirebaseDatabase.getInstance().getReference("services_with_provider_list").child(Sname);

        Service_Name service_name=new Service_Name(user.getUid(),username,Sname);

        databaseReferenceC.child(user.getUid()).setValue(service_name);




        Toast.makeText(Service_Provider_Services.this, "Save Successful", Toast.LENGTH_SHORT).show();



    }
    private void delete(){

        FirebaseUser user=firebaseAuth.getCurrentUser();

        DatabaseReference dr=FirebaseDatabase.getInstance().getReference("Service_Provider_Account").child(user.getUid()).child("services").child(SPid);

        DatabaseReference ds=FirebaseDatabase.getInstance().getReference("services_with_provider_list").child(Sname).child(user.getUid());

        dr.removeValue();
        ds.removeValue();

        Toast.makeText(this, "Services has been removed Successful",Toast.LENGTH_SHORT).show();

    }


    public void onClick(View view) {
        if(view==buttonAdd){
            add();
        }
        if(view==buttonDelete){
            delete();

        }
        if(view==buttonBack){
            finish();

        }

    }
}
