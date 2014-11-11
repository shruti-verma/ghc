package com.csfaq.reportit.utils;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.csfaq.reportit.Complain;
import com.csfaq.reportit.NotificationReceiverActivity;
import com.csfaq.reportit.R;

import java.util.Random;

public class Utils {

	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static void createNotification(Context ctx, View view, String type) {

		if (type.equals("Submit")) {
			// Prepare intent which is triggered if the
			// notification is selected
			
			  
			Intent intent = new Intent(ctx, Complain.class);
			PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

			// Build notification
			Notification notification = new Notification.Builder(ctx)
			.setContentTitle("Congratulations!!")
			.setContentText("Your compliant is submitted succesfully. We will get back to you shortly").setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pIntent)
			//.addAction(R.drawable.ic_launcher, "Call", pIntent)
			//.addAction(R.drawable.ic_launcher, "More", pIntent)
			//.addAction(R.drawable.ic_launcher, "And more", pIntent)
			.build();
			
			
			NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(ctx.NOTIFICATION_SERVICE);
			// hide the notification after its selected
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			notificationManager.notify(0, notification);
		} else if (type.equals("DBSubmit")) {
			// Prepare intent which is triggered if the
			// notification is selected
			Intent intent = new Intent(ctx,Complain.class);
			PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

			// Build notification
			Notification notification = new Notification.Builder(ctx)
			.setContentTitle("Congratulations!!")
			.setContentText("Your compliant processing is under progress").setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pIntent)
			//.addAction(R.drawable.ic_launcher, "Call", pIntent)
			//.addAction(R.drawable.ic_launcher, "More", pIntent)
			//.addAction(R.drawable.ic_launcher, "And more", pIntent)
			.build();
			NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(ctx.NOTIFICATION_SERVICE);
			// hide the notification after its selected
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			notificationManager.notify(1, notification);
		}
	}
}
