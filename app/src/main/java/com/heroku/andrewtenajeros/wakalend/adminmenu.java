package com.heroku.andrewtenajeros.wakalend;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adminmenu extends AppCompatActivity {

    DatabaseReference Clientdb;
    public ListView listViewClient;
    List<Client> clientList;
    TextView textcashonhand;
    DatabaseReference CashDB;
    DatabaseReference Userdb;
    public static final String CLIENT_ID = "";
    private static Double cash;
    public static String currentuserid;
    private static Double cash1;
    private Double mycollection;

    //    private static final String CLIENT_COLLECTOR = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Clientdb = FirebaseDatabase.getInstance().getReference("Client");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listViewClient = (ListView) findViewById(R.id.ListViewCustomer);
        clientList = new ArrayList<>();
        currentuserid = getIntent().getStringExtra(LoginActivity.USER_ID);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.NewclientButton);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NewclientDialog dialog = new NewclientDialog();
//                dialog.show(getFragmentManager(),null);
//            }
//        });

        listViewClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Client client = clientList.get(i);
                Intent intent = new Intent(getApplicationContext(), ClientProfileActivity.class );
                intent.putExtra(CLIENT_ID,client.getId());
                startActivity(intent);
            }
        });

        textcashonhand =(TextView) findViewById(R.id.cashvalue);
        CashDB = FirebaseDatabase.getInstance().getReference("cashonhand");
//        CashDB = new FirebaseDatabase("https://wakalend.firebaseio.com/cashonhand");
        CashDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                    String value = dataSnapshot.getValue(String.class);
//                Toast.makeText(LoginActivity.this, "datasnapschot: "+, Toast.LENGTH_SHORT).show();
                cash = Double.parseDouble(dataSnapshot.getValue().toString());
//                textcashonhand.setText(cash.toString()); //DAILY COLLECTION TEXT

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Userdb = FirebaseDatabase.getInstance().getReference("users").child(currentuserid).child("profile").child("dailycollection");
        Userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mycollection = Double.parseDouble(dataSnapshot.getValue().toString());
                Toast.makeText(adminmenu.this, "mycollection: "+mycollection, Toast.LENGTH_SHORT).show();
                textcashonhand.setText(mycollection.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Button buttontransfer =(Button)findViewById(R.id.buttonTransfer);
        buttontransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Userdb.setValue("0");
                Double newcash = mycollection + cash;
                CashDB.setValue(newcash.toString());
            }
        });

    }
    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onStart() {
        super.onStart();
//        String id= getIntent().getStringExtra(ClientProfileActivity.CID);
//        Toast.makeText(this, "ID: "+id , Toast.LENGTH_SHORT).show();
//        if(id!=null){
//            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Client").child(id);
//            databaseReference.removeValue();
//        }
        Clientdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clientList.clear();

                for(DataSnapshot clientSnapshot: dataSnapshot.getChildren()){
                    Client client = clientSnapshot.getValue(Client.class);
                    clientList.add(client);
                }

                customerlist adapter = new customerlist(adminmenu.this, clientList);
                listViewClient.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static class NewclientDialog extends DialogFragment {
        DatabaseReference Clientdb;

        @Override



        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout

            Clientdb = FirebaseDatabase.getInstance().getReference("Client");

            builder.setView(inflater.inflate(R.layout.newclient_dialog, null))
                    // Add action buttons
                    .setPositiveButton(R.string.action_create, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            EditText firstnameField = (EditText)((AlertDialog)dialog).findViewById(R.id.firstname);
                            EditText lastnameField = (EditText)((AlertDialog)dialog).findViewById(R.id.lastname);
                            EditText BarangayField = (EditText)((AlertDialog)dialog).findViewById(R.id.barangay);
                            EditText DistrictField = (EditText)((AlertDialog)dialog).findViewById(R.id.district);
                            EditText LoanField = (EditText)((AlertDialog)dialog).findViewById(R.id.loan);
                            EditText DaysField = (EditText)((AlertDialog)dialog).findViewById(R.id.days);

                            String ClientID = Clientdb.push().getKey();

                            String firstname = firstnameField.getText().toString();
                            String lastname = lastnameField.getText().toString();
                            String barangay = BarangayField.getText().toString();
                            String district = DistrictField.getText().toString();
                            String loan = LoanField.getText().toString();
                            String days = DaysField.getText().toString();

                            if(TextUtils.isEmpty(firstname)){
                                firstnameField.setError("First Name is required");
                            }
                            if(TextUtils.isEmpty(lastname)){
                                lastnameField.setError("Last Name is required");
                            }
                            if(TextUtils.isEmpty(barangay)){
                                BarangayField.setError("Address is required");
                            }
                            if(TextUtils.isEmpty(district)){
                                DistrictField.setError("Contact number is required");
                            }
                            if(TextUtils.isEmpty(loan)){
                                LoanField.setError("Loan amount is required");
                            }
                            if(TextUtils.isEmpty(days)){
                                DaysField.setError("Days to pay is required");
                            }


                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                             cash1= cash - Double.parseDouble(loan);
                            Client aClient = new Client(ClientID,firstname,lastname,barangay,district,loan,days);
                            Debt debt = new Debt(loan, days);
                            Clientdb.child(ClientID).setValue(aClient);
                            DatabaseReference CashDB = FirebaseDatabase.getInstance().getReference("cashonhand");
                            CashDB.setValue(cash1);
                        }
                    });


            return builder.create();
        }
    }
}
