package sn.soulesidibe.example.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	String pathToFile = "http://192.168.0.100:9000/personne";
	InputStream in = null;
	try {
	    URL url = new URL(pathToFile);
	    URLConnection connection = url.openConnection();
	    HttpURLConnection httpConnection = (HttpURLConnection) connection;
	    int responseCode = httpConnection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
		in = httpConnection.getInputStream();
	    }
	} catch (MalformedURLException e) {
	} catch (IOException e) {
	}

	BufferedReader bufferedReader = new BufferedReader(
		new InputStreamReader(in));
	StringBuilder stringBuilder = new StringBuilder();
	try {
	    String ligneLue = bufferedReader.readLine();
	    while (ligneLue != null) {
		stringBuilder.append(ligneLue + "\n");
		ligneLue = bufferedReader.readLine();
	    }
	    bufferedReader.close();
	} catch (IOException e2) {
	    e2.printStackTrace();
	}
	String json = stringBuilder.toString();
	ArrayList<Personne> listePersonne = new ArrayList<Personne>();
	Personne personne;
	JSONObject object;
	try {
	    object = new JSONObject(json);
	    String result = object.getString("result");
	    Toast.makeText(this, "Result:" + result, Toast.LENGTH_SHORT).show();
	    JSONArray array = object.getJSONArray("Personnes");
	    for (int i = 0; i < array.length(); i++) {
		personne = new Personne();
		personne.setNom(array.getJSONObject(i).getString("name"));
		personne.setAge(array.getJSONObject(i).getString("age"));
		personne.setAdress(array.getJSONObject(i).getString("adress"));
		Log.i("PERSONNE", personne.toString());
		Toast.makeText(this, personne.toString(), Toast.LENGTH_SHORT)
			.show();
		listePersonne.add(personne);
	    }
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

}
