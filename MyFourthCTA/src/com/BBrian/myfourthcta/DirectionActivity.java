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

public class DirectionActivity extends ListActivity {
	private Intent intent;
	private List<String> list;
	private String route;
	private Intent getStops;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		intent = getIntent();
		route = intent.getStringExtra("route");
		String url = "http://www.ctabustracker.com/bustime/api/v1/getdirections?" + "rt="
					+ route + "&" + ConnectAPI.BusKey;
		
		new DownloadDirections().execute(url);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getStops = new Intent();
		getStops.setClass(this, StopsActivity.class);
		getStops.putExtra("route", route);
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		getStops.putExtra("direction", list.get(position));
		startActivity(getStops);
	}
	
	class DownloadDirections extends AsyncTask<String,Void,String> {

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
//			Log.v("test2", urls[0]);
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
					
					break;
				case XmlPullParser.START_TAG:
					String name = parser.getName();
//					Log.v("test1", name);
					if(name.equals("dir")) {
						
						event = parser.next();
				      	//String s = parser.getText();
						//Log.v("test", s);
						list.add(parser.getText());
//						Log.v("test1", parser.getText());		
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
		
		DirectionActivity.this.setListAdapter(new ArrayAdapter<String>(DirectionActivity.this,
				android.R.layout.simple_list_item_1,list));
		
		}
		
		
	}
}
