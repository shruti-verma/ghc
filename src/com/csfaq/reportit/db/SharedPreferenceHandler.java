package com.csfaq.reportit.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.csfaq.reportit.model.Complaint;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

public class SharedPreferenceHandler {

	private static final String TAG = SharedPreferenceHandler.class.getName(); 

	public static final String COMPLAINTS_SHARED_PREF = "ComplaintsSharedPeference";

	public static final String KEY_COMPLAINTS = "Complaints";
	public static final String KEY_COMPLAINT_CATEGORY = "ComplaintCategory";
	public static final String KEY_COMPLAINT_CATEGORY_COUNT = "ComplaintCategoryCount";

	public static final String COMPLAINTS_SEPARATOR = "###############";	
	public static final String COMPLAINT_FIELD_SEPARATOR = ";;;;;;;;;;;";

	public static final String COMPLAINT_CAETGORY_SEPARATOR = "###############";

	public static final String COMPLAINT_CAETGORY_COUNT_SEPARATOR = "###############";
	public static final String COMPLAINT_CAETGORY_COUNT_FIELD_SEPARATOR = ";;;;;;;;;;;";




	public static void updateOtherCategoryCount(Context context, String newCategory) {
		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);
		SharedPreferences.Editor editor = prefs.edit();

		String oldCategoryCount = prefs.getString(KEY_COMPLAINT_CATEGORY_COUNT, null);
		String newCategoryCounts = "";
		boolean foundCategory = false;

		if (oldCategoryCount != null) {
			List<String> newCategoryCountList = new ArrayList<String>();
			Log.d(TAG, "oldCategoryCount not null");
			String[] oldCategoryCountPieces = oldCategoryCount.split(COMPLAINT_CAETGORY_COUNT_SEPARATOR);
			Log.d(TAG, "oldCategoryCountPieces size = " + oldCategoryCountPieces.length);
			for(String oldCategoryCountPiece : oldCategoryCountPieces) {
				String[] oldCategoryCountPieceSplits = oldCategoryCountPiece.split(COMPLAINT_CAETGORY_COUNT_FIELD_SEPARATOR);
				Log.d(TAG, "Processing " + oldCategoryCountPieceSplits[0] + " " + oldCategoryCountPieceSplits[1]);
				if (oldCategoryCountPieceSplits[0].equals(newCategory)) {					
					foundCategory = true;
					int count = Integer.parseInt(oldCategoryCountPieceSplits[1]) + 1;
					Log.d(TAG, "Found incrementing count to " + count);
					newCategoryCountList.add(oldCategoryCountPieceSplits[0] + COMPLAINT_CAETGORY_COUNT_FIELD_SEPARATOR + count);
				} else {
					newCategoryCountList.add(oldCategoryCountPieceSplits[0] + COMPLAINT_CAETGORY_COUNT_FIELD_SEPARATOR + oldCategoryCountPieceSplits[1]);
				}
			}
			newCategoryCounts = TextUtils.join(COMPLAINT_CAETGORY_COUNT_SEPARATOR, newCategoryCountList);
		} 
		if (!foundCategory) {
			Log.d(TAG, "not found adding first time");
			newCategoryCounts = newCategory + COMPLAINT_CAETGORY_COUNT_FIELD_SEPARATOR + 1;
		}

		editor.putString(KEY_COMPLAINT_CATEGORY_COUNT, newCategoryCounts);
		editor.commit();

	}

	public static void removeOtherCategoryCount(Context context, String newCategory) {
		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);
		SharedPreferences.Editor editor = prefs.edit();

		String oldCategoryCount = prefs.getString(KEY_COMPLAINT_CATEGORY_COUNT, null);
		String[] oldCategoryCountPieces;
		String newCategoryCounts;

		if (oldCategoryCount != null) {
			oldCategoryCountPieces = oldCategoryCount.split(COMPLAINT_CAETGORY_COUNT_SEPARATOR);
			for(String oldCategoryCountPiece : oldCategoryCountPieces) {
				String[] oldCategoryCountPieceSplits = oldCategoryCountPiece.split(COMPLAINT_CAETGORY_COUNT_FIELD_SEPARATOR);
				if (oldCategoryCountPieceSplits[0].equals(newCategory)) {					
					oldCategoryCountPieceSplits[1] = "" + 0;
				}
			}
			newCategoryCounts = TextUtils.join(COMPLAINT_CAETGORY_COUNT_SEPARATOR, oldCategoryCountPieces);
		} else {
			newCategoryCounts = newCategory + COMPLAINT_CAETGORY_COUNT_FIELD_SEPARATOR + 1;
		}

		editor.putString(KEY_COMPLAINT_CATEGORY_COUNT, newCategoryCounts);
		editor.commit();

	}


	public static List<NameValuePair> getOtherCategoryCount(Context context) {

		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);

		String oldCategoryCount = prefs.getString(KEY_COMPLAINT_CATEGORY_COUNT, null);

		List<NameValuePair> result = new ArrayList<NameValuePair>();

		if (oldCategoryCount != null) {
			String[] oldCategoryCountPieces = oldCategoryCount.split(COMPLAINT_CAETGORY_COUNT_SEPARATOR);
			for(String oldCategoryCountPiece : oldCategoryCountPieces) {
				String[] oldCategoryCountPieceSplits = oldCategoryCountPiece.split(COMPLAINT_CAETGORY_COUNT_FIELD_SEPARATOR);
				result.add(new BasicNameValuePair(oldCategoryCountPieceSplits[0], oldCategoryCountPieceSplits[1]));					
			}
			return result;
		}
		return null;
	}


	public static void saveCategories(Context context, String[] categories) {
		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);
		SharedPreferences.Editor editor = prefs.edit();

		String newCategoryStr = TextUtils.join(COMPLAINT_CAETGORY_SEPARATOR, categories);

		editor.putString(KEY_COMPLAINT_CATEGORY, newCategoryStr);
		editor.commit();
	}

	public static void addCategory(Context context, String newCategory) {
		
		Log.d(TAG, "Trying to add category " + newCategory);
		
		// check if it already exists
		String[] existingCategories = getCategories(context);
		for(String existingCategory : existingCategories) {
			if (existingCategory.equals(newCategory)) {
				Log.d(TAG, "Category already exists, not adding");
				return;
			}
		}
		
		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);
		SharedPreferences.Editor editor = prefs.edit();

		String complaintCategoriesStr = prefs.getString(KEY_COMPLAINT_CATEGORY, "");

		complaintCategoriesStr = complaintCategoriesStr + COMPLAINT_CAETGORY_SEPARATOR + newCategory;

		editor.putString(KEY_COMPLAINT_CATEGORY, complaintCategoriesStr);
		editor.commit();
	}

	public static String[] getCategories(Context context) {

		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);
		String complaintCategoriesStr = prefs.getString(KEY_COMPLAINT_CATEGORY, null);
		if (complaintCategoriesStr == null) {
			return null;
		}
		return complaintCategoriesStr.split(COMPLAINT_CAETGORY_SEPARATOR);		
	}

	public static void saveComplaints(Context context, Complaint complaint) {
		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);
		SharedPreferences.Editor editor = prefs.edit();

		String oldComplaints = prefs.getString(KEY_COMPLAINTS, "");

		String newComplaints;

		if (oldComplaints.equals("")) {
			newComplaints = getComplaintAsString(complaint);
		} else {
			newComplaints = oldComplaints + COMPLAINTS_SEPARATOR + getComplaintAsString(complaint);
		}

		editor.putString(KEY_COMPLAINTS, newComplaints);
		editor.commit();
	}

	public static List<Complaint> getComplaints(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);

		String complaints = prefs.getString(KEY_COMPLAINTS, "");		
		List<Complaint> result = new ArrayList<Complaint>();


		if (complaints != null && !complaints.equals("")) {
			String[] complaintsArray = complaints.split(COMPLAINTS_SEPARATOR);
			for(String complaint : complaintsArray) {
				Complaint newComplaint = convertStringToComplaint(complaint);
				if (newComplaint != null) {
					result.add(newComplaint);
				}
			}
		}
		return result;
	}

	private static String getComplaintAsString(Complaint complaint) {
		// category, subject, comments, phNum, email, location, complaintNum, url 
		return String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s", 
				complaint.category, COMPLAINT_FIELD_SEPARATOR,
				complaint.subject, COMPLAINT_FIELD_SEPARATOR,
				complaint.comments, COMPLAINT_FIELD_SEPARATOR,
				complaint.phNum, COMPLAINT_FIELD_SEPARATOR,
				complaint.email, COMPLAINT_FIELD_SEPARATOR,
				complaint.location, COMPLAINT_FIELD_SEPARATOR,
				complaint.complaintNum, COMPLAINT_FIELD_SEPARATOR,				
				complaint.url);
	}

	private static Complaint convertStringToComplaint(String complaintStr) {

		if (complaintStr == null || complaintStr.isEmpty()) {
			return null;
		}

		String[] complaintFields = complaintStr.split(COMPLAINT_FIELD_SEPARATOR);
		Complaint complaint = new Complaint();

		complaint.category = complaintFields[0];
		complaint.subject= complaintFields[1];
		complaint.comments= complaintFields[2];
		complaint.phNum= complaintFields[3];
		complaint.email= complaintFields[4];
		complaint.location= complaintFields[5];
		complaint.complaintNum= complaintFields[6];
		complaint.url= complaintFields[7];

		return complaint;
	}

	public static int getNumComplaints(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(COMPLAINTS_SHARED_PREF, 0);
		String allComplaints = prefs.getString(KEY_COMPLAINTS, "");

		if (allComplaints == null || allComplaints.isEmpty()) {
			return 0;
		}

		String[] complaintsArray = allComplaints.split(COMPLAINTS_SEPARATOR);

		return complaintsArray.length;
	}

	public static int getNumCompletedComplaints(Context context) {
		return 0;
	}
}
