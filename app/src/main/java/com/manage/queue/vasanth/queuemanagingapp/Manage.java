package com.manage.queue.vasanth.queuemanagingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Manage extends AppCompatActivity {

    DatabaseReference databaseReference;
    ListView listView;
    public static ArrayList<CardClass> membersArrayList;
    public static ArrayList<String> keysArrayList;
    ArrayList<String> namesForReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(QueueCodeEntryActivity.queueCodeString);
        listView = (ListView)findViewById(R.id.listOfMembers);
        membersArrayList = new ArrayList<>();
        keysArrayList = new ArrayList<>();
        namesForReference = new ArrayList<>();


        final AdapterClassForCardDetails arrayAdapter = new AdapterClassForCardDetails(this,R.layout.card_design, membersArrayList);
        listView.setAdapter(arrayAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//              DatabaseReference databaseReferenceForRemoval = FirebaseDatabase.getInstance().getReference().child(QueueCodeEntryActivity.queueCodeString);
//              databaseReferenceForRemoval.child(keysArrayList.get(i)).removeValue();
//
//            }
//        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String addedMember = dataSnapshot.getValue(String.class);
                membersArrayList.add(new CardClass(1,addedMember));
                namesForReference.add(addedMember);
                String addedkey = dataSnapshot.getKey();
                keysArrayList.add(addedkey);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                String removedKey = dataSnapshot.getKey();
                String removedValue = dataSnapshot.getValue(String.class);


                int index = keysArrayList.indexOf(removedKey);
//                CardClass cc  = membersArrayList.get(index);
//                membersArrayList.remove(cc);
                membersArrayList.remove(index);
                namesForReference.remove(index);
                keysArrayList.remove(removedKey);



//                membersArrayList.remove(keysArrayList.indexOf(removedKey));
//                keysArrayList.remove(removedKey);
//                namesForReference.remove(removedValue);


                arrayAdapter.notifyDataSetChanged();

//       arrayAdapter.notifyDataSetInvalidated();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
