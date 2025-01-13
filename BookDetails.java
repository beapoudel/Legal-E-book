package com.example.legallib;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class BookDetails extends AppCompatActivity {
 TextView title,author,publish,pdftext;
 ImageView cover;
 Button web,download;
 Toolbar toolbar;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pdftext = findViewById(R.id.pdftext);
        author = findViewById(R.id.details_author);
        web = findViewById(R.id.web);
        download = findViewById(R.id.download);
        publish = findViewById(R.id.details_pub);
        title = findViewById(R.id.details_id);
        cover = findViewById(R.id.details_cover);
       Intent intent = getIntent();
       String Title = intent.getStringExtra("title");
       String Publish = intent.getStringExtra("description");
       String Author = intent.getStringExtra("author");
       Bitmap Cover = intent.getParcelableExtra("img");
       String Webreader = intent.getStringExtra("webreader");
       String Download = intent.getStringExtra("download");
       Boolean Avaibility = intent.getBooleanExtra("avaibility", false);
       title.setText(Title);
       publish.setText(Publish);
       cover.setImageBitmap(Cover);
       author.setText(Author);
       setSupportActionBar(toolbar);
       if (Avaibility && Download!=null){
           download.setVisibility(View.VISIBLE);
           download.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   OpenDownloadLink(Download);
               }
           });
       }
       else if (Avaibility){
           pdftext.setText("PDF is available");
       }


           web.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   WebsiteReader(Webreader);
               }
           });
    }
    public void OpenDownloadLink(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    public void WebsiteReader(String weburl){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(weburl));
        startActivity(intent);
    }

}