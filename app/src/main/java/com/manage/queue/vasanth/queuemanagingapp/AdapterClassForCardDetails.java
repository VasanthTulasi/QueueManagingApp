package com.manage.queue.vasanth.queuemanagingapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AdapterClassForCardDetails extends ArrayAdapter<CardClass> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    public static int positionValue;

    private static class ViewHolder {
        TextView number;
        TextView name;
    }

    public AdapterClassForCardDetails(Context context, int resource, ArrayList<CardClass> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        //sets up the image loader library

        //get the persons information
        final int number= getItem(position).getIndexNumber();

        String name = getItem(position).getNameOfPerson();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;



        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.number= (TextView) convertView.findViewById(R.id.number);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            result = convertView;

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

//        ImageView imageInCSE = (ImageView) convertView.findViewById(R.id.cardImageInCSE);
//        imageInCSE.setImageResource(R.drawable.idea_presentation_cse);


//        final Button accept = (Button)convertView.findViewById(R.id.accept);
        final Button reject = (Button)convertView.findViewById(R.id.reject);

//        accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    accept.setText("Accepted");
//                    accept.setBackgroundColor(Color.parseColor("#9BA6B2"));
//                    accept.setTextColor(Color.parseColor("#68727E"));
//                    accept.setEnabled(false);
//                  reject.setVisibility(View.GONE);
//
//
//            }});


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//          remove(getItem(position));
            DatabaseReference databaseReferenceForRemoval = FirebaseDatabase.getInstance().getReference().child(QueueCodeEntryActivity.queueCodeString);
            databaseReferenceForRemoval.child(Manage.keysArrayList.get(position)).removeValue();

            }
        });

        // Animation animation = AnimationUtils.loadAnimation(mContext,
        // (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        // result.startAnimation(animation);
        // lastPosition = position;
        positionValue = position;
        holder.number.setText(String.valueOf(position+1));
        holder.name.setText(name);

        //create the imageloader object
//        ImageLoader imageLoader = ImageLoader.getInstance();

//        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());
//
//        //create display options
//        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                .cacheOnDisc(true).resetViewBeforeLoading(true)
//                .showImageForEmptyUri(defaultImage)
//                .showImageOnFail(defaultImage)
//                .showImageOnLoading(defaultImage).build();
//
//        //download and display image from url
//        imageLoader.displayImage(imgUrl, holder.image, options);

        return convertView;


    }




}
