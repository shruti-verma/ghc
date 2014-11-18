package com.csfaq.reportit.Adapter;

import com.csfaq.reportit.Activity.ComplaintFragment;
import com.csfaq.reportit.Activity.DashboardFragment;
import com.csfaq.reportit.Activity.SearchFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			//Fragement for complaint
			return new ComplaintFragment();
		case 1:
			//Fragment for search
			return new SearchFragment();
		case 2:
			//Fragment for dashboard
			return new DashboardFragment();
		}
		return null;

	}

	@Override
	public int getCount() {
		return 3; 
	}


}