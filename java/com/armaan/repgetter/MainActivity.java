package com.armaan.repgetter;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RepoAdapter adapter;
    private static int LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);
        adapter = new RepoAdapter(this);
        listView.setAdapter(adapter);

        final EditText search = (EditText) findViewById(R.id.search);
        Button searchButton = (Button) findViewById(R.id.search_button);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = search.getText().toString();
                RepoAsyncTask task = new RepoAsyncTask();
                task.execute(user);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = repo.getUrl();
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    class RepoAsyncTask extends AsyncTask<String, Void, List<Repo>>{

        @Override
        protected List<Repo> doInBackground(String... strings) {
            String jsonResponse = "";
            try {
                jsonResponse = QueryUtils.makeHttpRequest(QueryUtils.createUrl(strings[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }


            List<Repo> Repos = null;

            try {
                Repos = QueryUtils.parseJson(jsonResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return Repos;
        }

        @Override
        protected void onPostExecute(List<Repo> repos) {
            adapter.clear();
            adapter.addAll(repos);
        }
    }
}
