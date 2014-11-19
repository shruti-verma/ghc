package com.csfaq.reportit.Activity;

import com.csfaq.reportit.R;
import com.csfaq.reportit.R.array;
import com.csfaq.reportit.R.id;
import com.csfaq.reportit.R.layout;
import com.csfaq.reportit.db.SharedPreferenceHandler;
import com.csfaq.reportit.model.Complaint;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View ios = inflater.inflate(R.layout.ios_frag, container, false);
		final Spinner categorySpinner = (Spinner)ios.findViewById(R.id.spinner);
		String[] categoryItems = SharedPreferenceHandler.getCategories(getActivity());
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, categoryItems);

		categorySpinner.setAdapter(aa);

		categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.i("GHC", categorySpinner.getSelectedItem().toString() + "  " + categorySpinner.getSelectedItemPosition());

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		Button searchButton = (Button)ios.findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ListView resultsListView = (ListView)v.getRootView().findViewById(R.id.resultsListView);
				List<Complaint> complaintList = SharedPreferenceHandler.getComplaints(getActivity());
				Complaint[] resultItems = complaintList.toArray(new Complaint[complaintList.size()]);//new String[]{"First Result", "Second Result"};
				ArrayAdapter<Complaint> aa = new ArrayAdapter<Complaint>(v.getContext(), android.R.layout.simple_list_item_1, resultItems);

				resultsListView.setAdapter(aa);
				
				resultsListView.setOnItemClickListener(new OnItemClickListener()
				{
				    @Override 
				    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
				    { 
//				        Toast.makeText(getActivity(), "fooo" + position, Toast.LENGTH_SHORT).show();
				    	
				    	String url = "http://vigeyegpms.in/bbmp/?module=helpdeskpublic&action=view-complaints";
				    	Intent i = new Intent(Intent.ACTION_VIEW);
				    	i.setData(Uri.parse(url));
				    	startActivity(i);
				    }
				});
				
				
				
				
			}
		});
		return ios;
	}


}
