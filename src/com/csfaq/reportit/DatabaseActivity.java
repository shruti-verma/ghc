package com.csfaq.reportit;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends Activity {

	TextView idView;
	EditText category;
	EditText status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ios_frag);


	}

	@Override
	protected void onStart()
	{
		super.onStart();
		//idView = (TextView) findViewById(R.id.textView);
		category = (EditText) findViewById(R.id.textView2);
	}


	public void newCompliant (View view) {
		MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1,null);

	}


}