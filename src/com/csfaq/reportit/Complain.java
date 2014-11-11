package com.csfaq.reportit;


import com.csfaq.reportit.utils.Utils;
import com.csfaq.reportit.R;
import com.csfaq.reportit.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Complain extends Fragment {

	// Code for our image picker select action.
	private static final int IMAGE_PICKER_SELECT = 999;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View android1 = inflater.inflate(R.layout.android_frag, container, false);
		final Spinner categorySpinner = (Spinner)android1.findViewById(R.id.spinner);

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


		EditText tv1 = (EditText) android1.findViewById(R.id.description);
		tv1.setText("");

		Button tv2 = (Button) android1.findViewById(R.id.ImageVideoPicker);
		tv2.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, IMAGE_PICKER_SELECT);	
			}
		});



		Button submitButton = (Button)android1.findViewById(R.id.Submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Thanks for submitting the complaint! We will get back to you shortly! )",
						Toast.LENGTH_LONG).show();  

				Utils.createNotification(MainActivity.getContext(), null, "Submit");

				// Post the notifications at a random time.
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// This method will be executed once the timer is over
						Utils.createNotification(MainActivity.getContext(), null, "DBSubmit");
					}
				}, Utils.randInt(8000, 14000));



			} });


		final Spinner logasSpinner = (Spinner)android1.findViewById(R.id.logspinner);

		String[] logItems = getResources().getStringArray(R.array.logas_category);
		ArrayAdapter<String> logCat = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, logItems);
		logasSpinner.setAdapter(logCat);

		return android1;
	}

		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK) {
			MainActivity activity = (MainActivity)getActivity();
			Bitmap bitmap = getBitmapFromCameraData(data, activity);
			}
			}
			/**
			* Use for decoding camera response data.
			*
			* @param data
			* @param context
			* @return
			*/
			public static Bitmap getBitmapFromCameraData(Intent data, Context context){
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			return BitmapFactory.decodeFile(picturePath);
			}
			
	}
