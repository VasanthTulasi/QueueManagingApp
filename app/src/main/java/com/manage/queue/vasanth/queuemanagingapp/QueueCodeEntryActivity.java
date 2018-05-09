package com.manage.queue.vasanth.queuemanagingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QueueCodeEntryActivity extends AppCompatActivity {
    static String queueCodeString;
    EditText queueCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_code_entry);
        queueCode = (EditText)findViewById(R.id.queueCode);
    }
    public void createQueue(View v){
        queueCodeString = queueCode.getText().toString();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Queue Codes");
        databaseReference.push().setValue(queueCodeString);

        startActivity(new Intent(QueueCodeEntryActivity.this,Manage.class));
    }
    public void open(View v){
        queueCodeString = queueCode.getText().toString();
        startActivity(new Intent(QueueCodeEntryActivity.this,Manage.class));
    }

}
