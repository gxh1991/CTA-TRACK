package com.BBrian.myfourthcta;

import java.util.ArrayList;
import java.util.List;

import com.BBrian.myfourthcta.BusFragment.MyItemClickListener;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TrainFragment extends Fragment {
	private List<String> list;
	private Intent trainIntent;
	private ListView lv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = new ArrayList<String>();
		list.add("Red Line");
		list.add("Blue Line");
		list.add("Brown Line");
		list.add("Green Line");
		list.add("Orange Line");
		list.add("Purple Line");
		list.add("Pink Line");
		list.add("Yellow Line");
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.firstfragment, container,false);
		v.setBackgroundColor(Color.parseColor("#4C4D45"));
		lv =(ListView) v.findViewById(R.id.MyListView1);
		
		//lv.setBackgroundColor(Color.parseColor("#2CB3E1"));
		lv.setAdapter(new Myadapter(list, getActivity()));
		return v;
		
	}
	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		trainIntent = new Intent();
		trainIntent.setClass(getActivity(), TrainStops.class);
		
		lv.setOnItemClickListener(new MyItemClickListener());
		
	}
	class MyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int postion,long id) {
			String line = list.get(postion);
			trainIntent.putExtra("line", line);
			startActivity(trainIntent);
		}
		
	}


	class Myadapter extends BaseAdapter {
		List<String> trains;
		List<String> org;
		Context context;
		LayoutInflater inflater;
		Filter myFilter;
		
		public Myadapter(List<String> trains,Context context) {
			this.trains = trains;
			this.context = context;
			inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return trains.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(v == null) 
				v = inflater.inflate(R.layout.train_listview, parent,false);
			
//			ImageView color = (ImageView)v.findViewById(R.id.img);
			TextView lineTV = (TextView)v.findViewById(R.id.traincolor);
			String line = trains.get(position).toString();
			lineTV.setText(line);
			if (line.equalsIgnoreCase("Blue Line")) {
				v.setBackgroundColor(Color.parseColor("#2CB3E1"));
			} else if (line.equalsIgnoreCase("Brown Line")) {
				v.setBackgroundColor(Color.parseColor("#BF857C"));
			} else if (line.equalsIgnoreCase("Green Line")) {
				v.setBackgroundColor(Color.parseColor("#48B4B0"));
			} else if (line.equalsIgnoreCase("Orange Line")) {
				v.setBackgroundColor(Color.parseColor("#E24126"));
			} else if (line.equalsIgnoreCase("Pink Line")) {
				v.setBackgroundColor(Color.parseColor("#FAADD7"));
			} else if (line.equalsIgnoreCase("Purple Line")) {
				v.setBackgroundColor(Color.parseColor("#7A4BA6"));
			} else if (line.equalsIgnoreCase("Red Line")) {
				v.setBackgroundColor(Color.parseColor("#F14949"));
			} else if (line.equalsIgnoreCase("Yellow Line")) {
				v.setBackgroundColor(Color.parseColor("#F5D02A"));
			}
			
			return v;
		}
	}
	
}
