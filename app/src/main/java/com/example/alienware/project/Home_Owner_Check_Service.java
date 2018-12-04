package com.example.alienware.project;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home_Owner_Check_Service extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceForclose;
    private DatabaseReference databaseReferenceOrderFinished;


    private Button buttonRate;
    private Button buttonBack;

    private ListView OrderList;
    private List<Service_In_Order>service_in_orders;

    String Service_name;
    String Provider_name;
    String HomeOwner_id;
    String Provider_id;
    String HomeOnwer_name;

    String rated;

    double fr;
    double r;
    int t;
    String u;
    String n;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__owner__check__service);

        database=FirebaseDatabase.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();

        final FirebaseUser user=firebaseAuth.getCurrentUser();

        HomeOwner_id=user.getUid();
        HomeOnwer_name=user.getEmail();

        databaseReference=database.getReference("Services_Order").child(user.getUid());

        OrderList=(ListView) findViewById(R.id.OrderList);

        service_in_orders=new ArrayList<>();

        buttonRate=(Button)findViewById(R.id.buttonRate);
        buttonBack=(Button)findViewById(R.id.buttonBack);

        buttonRate.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        OrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service_In_Order service_in_order=service_in_orders.get(position);

                Service_name=service_in_order.getService_name();
                Provider_name=service_in_order.getService_provider_name();
                Provider_id=service_in_order.getService_provider_id();

                databaseReferenceForclose=database.getReference("Services_Order").child(user.getUid()).child(Service_name).child(Provider_id);


                databaseReferenceForclose.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Service_In_Order serviceInOrder=dataSnapshot.getValue(Service_In_Order.class);
                        rated=serviceInOrder.getRated();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                view.setSelected(true);

            }
        });

    }
    private void showRate(final String service_name, final String provider_name, final String provider_id,final String homeOwner_id,final String homeOnwer_name){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.provider_rate, null);
        dialogBuilder.setView(dialogView);



        final TextView textViewProviderName=(TextView)dialogView.findViewById(R.id.textViewProviderName);
        final EditText editTextProviderName=(EditText)dialogView.findViewById(R.id.editTextProviderName);
        final TextView textViewServiceName=(TextView)dialogView.findViewById(R.id.textViewServiceName);
        final EditText editTextServiceName=(EditText)dialogView.findViewById(R.id.editTextServiceName);
        final TextView textViewRate=(TextView)dialogView.findViewById(R.id.textViewRate);
        final RatingBar ratingBar=(RatingBar)dialogView.findViewById(R.id.ratingBar);
        final TextView textViewComment=(TextView)dialogView.findViewById(R.id.textViewComment);
        final EditText editTextComment=(EditText)dialogView.findViewById(R.id.editTextComment);
        final Button buttonSubmit=(Button)dialogView.findViewById(R.id.buttonSubmit);
        final Button buttonCancel=(Button)dialogView.findViewById(R.id.buttonCancel);


        dialogBuilder.setTitle("Rating System");


        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        editTextProviderName.setText(provider_name);
        editTextServiceName.setHint(service_name);

        DatabaseReference databaseReferenceforate=FirebaseDatabase.getInstance().getReference("Provider_Rate").child(provider_id);

        databaseReferenceforate.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot rateSnapshot) {

                Service_Provider_Rate service_provider_rate=rateSnapshot.getValue(Service_Provider_Rate.class);

                fr=service_provider_rate.getFinalrate();
                r=service_provider_rate.getRate();
                t=service_provider_rate.getTimes();
                u=service_provider_rate.getId();
                n=service_provider_rate.getName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double rate=ratingBar.getRating();
                String pid=provider_id;
                String pname=provider_name;
                String sname=service_name;
                String hid=homeOwner_id;
                String hname=homeOnwer_name;
                String comments=editTextComment.getText().toString().trim();

                double tr=r+rate;
                int tt=t+1;
                double tfr=tr/tt;


                close(hid,hname,sname,pid,pname,rate,comments);
                rating(tfr,tr,tt,pid);

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
    private void close(String hid, String hname, String sname,String pid,String pname,double prate,String comments){

        final DatabaseReference databaseReferenceforend=FirebaseDatabase.getInstance().getReference("Services_Order").child(hid).child(sname).child(pid);

        databaseReferenceOrderFinished=FirebaseDatabase.getInstance().getReference("Services_Order_History");
        String id=databaseReferenceOrderFinished.push().getKey();

        Service_In_Order serviceInOrder=new Service_In_Order(sname,hid,hname,pid,pname,"Yes");
        Service_Order_History service_order_history=new Service_Order_History(sname,hid,hname,pid,pname,prate,comments);

        databaseReferenceforend.setValue(serviceInOrder);
        databaseReferenceOrderFinished.child(id).setValue(service_order_history);



    }
    private void rating(double frs,double rs,int t,String provider_id){

        DatabaseReference databaseReferenceforate=FirebaseDatabase.getInstance().getReference("Provider_Rate").child(provider_id);

        Service_Provider_Rate service_provider_rate=new Service_Provider_Rate(frs,rs,t,u,n);

        databaseReferenceforate.setValue(service_provider_rate);


        Toast.makeText(this, "Rating successful",Toast.LENGTH_SHORT).show();




    }

    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                service_in_orders.clear();
                for(DataSnapshot orderSnapshot: dataSnapshot.getChildren()){

                    for(DataSnapshot SorderSnapshot: orderSnapshot.getChildren()){


                        Service_In_Order serviceInOrder=SorderSnapshot.getValue(Service_In_Order.class);
                        service_in_orders.add(serviceInOrder);
                    }

                }
                ServiceOrderCheck adapter=new ServiceOrderCheck(Home_Owner_Check_Service.this,service_in_orders);
                OrderList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    public void onClick(View view) {
        if(view==buttonRate){

            if(rated.equals("No")){
                showRate(Service_name,Provider_name,Provider_id,HomeOwner_id,HomeOnwer_name);
            }
            else{
                Toast.makeText(this, "You have already rated this service",Toast.LENGTH_SHORT).show();
            }



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
