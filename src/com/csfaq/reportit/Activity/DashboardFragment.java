package com.csfaq.reportit.Activity;

import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeriesFormatter;
import com.csfaq.reportit.R;
import com.csfaq.reportit.R.array;
import com.csfaq.reportit.R.id;
import com.csfaq.reportit.R.layout;
import com.csfaq.reportit.plot.XAxisLabelFormat;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import android.graphics.Color;
import android.os.Bundle;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;


public class DashboardFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		String[] categoryItems = getResources().getStringArray(R.array.complaint_category);

		View windows = inflater.inflate(R.layout.windows_frag, container, false);
		XYPlot byCategory = (XYPlot)windows.findViewById(R.id.ByCategoryXYPlot);

		// Create two arrays of y-values to plot:
		Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
		String[] series2Numbers = {"Auto/Bus", "Accident", "Bribe", "Fire", "Police"};

		SimpleXYSeries series1 = new SimpleXYSeries(
				Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
				"Series1");                             // Set the display title of the series
		//SimpleXYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
		//      "Series2");

		LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.CYAN, Color.GRAY, Color.MAGENTA, new PointLabelFormatter(5));              // fill color (optional)
		BarFormatter barFormatter = new BarFormatter(Color.LTGRAY, Color.CYAN);

		byCategory.addSeries(series1, barFormatter);
		//byCategory.addSeries(series2, new LineAndPointFormatter(Color.RED, Color.GREEN, Color.DKGRAY, new BarFormatter(Color.GREEN.)));

		// Reduce the number of range labels
		byCategory.setTicksPerRangeLabel(3);
		byCategory.setTicksPerDomainLabel(2);

		// By default, AndroidPlot displays developer guides to aid in laying out your plot.
		// To get rid of them call disableAllMarkup():
		//byCategory.disableAllMarkup();

		byCategory.getBackgroundPaint().setAlpha(0);
		byCategory.getGraphWidget().setDomainValueFormat(new XAxisLabelFormat(series2Numbers));
		byCategory.getGraphWidget().getBackgroundPaint().setAlpha(0);
		byCategory.getGraphWidget().getGridBackgroundPaint().setAlpha(0);

		return windows;
	}}
