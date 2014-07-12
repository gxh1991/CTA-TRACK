package com.BBrian.myfourthcta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.BBrian.getapi.ConnectAPI;

public class StopsActivity extends ListActivity {
	private Intent intent;
	private List<String> list;
	private String route;
	private String direction;
	private List<String> slist;
	private String stpid;
	private Intent prediction;
	private String stpnm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();
		route = intent.getStringExtra("route");
		direction = intent.getStringExtra("direction");
		String url = "http://www.ctabustracker.com/bustime/api/v1/getstops?" + "rt=" + route + "&dir=" 
				+ direction + "&" + ConnectAPI.BusKey;
//		Log.v("test", url);
		prediction = new Intent();
		prediction.setClass(this, PredictionActivity.class);
		
		new DownloadStops().execute(url);
		prediction.putExtra("route", route);
		prediction.putExtra("direction", direction);
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		stpid = slist.get(position);
		stpnm = list.get(position);
		prediction.putExtra("stpid",stpid);
		prediction.putExtra("stpnm", stpnm);
		startActivity(prediction);
		
	}
	
	class DownloadStops extends AsyncTask<String,Void,String> {

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			return  ConnectAPI.loadXmlFromNetwork(urls[0]);
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
					list = new ArrayList<String>();
					slist = new ArrayList<String>();
					break;
				case XmlPullParser.START_TAG:
					String name = parser.getName();
					//Log.v("test1", name);
					if(name.equals("stpnm")) {
						
						event = parser.next();
				      	//String s = parser.getText();
						//Log.v("test", s);
						list.add(parser.getText());
//						Log.v("test1", parser.getText());		
					} else if(name.equals("stpid")) {
						event = parser.next();
						slist.add(parser.getText());
					}
					break;
				
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
		
		StopsActivity.this.setListAdapter(new ArrayAdapter<String>(StopsActivity.this,
				android.R.layout.simple_list_item_1,list));

		}
		
	
	
	
	}	
	
	
	
	
	
	
	
	
}
