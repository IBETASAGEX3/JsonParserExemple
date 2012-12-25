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

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPersonne extends Activity {

    EditText name;
    EditText age;
    EditText adress;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add_personn);

	name = (EditText) findViewById(R.id.edt_nom);
	adress = (EditText) findViewById(R.id.edt_adress);
	age = (EditText) findViewById(R.id.edt_age);
	save = (Button) findViewById(R.id.btn_valider);

	save.setOnClickListener(saveListener);

    }

    OnClickListener saveListener = new OnClickListener() {

	@Override
	public void onClick(View v) {
	    String name = AddPersonne.this.name.getText().toString();
	    String adress = AddPersonne.this.adress.getText().toString();
	    String age = AddPersonne.this.age.getText().toString();

	    InputStream in = null;
	    try {
		URL url = new URL("http://192.168.0.100:9000/personne/add");
		HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
			"application/json");
		JSONObject personne = new JSONObject();
		personne.put("name", name);
		personne.put("age", age);
		personne.put("adress", adress);
		OutputStream os = connection.getOutputStream();
		os.write(personne.toString().getBytes());
		os.flush();
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
		    in = connection.getInputStream();
		} else
		    Toast.makeText(AddPersonne.this, "" + responseCode,
			    Toast.LENGTH_SHORT).show();
	    } catch (MalformedURLException e) {
	    } catch (IOException e) {
	    } catch (JSONException e) {
		e.printStackTrace();
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

		Toast.makeText(AddPersonne.this, json, Toast.LENGTH_SHORT)
			.show();
	    } else {
		Toast.makeText(AddPersonne.this, "Inputstream null",
			Toast.LENGTH_SHORT).show();
	    }

	}
    };
}
