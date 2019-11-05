package com.marginallyclever.makelangeloRobot.generators;

import java.io.IOException;
import java.io.Writer;
import com.marginallyclever.makelangelo.Translator;

/**
 * Draws a border around the paper.  Uses current paper settings.
 * @author Dan Royer
 *
 */
public class Generator_Border extends ImageGenerator {
	
	@Override
	public String getName() {
		return Translator.get("BorderName");
	}

	@Override
	public ImageGeneratorPanel getPanel() {
		return new Generator_Empty_Panel(this);
	}

	/**
	 * @param out
	 * @throws IOException
	 */
	@Override
	public boolean generate(Writer out) throws IOException {
		imageStart(out);

		float yMin = (float)machine.getMarginBottom();
		float yMax = (float)machine.getMarginTop();
		float xMin = (float)machine.getMarginLeft();
		float xMax = (float)machine.getMarginRight();

		moveTo(out,xMin,yMax,true);
		moveTo(out,xMin,yMax,false);
		moveTo(out,xMax,yMax,false);
		moveTo(out,xMax,yMin,false);
		moveTo(out,xMin,yMin,false);
		moveTo(out,xMin,yMax,false);
		
		imageEnd(out);
	    
	    return true;
	}
}
