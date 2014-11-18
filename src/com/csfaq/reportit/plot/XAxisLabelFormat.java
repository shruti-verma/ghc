package com.csfaq.reportit.plot;

import android.os.Bundle;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Created by shash on 11/11/2014.
 */
public class XAxisLabelFormat extends Format {
	public String[] Labels;

	public XAxisLabelFormat(String[] labels) {
		Labels = labels;
	}

	@Override
	public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field)
	{
		int parsedInt =  Math.round(((Number) object).floatValue());
		String labelString = Labels[parsedInt];
		buffer.append(labelString);
		return buffer;
	}


	@Override
	public Object parseObject(String string, ParsePosition position) {
		return java.util.Arrays.asList(Labels).indexOf(string);
	}
}

