package com.csfaq.reportit.model;

public class Complaint {

	public String category;
	public String subject;	
	public String comments;
	public String phNum;
	public String email;
	public String location;
	public String complaintNum;
	public String url;
	
	@Override
	public String toString() {
		return "Complaint in " + category + " " + comments;
	}
}
