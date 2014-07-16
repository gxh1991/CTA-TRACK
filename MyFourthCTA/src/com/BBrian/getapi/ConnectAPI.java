package com.BBrian.getapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class ConnectAPI {
	public static String BusKey = "key=qehr5FhtFK5EJMh2z4zXhBnQH";
	public static String TrainKey = "key=8fb049a789d84835b12917cdddeffd1b";
	public static String loadXmlFromNetwork(String urlString) {
		try {
			InputStream is;
			StringBuilder builder = new StringBuilder();
			//urlString = urlString + key;
			URL url = new URL(urlString);
			BufferedReader br;
//			Log.v("key", urlString);
//			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//			conn.setReadTimeout(10000);
//			conn.setConnectTimeout(15000);
//			conn.setRequestMethod("GET");
//			conn.setDoInput(true);
//			conn.connect();
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			HttpClient client = new DefaultHttpClient();  
			HttpGet get = new HttpGet(urlString);
			HttpResponse response = client.execute(get);  
			 if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
				 is = response.getEntity().getContent();  
				 br = new BufferedReader(new InputStreamReader(is));
			 } else return null;

			String line;
			while((line = br.readLine()) != null) {
				builder.append(line);
				Log.v("out", line);
			}
			return builder.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
