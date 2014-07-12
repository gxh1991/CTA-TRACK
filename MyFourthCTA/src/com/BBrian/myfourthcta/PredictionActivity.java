package com.BBrian.myfourthcta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.BBrian.getapi.ConnectAPI;

public class PredictionActivity extends Activity {
	private ListView listView1;
	private ListView listView2;
	private Intent intent;
	private String route;
	private String stpid;
	private List<String> list;
	private String stpnm;
	private List<Map<String,String>> nlist;
	Map<String,String> smap;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prediction_activity);
		listView1 = (ListView)findViewById(R.id.MyListView1);
		listView2 = (ListView)findViewById(R.id.MyListView2);
		intent = getIntent();
		route = intent.getStringExtra("route");
		stpid = intent.getStringExtra("stpid");
		stpnm = intent.getStringExtra("stpnm");
		Log.v("stpnm", stpnm);
		String url = "http://www.ctabustracker.com/bustime/api/v1/getpredictions?" + "rt=" + route +
				"&stpid=" + stpid + "&" + ConnectAPI.BusKey;
		new DownloadPrediction().execute(url);
	}
	private void setListView1() {
		Map<String,String> map = new HashMap<String,String>();
		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		map.put("route",route);
		map.put("stpnm", stpnm);
		listMap.add(map);
		listView1.setAdapter(new SimpleAdapter(this,listMap,R.layout.listview1_layout,
				new String[] {"route","stpnm"},new int[]{R.id.list1route,R.id.list1routename}));
	}
	
	private void setListView2() {
		listView2.setAdapter(new SimpleAdapter(this,nlist,R.layout.listview_2_layout,
				new String[]{"vid","des","prdtm"},new int[]{R.id.textView1,R.id.textView2,R.id.textView3}));
		
	}

	class DownloadPrediction extends AsyncTask<String,Void,String> {

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
					nlist = new ArrayList<Map<String,String>>();
					break;
				case XmlPullParser.START_TAG:
					String name = parser.getName();
					//Log.v("test1", name);
					if(name.equals("prdtm")) {
						event = parser.next();
						Timestamp currentTS = new Timestamp(System.currentTimeMillis());
						Date date1 = new Date(currentTS.getTime());
						String pre = parser.getText();
						pre = pre.substring(0,4) + "-" + pre.substring(4,pre.length());
						pre = pre.substring(0,7) + "-" + pre.substring(7,pre.length());
						pre = pre + ":00";
						Timestamp predictedTS = Timestamp.valueOf(pre);
						Date date2 = new Date(predictedTS.getTime());
						
						long time = getDateDiff(date1, date2, TimeUnit.MINUTES);
						String tt = String.valueOf(time);
						Log.v(pre, "time"+tt);
						String timeDiffString = time <= 1? "Approaching" : String.valueOf(time) + " minutes";
						list.add(timeDiffString);
						smap.put("prdtm", timeDiffString);
						nlist.add(new HashMap<String,String>(smap));
						Log.v("pre", timeDiffString);
					} else if(name.equals("des")) {
						event = parser.next();
						String des = parser.getText();
						
						smap.put("des",des);
						Log.v("pre", des);
					} else if(name.equals("vid")) {
						event = parser.next();
						String vid = parser.getText();
						smap = new HashMap<String,String>();
						smap.put("vid", vid);
						Log.v("pre", vid);
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
		
//		StopsActivity.this.setListAdapter(new ArrayAdapter<String>(StopsActivity.this,
//				android.R.layout.simple_list_item_1,list));
		setListView1();
		setListView2();

		}
		
	
	
		private long getDateDiff(Date date1,Date date2,TimeUnit timeUnit) {
			long diffInMills = Math.abs(date2.getTime() - date1.getTime());
			return timeUnit.convert(diffInMills, TimeUnit.MILLISECONDS);
		}
	}	
	
}
