package com.csfaq.reportit;

import com.csfaq.reportit.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.util.Log;

import java.util.ArrayList;

public class Search extends Fragment {
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View ios = inflater.inflate(R.layout.ios_frag, container, false);
            final Spinner categorySpinner = (Spinner)ios.findViewById(R.id.spinner);
            String[] categoryItems = getResources().getStringArray(R.array.complaint_category);
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
                    String[] resultItems = new String[]{"First Result", "Second Result"};
                    ArrayAdapter<String> aa = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, resultItems);

                    resultsListView.setAdapter(aa);
                }
            });
            return ios;
}


}
