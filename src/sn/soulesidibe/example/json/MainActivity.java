package sn.soulesidibe.example.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	String json = "{\"personnes\":[{\"nom\":\"Fama\","
		+ "\"prenom\":\"Mbaye\",\"age\":\"19\","
		+ "\"genre\":\"feminin\"},{\"nom\":\"Jack\","
		+ "\"prenom\":\"Anthoine\",\"age\":\"30\",\"genre\":\"masculin\"}]}";
	ArrayList<Personne> listePersonne = new ArrayList<Personne>();
	Personne personne;
	JSONObject object;
	try {
	    object = new JSONObject(json);

	    JSONArray array = object.getJSONArray("personnes");
	    for (int i = 0; i < array.length(); i++) {
		personne = new Personne();
		personne.setNom(array.getJSONObject(i).getString("nom"));
		personne.setPrenom(array.getJSONObject(i).getString("prenom"));
		personne.setAge(array.getJSONObject(i).getString("age"));
		personne.setGenre(array.getJSONObject(i).getString("genre"));

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
