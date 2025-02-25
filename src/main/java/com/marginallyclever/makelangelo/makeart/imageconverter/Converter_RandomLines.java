package com.marginallyclever.makelangelo.makeart.imageconverter;

import com.marginallyclever.makelangelo.Translator;
import com.marginallyclever.makelangelo.makeart.TransformedImage;
import com.marginallyclever.makelangelo.makeart.imagefilter.FilterDesaturate;
import com.marginallyclever.makelangelo.paper.Paper;
import com.marginallyclever.makelangelo.select.SelectInteger;
import com.marginallyclever.makelangelo.turtle.Turtle;


/**
 * create random lines across the image.  Raise and lower the pen to darken the appropriate areas
 * @author Dan Royer
 */
public class Converter_RandomLines extends ImageConverter {
	static protected int numLines = 2500;

	public Converter_RandomLines() {
		super();
		SelectInteger selectTotal = new SelectInteger("total",Translator.get("ConverterRandomLinesCount"),getLineCount());
		add(selectTotal);

		selectTotal.addPropertyChangeListener((evt)->{
			if(evt.getPropertyName().equals("total")) setLineCount((int)evt.getNewValue());
			fireRestart();
		});
	}

	@Override
	public String getName() {
		return Translator.get("ConverterRandomLinesName");
	}

	@Override
	public void start(Paper paper, TransformedImage image) {
		super.start(paper, image);

		// The picture might be in color.  Smash it to 255 shades of grey.
		FilterDesaturate bw = new FilterDesaturate(myImage);
		TransformedImage img = bw.filter();


		double stepSize = 5;
		if (stepSize < 1) stepSize = 1;

		// Color values are from 0...255 inclusive.  255 is white, 0 is black.
		// Lift the pen any time the color value is > level (128 or more).
		double level = 255.0 / 4.0;

		// from top to bottom of the margin area...
		float yBottom = (float)myPaper.getMarginBottom();
		float yTop    = (float)myPaper.getMarginTop()   ;
		float xLeft   = (float)myPaper.getMarginLeft()  ;
		float xRight  = (float)myPaper.getMarginRight() ;
		double dy = yTop - yBottom-1;
		double dx = xRight - xLeft-1;

		turtle = new Turtle();
		turtle.moveTo(0, yTop);

		double startPX = 0; 
		double startPY = yTop;

		int i;
		for(i=0;i<numLines;++i) {
			level = 200.0 * (double)i / (double)numLines;
			double endPX = xLeft   + (Math.random() * dx)+0.5; 
			double endPY = yBottom + (Math.random() * dy)+0.5; 

			convertAlongLine(startPX,startPY,endPX,endPY,stepSize,level,img);
			
			startPX = endPX;
			startPY = endPY;
		}

		fireConversionFinished();
	}

	public int getLineCount() {
		return numLines;
	}
	public void setLineCount(int value) {
		if(value<1) value=1;
		numLines = value;
	}
}
