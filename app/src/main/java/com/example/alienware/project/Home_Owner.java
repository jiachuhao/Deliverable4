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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Home_Owner extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private Button buttonBook;
    private Button buttonCheck;
    private Button buttonLogout;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__owner);

        buttonBook=(Button)findViewById(R.id.buttonBook);
        buttonCheck=(Button)findViewById(R.id.buttonCheck);
        buttonLogout=(Button)findViewById(R.id.buttonLogout);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(this,Login.class));
        }
        final FirebaseUser user=firebaseAuth.getCurrentUser();

        buttonBook.setOnClickListener(this);
        buttonCheck.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

    }

    public void onClick(View view) {
        if(view==buttonBook){
            startActivity(new Intent(this,Home_Owner_Book_service.class));
        }
        if(view==buttonCheck){
            startActivity(new Intent(this,Home_Owner_Check_Service.class));

        }
        if(view==buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,Login.class));
        }
    }
}
