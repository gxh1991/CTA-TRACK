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

import com.BBrian.getapi.ConnectAPI;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BusFragment extends Fragment{
	
	private Intent busIntent;
	private Map<String,String> map;
	private List<Map<String,String>> list;
	private ListView lv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		busIntent = new Intent();
		String url = "http://www.ctabustracker.com/bustime/api/v1/getroutes?" + ConnectAPI.BusKey;
		lv = (ListView)getActivity().findViewById(R.id.MyListView2);
		new DownloadBusList().execute(url);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	class DownloadBusList extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			return ConnectAPI.loadXmlFromNetwork(urls[0]);
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
					map = new HashMap<String,String>();
					list = new ArrayList<Map<String,String>>();
					break;
				case XmlPullParser.START_TAG:
					String name = parser.getName();
					if(name.equals("rt")) {
						event = parser.next();
						map.put("rt", parser.getText());
					} else if(name.equals("rtnm")) {
						event = parser.next();
						map.put("rtnm", parser.getText());
						list.add(new HashMap<String,String>(map));
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
			lv.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.bus_fragment_listview, 
					new String[] {"route","stpnm"}, new int[] {R.id.busRt,R.id.busRtnm}));
		
		}
		
		
		
	}
	
}
