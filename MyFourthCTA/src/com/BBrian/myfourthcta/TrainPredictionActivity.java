package com.BBrian.myfourthcta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TrainPredictionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train_prediction);
		
	}
	
	class FindParentId extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
					
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			//get stop user selected
			Intent intent = getIntent();
			String line = intent.getStringExtra("line");
			line = line.replace(" Line", ""); //strip Line off
			String target = intent.getStringExtra("stopName") + "-" + line; 

			String parentId;
			String name;						
			String l;

			try {
				InputStream is = getAssets().open("stops.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				while ((l = br.readLine()) != null) {
					Scanner s = new Scanner(l).useDelimiter("\\,");
					while (s.hasNext()) {
						parentId = s.next();
						s.next();
						name = s.next();
						s.nextLine();
						name = name.replace("\"", "");						
						if (target.equalsIgnoreCase(name) || target.contains(name)) {
							new MakePrediction().execute(parentId);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
			return null;
		}	
	}

}
