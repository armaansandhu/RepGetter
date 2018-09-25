package com.armaan.repgetter;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    static URL createUrl(String user) throws MalformedURLException {
        /*Uri.Builder builder =  new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("api.github.com")
                .appendPath("users/armaansandhu/repos");
        String urlString = builder.build().toString();*/
        String urlString = "https://api.github.com/users/" + user + "/repos";
        URL url = new URL(urlString);
        return url;
    }

    static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null)
            return jsonResponse;

        HttpURLConnection connection = null;
        InputStream stream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            if (connection.getResponseCode() == 200){
                stream = connection.getInputStream();
                jsonResponse = readFromInputStream(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            if(stream != null)
                stream.close();
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder output = new StringBuilder();

        String line = reader.readLine();
        while (line != null){
            output.append(line);
            line = reader.readLine();
        }
        return output.toString();
    }

    static List<Repo> parseJson(String jsonResponse) throws JSONException {
        String username, avatar, url, description; username=avatar=url=description="";
        ArrayList<Repo> Repos = new ArrayList<>();

        JSONArray response = new JSONArray(jsonResponse);
        for (int i=0;i<response.length();i++){
            JSONObject currentRepo = response.getJSONObject(i);
            username = currentRepo.getString("name");
            url = currentRepo.getString("html_url");
            description = currentRepo.getString("description");
            if(currentRepo.getString("description") == null ){
                description = "NA";
            } else {
                description = currentRepo.getString("description");
            }
             JSONObject owner = currentRepo.getJSONObject("owner");
            avatar = owner.getString("avatar_url");
            Repos.add(new Repo(username, avatar, url, description));
        }
        return Repos;
    }
}
