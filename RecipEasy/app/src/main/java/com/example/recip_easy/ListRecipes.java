package com.example.recip_easy;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ListRecipes extends AppCompatActivity{
    ListView recipeOptions;
    ArrayList<String> recipes =  new ArrayList<>();
    Context context;
    String key = "8ad6e9fe49b04bf9b77792e7a305833e";
    public static String name = "";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        recipeOptions = findViewById(R.id.listView);

        context = this;

        name = getIntent().getStringExtra("Ingredient");

        new AsyncThread().execute(name);

    }
    public class AsyncThread extends AsyncTask<String, Void, Void> {
        String allData = "";
        URL url;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                url = new URL("https://api.spoonacular.com/recipes/findByIngredients?apiKey=" + key + "&ingredients=" + name+"&number=5");
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                allData = input.readLine();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                recipes.clear();
                JSONArray data = new JSONArray(allData);
                for (int i = 0; i < data.length(); i++) {
                    JSONObject individual = data.getJSONObject(i);
                    String t = individual.get("title").toString();
                    Log.d("hi",t);
                    recipes.add(t);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, android.R.id.text1, recipes);
            recipeOptions.setAdapter(adapter);
        }
    }
}
