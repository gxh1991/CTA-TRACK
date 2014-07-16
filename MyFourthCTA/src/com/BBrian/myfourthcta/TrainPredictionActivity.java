package com.BBrian.myfourthcta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.BBrian.getapi.ConnectAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TrainPredictionActivity extends Activity {
	List<Map<String,String>> list;
	Map<String,String> map;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train_prediction);
		lv = (ListView)findViewById(R.id.tpListview1);
		new FindParentId().execute();
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
			Log.v("Line", line);
			line = line.replace(" Line", ""); //strip Line off
			String target = intent.getStringExtra("stpnm") + "-" + line; 

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
	
	class MakePrediction extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			String parentId = params[0];
			String key = "?key=0372ce7eb8b041b3b0291b1c74ce1791";
			String arrivals = "http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx";
			String id = "&mapid=" + parentId;
			String call = arrivals + key + id;
			
						
			return ConnectAPI.loadXmlFromNetwork(call);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {	
				Reader r = new StringReader(result);
				BufferedReader br = new BufferedReader(r);
			
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(br);
				int event = parser.getEventType();
				
				while(event != XmlPullParser.END_DOCUMENT) {
					
					switch(event) {
					case XmlPullParser.START_DOCUMENT:
						
						list= new ArrayList<Map<String,String>>();
						break;
					case XmlPullParser.START_TAG:
						String name = parser.getName();
						if(name.equals("stpDe")) {
							
							event = parser.next();
							map = new HashMap<String,String>();
							map.put("stpDe", parser.getText());
							
						} else if(name.equals("destNm")) {
							event = parser.next();
							String destNm = parser.getText();
							map.put("destNm", destNm);
						} else if(name.equals("arrT")) {
							event = parser.next();
							Timestamp currentTS = new Timestamp(System.currentTimeMillis());
							Date date1 = new Date(currentTS.getTime());

							//get cta string timestamp and format it correctly
							String prediction = parser.getText();
							prediction = prediction.substring(0, 4) + "-" + prediction.substring(4, prediction.length());
							prediction = prediction.substring(0, 7) + "-" + prediction.substring(7, prediction.length());

							//convert cta string timestamp into timestamp and convert to date object
							Timestamp predictedTS = Timestamp.valueOf(prediction);
							Date date2 = new Date(predictedTS.getTime());

							//get difference in minutes between the two date objects
							long time = getDateDiff(date1, date2, TimeUnit.MINUTES);

							String timeDiffString = time <= 1 ? "Approaching" : String.valueOf(time) + " minutes";
							map.put("arrT", timeDiffString);
							list.add(map);
						}
					}
					event = parser.next();
				}
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setListView1();
		}
		private long getDateDiff(Date date1,Date date2,TimeUnit timeUnit) {
			long diffInMills = Math.abs(date2.getTime() - date1.getTime());
			return timeUnit.convert(diffInMills, TimeUnit.MILLISECONDS);
		}
		
		private void setListView1() {
					
			
			lv.setAdapter(new SimpleAdapter(TrainPredictionActivity.this,list,R.layout.tp_listview,
					new String[] {"stpDe","destNm","arrT"},new int[]{R.id.TptextView1,R.id.TptextView2,R.id.TptextView3}));
		}
		

		
	}

}
