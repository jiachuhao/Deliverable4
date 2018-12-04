package com.example.alienware.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Service_Provider_Profile extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceName;


    private Button buttonSubmit;
    private Button buttonBack;


    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextNameOfCompany;
    private EditText editTextPhoneNumber;
    private EditText editTextGeneralDescription;
    private CheckBox checkBoxLicensed;
    private String licensed="No";
    private TextView textViewNameOfCompany;

    Service_Provider_Account service_provider_account;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__provider__profile);

        buttonSubmit=(Button)findViewById(R.id.buttonSubmit);
        buttonBack=(Button)findViewById(R.id.buttonBack);


        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextAddress=(EditText)findViewById(R.id.editTextAddress);
        editTextPhoneNumber=(EditText)findViewById(R.id.editTextPhoneNumber);
        editTextNameOfCompany=(EditText)findViewById(R.id.editTextNameOfCompany);
        editTextGeneralDescription=(EditText)findViewById(R.id.editTextGeneralDescription);
        checkBoxLicensed=(CheckBox)findViewById(R.id.checkBoxLicensed);
        textViewNameOfCompany=(TextView)findViewById(R.id.textViewNameOfCompany);


        database=FirebaseDatabase.getInstance();

        service_provider_account = new Service_Provider_Account();
        database = FirebaseDatabase.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();



        databaseReference = database.getReference("Service_Provider_Account");

        service_provider_account=new Service_Provider_Account();

        buttonSubmit.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        checkBoxLicensed.setOnClickListener(this);

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
                        editTextName.setText(service_provider_account.getName(),TextView.BufferType.EDITABLE);
                        editTextAddress.setText(service_provider_account.getAddress(),TextView.BufferType.EDITABLE);
                        editTextPhoneNumber.setText(service_provider_account.getPhoneNumber(),TextView.BufferType.EDITABLE);
                        editTextNameOfCompany.setText(service_provider_account.getNameOfCompany(),TextView.BufferType.EDITABLE);
                        editTextGeneralDescription.setText(service_provider_account.getGeneralDescription(),TextView.BufferType.EDITABLE);
                        if(service_provider_account.getLicensed().equals("Yes")){
                            checkBoxLicensed.setChecked(true);
                        }
                        if(service_provider_account.getLicensed().equals("No")){
                            checkBoxLicensed.setChecked(false);
                        }

                        editTextName.setEnabled(false);

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void save(){

        databaseReference=FirebaseDatabase.getInstance().getReference("Service_Provider_Account");
        databaseReferenceName=FirebaseDatabase.getInstance().getReference("Provider_Rate").child(firebaseAuth.getUid());

        FirebaseUser user=firebaseAuth.getCurrentUser();

        String id=user.getUid();
        String name=editTextName.getText().toString().trim();
        String address=editTextAddress.getText().toString().trim();
        String phoneNumber=editTextPhoneNumber.getText().toString().trim();
        String nameOfCompany=editTextNameOfCompany.getText().toString().trim();
        String generalDescription=editTextGeneralDescription.getText().toString().trim();

        Service_Provider_Rate sr=new Service_Provider_Rate(name);
        Service_Provider_Account sp=new Service_Provider_Account(id,name,address,phoneNumber,nameOfCompany,generalDescription,licensed);

        databaseReference.child(user.getUid()).setValue(sp);
        databaseReferenceName.setValue(sr);
        Toast.makeText(Service_Provider_Profile.this, "Save Successful", Toast.LENGTH_SHORT).show();
    }
    private void submit(){
        String name=editTextName.getText().toString().trim();
        String address=editTextAddress.getText().toString().trim();
        String phoneNumber=editTextPhoneNumber.getText().toString().trim();
        String nameOfCompany=editTextNameOfCompany.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            editTextName.setError("You have to enter your name");
            return;
        }
        if(TextUtils.isEmpty(address)){
            editTextAddress.setError("You have to enter a address");
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            editTextPhoneNumber.setError("You have to enter your phone number");
            return;
        }
        if(TextUtils.isEmpty(nameOfCompany)){
            editTextNameOfCompany.setError("You have to enter your company's name");
            return;
        }
        save();

    }
    public void onClick(View view) {
        if(view==checkBoxLicensed){
            boolean check=((CheckBox)view).isChecked();
            if(check){
                licensed="Yes";
            }
            else{
                licensed="No";
            }

        }

        if(view==buttonSubmit){
            submit();
        }

        if(view==buttonBack){

            finish();

        }
    }
}
