package com.csfaq.reportit.Activity;


import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.csfaq.reportit.R;
import com.csfaq.reportit.Helper.ComplaintCreatorHelper;
import com.csfaq.reportit.constants.Constants;
import com.csfaq.reportit.db.SharedPreferenceHandler;
import com.csfaq.reportit.model.Complaint;
import com.csfaq.reportit.utils.NetworkUtils;
import com.csfaq.reportit.utils.Utils;

public class ComplaintFragment extends Fragment {
	
	private static final String TAG = ComplaintFragment.class.getName();

	// Code for our image picker select action.
	private static final int IMAGE_PICKER_SELECT = 999;
	private String mCategory;
	private String mSubject;	
	private String mComments;
	private String mPhNum;
	private String mEmail;
	private String mLocation;
	private String mComplaintNum;
	private String mUrl;

	private EditText mDescriptionTv;
	private View mView;

	// default complaint categories
	private static final String[] defaultComplaintCategories = {
		"Auto/Bus",
		"Bribe",
		"Garbage Collection",
		"Accident",
		"Fire",
		"Police",
		"Women Abuse",
		"Pet Care",
		"Others"
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.complain_frag, container, false);
		final Spinner categorySpinner = (Spinner)mView.findViewById(R.id.spinner);

		//String[] categoryItems = getResources().getStringArray(R.array.complaint_category);
		String[] categoryItems = SharedPreferenceHandler.getCategories(getActivity());
		if (categoryItems == null || categoryItems.length == 0) {
			SharedPreferenceHandler.saveCategories(getActivity(), defaultComplaintCategories);
			categoryItems = defaultComplaintCategories;
		}

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, categoryItems);
		categorySpinner.setAdapter(aa);
		categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.i("GHC", categorySpinner.getSelectedItem().toString() + "  " + categorySpinner.getSelectedItemPosition());
				mCategory = categorySpinner.getSelectedItem().toString();
				
				final LinearLayout otherCategoryLayout = (LinearLayout)mView.findViewById(R.id.otherCategoryLayout);
				if (mCategory.equals("Others")) {					
					otherCategoryLayout.setVisibility(View.VISIBLE);
				} else {
					otherCategoryLayout.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mDescriptionTv = (EditText) mView.findViewById(R.id.description);
		mDescriptionTv.setText("");

		Button tv2 = (Button) mView.findViewById(R.id.ImageVideoPicker);
		tv2.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, IMAGE_PICKER_SELECT);	
			}
		});



		Button submitButton = (Button)mView.findViewById(R.id.Submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				mComments = mDescriptionTv.getText().toString();

				if (mCategory != null && !mCategory.isEmpty() &&
						mComments != null && !mComments.isEmpty()) {

					mSubject = "Complaint on " + mCategory;	
					mPhNum = "9999999999";
					mEmail = "foo@bar.com";
					mLocation = "Mandur";
					mComplaintNum = "123";
					mUrl = "http://vigeyegpms.in/bbmp/?module=helpdeskpublic&action=view-complaints";


					// Save in Shared preference
					Complaint complaint = new Complaint();
					complaint.category = mCategory;
					complaint.comments = mComments;
					complaint.subject = mSubject;
					SharedPreferenceHandler.saveComplaints(getActivity(), complaint);



					//new DownloadFilesTask(getActivity()).execute(complaint);

					// Create notifications
					Utils.createNotification(MainActivity.getContext(), null, "Submit");

					// Post the notifications at a random time.
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// This method will be executed once the timer is over
							Utils.createNotification(MainActivity.getContext(), null, "DBSubmit");
						}
					}, Utils.randInt(8000, 14000));

					// refresh view
					refreshCountViews();
					
					// Update category if needed
					if (mCategory.equals("Others")) {
						Log.d(TAG, "Other category");
						final EditText otherCategoryName = (EditText)mView.findViewById(R.id.otherCategoryEditText);
						
						SharedPreferenceHandler.updateOtherCategoryCount(getActivity(), otherCategoryName.getText().toString());
						Log.d(TAG, "Updated Other category count");
						
						List<NameValuePair> otherCategoryCounts = SharedPreferenceHandler.getOtherCategoryCount(getActivity());
						
						for(NameValuePair nvp : otherCategoryCounts) {
							if (Integer.parseInt(nvp.getValue()) > 3 ) {
								Log.d(TAG, "Found Other category count > threshold");
								SharedPreferenceHandler.addCategory(getActivity(), nvp.getName());
								SharedPreferenceHandler.removeOtherCategoryCount(getActivity(), nvp.getName());
							}
						}
					}
					
					
				} else {
					Toast.makeText(getActivity(), "Enter all necessary fields", Toast.LENGTH_SHORT).show();
				}

			} });


		final Spinner logasSpinner = (Spinner)mView.findViewById(R.id.logspinner);

		String[] logItems = getResources().getStringArray(R.array.logas_category);
		ArrayAdapter<String> logCat = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, logItems);
		logasSpinner.setAdapter(logCat);


		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		refreshCountViews();

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

	public void refreshCountViews() {
		TextView totalTv = (TextView) mView.findViewById(R.id.totalCount);
		TextView completedTv = (TextView) mView.findViewById(R.id.completedCount);

		if (totalTv != null && completedTv != null) {
			totalTv.setText("" + SharedPreferenceHandler.getNumComplaints(getActivity()));
			completedTv.setText("" + SharedPreferenceHandler.getNumCompletedComplaints(getActivity()));
		}
	}

}


class DownloadFilesTask extends AsyncTask<Complaint, Void, HttpResponse> {

	private Context mContext;

	public DownloadFilesTask(Context context) {
		mContext = context;
	}

	protected HttpResponse doInBackground(Complaint... complaints) {

		Complaint complaint = complaints[0];

		//String body = ComplaintCreatorHelper.createBBMPPostBody("Subject", "Name", "999999999", "foo@bar.com", "195", "location", "complaint");
		String body = ComplaintCreatorHelper.createBBMPPostBody(complaint.subject, "Anon", complaint.phNum, complaint.email, "195", complaint.location, complaint.comments);
		StringEntity se = null;
		try {
			se = new StringEntity(body, "UTF-8");
		} catch (Exception e) {

		}
		return NetworkUtils.postData(Constants.BBMP_POST_URL, se, Constants.BBMP_POST_HEADER);
	}

	protected void onProgressUpdate(Void... progress) {
	}

	protected void onPostExecute(HttpResponse result) {

		String message = "";
		try {
			message = NetworkUtils.readIt(result.getEntity().getContent());
		} catch (Exception e) {

		}
		Toast.makeText(mContext, message, Toast.LENGTH_LONG).show(); 
	}
}

