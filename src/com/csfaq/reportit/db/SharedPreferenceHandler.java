package com.csfaq.reportit.db;

import java.util.ArrayList;
import java.util.List;

import com.csfaq.reportit.model.Complaint;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHandler {

	public static final String COMPLAINTS_SHARED_PREF = "ComplaintsSharedPeference";

	public static final String KEY_COMPLAINTS = "Complaints";
	public static final String COMPLAINTS_SEPARATOR = "###############";
	public static final String COMPLAINT_FIELD_SEPARATOR = ";;;;;;;;;;;";



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

	public static String getComplaintAsString(Complaint complaint) {
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

	public static Complaint convertStringToComplaint(String complaintStr) {

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
