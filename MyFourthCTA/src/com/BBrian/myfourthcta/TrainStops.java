package com.BBrian.myfourthcta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class TrainStops extends ListActivity {
	private Intent intent;
	List<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();
		String line = intent.getStringExtra("line");
		String[] stops = getTrainStops(line);
		list = new ArrayList<String>(Arrays.asList(stops));
		getListView().setBackgroundColor(Color.parseColor("#E9EBEA"));
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1
				,list));
		
	}
	
	
	public String[] getTrainStops(String line) {
		String[] blue = {"O'Hare", "Rosemont", "Cumberland", "Harlem (O'Hare)", "Jefferson Park", 
				"Montrose", "Irving Park", "Addison", "Belmont", "Logan Square", 
				"California", "Western (O'Hare)", "Damen", "Division", "Chicago", "Grand", "State/Lake", 
				"Washington", "Monroe", "Jackson", "LaSalle", "Clinton", "UIC-Halsted", "Racine", 
				"Illinois Medical District", "Western (Forest Park)", "Kedzie-Homan", "Pulaski", "Cicero", 
				"Austin", "Oak Park", "Harlem (Forest Park)", "Forest Park"};

		String[] brown = {"Kimball", "Kedzie", "Francisco", "Rockwell", "Western", "Damen",
				"Montrose", "Irving Park", "Addison", "Paulina", "Southport", "Belmont", "Wellington",
				"Diversey", "Fullerton", "Armitage", "Sedgwick", "Chicago", "Merchandise Mart",
				"Washington/Wells", "Quincy", "LaSalle/Van Buren", "Harold Washington Library-State/Van Buren",
				"Adams/Wabash", "Madison/Wabash", "Randolph/Wabash", "State/Lake", "Clark/Lake"};

		String[] green = {"Harlem/Lake", "Oak Park", "Ridgeland", "Austin", "Central", "Laramie", "Cicero",
				"Pulaski", "Conservatory-Central Park Drive", "Kedzie", "California", "Ashland", "Morgan",
				"Clinton", "Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash", "Adams/Wabash",
				"Roosevelt", "35th-Bronzeville-IIT", "Indiana", "43rd", "47th", "51st", "Garfield", "King Drive",
				"Cottage Grove", "Halsted", "Ashland/63rd"};

		String[] orange = {"Midway", "Pulaski", "Kedzie", "Western", "35th/Archer", "Ashland", "Halsted", 
				"Roosevelt", "Harold Washington Library-State/Van Buren", "LaSalle/Van Buren", "Quincy",
				"Washington/Wells", "Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash",
				"Adams/Wabash"};

		String[] pink = {"54th/Cermak", "Cicero", "Kostner", "Pulaski", "Central Park", "Kedzie", 
				"California", "Western", "Damen", "18th", "Polk", "Ashland", "Morgan", "Clinton", 
				"Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash", "Adams/Wabash", 
				"Harold Washington Library-State/Van Buren", "LaSalle/Van Buren", "Quincy",
				"Washington/Wells"};

		String[] purple = {"Linden", "Central", "Noyes", "Foster", "Davis", "Dempster", "Main", "South Blvd",
				"Howard", "Belmont", "Wellington", "Diversey", "Fullerton", "Armitage", "Sedgwick", 
				"Chicago", "Merchandise Mart", "Clark/Lake", "State/Lake", "Randolph/Wabash", 
				"Madison/Wabash", "Adams/Wabash", "Harold Washington Library-State/Van Buren", 
				"LaSalle/Van Buren", "Quincy", "Washington/Wells"};

		String[] red = {"Howard", "Jarvis", "Morse", "Loyola", "Granville", "Thorndale", "Bryn Mawr", 
				"Berwyn", "Argyle", "Lawrence", "Wilson", "Sheridan", "Addison", "Belmont", "Fullerton", 
				"North/Clybourn", "Clark/Division", "Chicago", "Grand", "Lake", "Monroe", "Jackson", 
				"Harrison" ,"Roosevelt", "Cermak-Chinatown", "Sox-35th", "47th", "Garfield", "63rd", 
				"69th", "79th", "87th", "95th/Dan Ryan"};

		String[] yellow = {"Dempster-Skokie", "Oakton-Skokie", "Howard"};

		//DETERMINES WHICH ARRAY OF STOPS IS CHOSEN
		if (line.equalsIgnoreCase("Blue Line")) {
			return blue;
		} else if (line.equalsIgnoreCase("Brown Line")) {
			return brown;
		} else if (line.equalsIgnoreCase("Green Line")) {
			return green;
		} else if (line.equalsIgnoreCase("Orange Line")) {
			return orange;
		} else if (line.equalsIgnoreCase("Pink Line")) {
			return pink;
		} else if (line.equalsIgnoreCase("Purple Line")) {
			return purple;
		} else if (line.equalsIgnoreCase("Red Line")) {
			return red;
		} else if (line.equalsIgnoreCase("Yellow Line")) {
			return yellow;
		}
		return null;
	}

}
