package com.example.alienware.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Service_Provider extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private Button buttonProfile;
    private Button buttonServices;
    private Button buttonTimes;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    Service_Provider_Account service_provider_account;

    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__provider);

        buttonProfile=(Button)findViewById(R.id.buttonProfile);
        buttonServices=(Button)findViewById(R.id.buttonServices);
        buttonTimes=(Button)findViewById(R.id.buttonTimes);
        buttonLogout=(Button)findViewById(R.id.buttonLogout);

        buttonProfile.setOnClickListener(this);
        buttonServices.setOnClickListener(this);
        buttonTimes.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        buttonServices.setEnabled(false);
        buttonTimes.setEnabled(false);

        textViewName=(TextView)findViewById(R.id.textViewName);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(this,Login.class));
        }
        final FirebaseUser user=firebaseAuth.getCurrentUser();

        textViewName.setText("Dear User: "+user.getEmail());

        database=FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Service_Provider_Account");

    }
    protected void onStart() {
        super.onStart();

        final FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                    service_provider_account = accountSnapshot.getValue(Service_Provider_Account.class);
                    if (service_provider_account.getId().equals(user.getUid())) {

                        buttonServices.setEnabled(true);
                        buttonTimes.setEnabled(true);

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void onClick(View view) {
        if(view==buttonProfile){
            startActivity(new Intent(this,Service_Provider_Profile.class));
        }
        if(view==buttonServices){
            startActivity(new Intent(this,Service_Provider_Services.class));

        }
        if(view==buttonTimes){
            startActivity(new Intent(this,Service_Provider_Times.class));

        }
        if(view==buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,Login.class));

        }
    }
}
