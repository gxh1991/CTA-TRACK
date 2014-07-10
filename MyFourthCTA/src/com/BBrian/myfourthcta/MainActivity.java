package com.BBrian.myfourthcta;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowHomeEnabled(false);  
		actionBar.setDisplayShowTitleEnabled(false);
		
		Tab tab = actionBar.newTab().setText("Favorite").
				setTabListener(new MyTabListener<TrainFragment>("Train",TrainFragment.class));
		actionBar.addTab(tab);
	}
	
	
	class MyTabListener<T extends Fragment> implements ActionBar.TabListener {
		private Fragment mFragment;
		private String mTag;
		private Class<T> mClass;
		
		public MyTabListener(String mTag,Class<T> mClass) {
			this.mTag = mTag;
			this.mClass = mClass;
			
		}
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if(mFragment == null) {
				mFragment = new TrainFragment();
				mFragment = Fragment.instantiate(MainActivity.this, mClass.getName());
				ft.add(android.R.id.content,mFragment,mTag);
				
			} else {
				ft.attach(mFragment);
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.detach(mFragment);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
//			if(mFragment != null) {
//				ft.detach(mFragment);
//			}
		}
		
	}
	
	
	
	
}
