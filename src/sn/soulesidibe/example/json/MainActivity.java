package sn.soulesidibe.example.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    TextView tv;
    ArrayList<Personne> listePersonne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	tv = (TextView) findViewById(R.id.tv);
	String serviceUrl = "http://192.168.0.100:9000/personne";
	listePersonne = (getPersonne(serviceUrl));

	setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main,
		R.id.tv, getListPersonName(listePersonne)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.activity_main, menu);
	return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

	Intent intent;
	if (item.getItemId() == R.id.menu_add_personn) {
	    intent = new Intent(this, AddPersonne.class);
	    startActivity(intent);
	}

	return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

	InputStream in = null;
	try {
	    URL url = new URL("http://192.168.0.100:9000/personne/delete/"
		    + listePersonne.get(position).getId());
	    HttpURLConnection connection = (HttpURLConnection) url
		    .openConnection();
	    connection.setDoOutput(true);
	    connection.setInstanceFollowRedirects(false);
	    connection.setRequestMethod("DELETE");
	    int responseCode = connection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
		in = connection.getInputStream();
	    }
	} catch (MalformedURLException e) {
	} catch (IOException e) {
	}

	if (in != null) {

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
	    Toast.makeText(this, json, Toast.LENGTH_SHORT).show();
	}

    }

    private ArrayList<String> getListPersonName(ArrayList<Personne> listPerson) {
	ArrayList<String> listeName = new ArrayList<String>();

	for (Personne personne : listPerson) {
	    listeName.add(personne.getNom());
	}

	return listeName;
    }

    private ArrayList<Personne> getPersonne(String serviceUrl) {
	InputStream in = null;
	try {
	    URL url = new URL(serviceUrl);
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
	    Log.i("Result", result);
	    // Toast.makeText(this, "Result:" + result,
	    // Toast.LENGTH_SHORT).show();
	    JSONArray array = object.getJSONArray("Personnes");
	    for (int i = 0; i < array.length(); i++) {
		personne = new Personne();
		personne.setNom(array.getJSONObject(i).getString("name"));
		personne.setAge(array.getJSONObject(i).getString("age"));
		personne.setAdress(array.getJSONObject(i).getString("adress"));
		personne.setId(Integer.parseInt(array.getJSONObject(i)
			.getString("id")));
		Log.i("PERSONNE", personne.toString());
		// Toast.makeText(this, personne.toString(), Toast.LENGTH_SHORT)
		// .show();
		listePersonne.add(personne);
	    }
	} catch (JSONException e) {
	    e.printStackTrace();
	}

	return listePersonne;
    }

}
