package com.example.legallib;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.NetworkException;
import android.os.Bundle;

import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView ftxt;
    TextView txt;
    EditText search;
    Button searchbtn;
    MyContentAdapter adapter;
    String download;
    ProgressBar loading;
    String API = "AIzaSyAu6dCxwSJ7zf3k6VoxicZSUgEYC2zJy70";
    String url = "https://www.googleapis.com/books/v1/volumes?q=Twilight&key=" + API;
    ArrayList<ContactModel> arrtitle = new ArrayList<ContactModel>();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchbtn = requireActivity().findViewById(R.id.searchbtn);
        search = requireActivity().findViewById(R.id.search);
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ftxt = view.findViewById(R.id.ftxt);
        ftxt.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new MyContentAdapter(requireContext(), arrtitle);
        ftxt.setAdapter(adapter);
        txt = view.findViewById(R.id.txt);
        loading = view.findViewById(R.id.spinner);
        arrtitle.clear();
        jsonsource();

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String added = search.getText().toString();
                if(added.isEmpty()){
                    Toast.makeText(requireContext(), "Search bar is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    url = "https://www.googleapis.com/books/v1/volumes?q=" + added + "&key=" + API;
                    jsonsource();
                }
            }
        });
        return view;
    }
    public void jsonsource() {

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        loading.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loading.setVisibility(View.GONE);
                            arrtitle.clear();
                            JSONArray items = response.getJSONArray("items");
                                for (int i = 0; i < items.length(); i++) {
                                        JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
                                        JSONObject accessinfo = items.getJSONObject(i).getJSONObject("accessInfo");
                                        JSONArray author = volumeInfo.getJSONArray("authors");
                                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                        JSONObject pdf = accessinfo.getJSONObject("pdf");
                                        Boolean availbility = pdf.getBoolean("isAvailable");
                                        String webreader = accessinfo.getString("webReaderLink");
                                        String thumbnail = imageLinks.getString("thumbnail");
                                        String name = volumeInfo.getString("title");
                                        String describe = volumeInfo.getString("publisher");
                                        String authname = author.getString(0);
                                        Log.d("ImageURL", thumbnail);
                                        if (accessinfo.has("downloadLink")) {
                                            download = accessinfo.getString("downloadLink");
                                        } else {
                                            download = "";
                                        }
                                        loadBookCover(thumbnail.replace("http://", "https://"), name, describe, authname, webreader, download, availbility);

                                }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {

                        }
                    }
                },
                     new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ftxt.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        txt.setText("No Internet\n Please Check your Connection " );
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadBookCover(String url, String name, String describe, String author,String webreader,String download,Boolean avaibility ) {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        arrtitle.add(new ContactModel(response,name,describe,author,webreader,download,avaibility));
                        adapter.notifyDataSetChanged();
                    }
                },
                0, 0, // Use original dimensions
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ImageError", error.toString());
                        Toast.makeText(requireContext(), "Error loading image!", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(imageRequest);
    }
                }