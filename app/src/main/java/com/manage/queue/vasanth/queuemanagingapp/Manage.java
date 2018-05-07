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
    ArrayList<CardClass> membersArrayList;
    public static ArrayList<String> keysArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Queue Members");
        listView = (ListView)findViewById(R.id.listOfMembers);
        membersArrayList = new ArrayList<>();
        keysArrayList = new ArrayList<>();


        final AdapterClassForCardDetails arrayAdapter = new AdapterClassForCardDetails(this,R.layout.card_design, membersArrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              DatabaseReference databaseReferenceForRemoval = FirebaseDatabase.getInstance().getReference().child("Queue Members");
              databaseReferenceForRemoval.child(keysArrayList.get(i)).removeValue();

            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String addedMember = dataSnapshot.getValue(String.class);
                membersArrayList.add(new CardClass(1,addedMember));
                String addedkey = dataSnapshot.getKey();
                keysArrayList.add(addedkey);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String changedMember = dataSnapshot.getValue(String.class);
                String changedKey = dataSnapshot.getKey();

                int index = keysArrayList.indexOf(changedKey);

                membersArrayList.set(index,new CardClass(1,changedMember));

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                String removedKey = dataSnapshot.getKey();

                int index = keysArrayList.indexOf(removedKey);
                keysArrayList.remove(removedKey);
                membersArrayList.remove(index);

                arrayAdapter.notifyDataSetInvalidated();
                arrayAdapter.notifyDataSetChanged();

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
