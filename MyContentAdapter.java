package com.example.legallib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContentAdapter extends RecyclerView.Adapter<MyContentAdapter.Viewholder> {
    Context context;
    ArrayList<ContactModel> arrcontacts;
    MyContentAdapter(Context context, ArrayList<ContactModel>arrcontacts){
        this.context = context;
        this.arrcontacts = arrcontacts;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.contenthere, parent,false);
        Viewholder view = new Viewholder(v);
        return view;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ContactModel contact = arrcontacts.get(position);
    holder.coverimg.setImageBitmap(contact.img);
    holder.title.setText(contact.title);
    holder.about.setText(contact.description);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent;
            intent = new Intent(context,BookDetails.class);
            intent.putExtra("title",contact.title);
            intent.putExtra("description", contact.description);
            intent.putExtra("img",contact.img );
            intent.putExtra("author", contact.author);
            intent.putExtra("webreader", contact.webreader);
            intent.putExtra(".download", contact.download);
            intent.putExtra("avaibility",contact.avaibility);
            context.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return arrcontacts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
TextView title,about,author;
ImageView coverimg;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.head);
            about = itemView.findViewById(R.id.about);
            coverimg = itemView.findViewById(R.id.coverimg);
        }
    }
}
