/*******************************************************************************
 * This software is provided as a supplement to the authors' textbooks on digital
 *  image processing published by Springer-Verlag in various languages and editions.
 * Permission to use and distribute this software is granted under the BSD 2-Clause 
 * "Simplified" License (see http://opensource.org/licenses/BSD-2-Clause). 
 * Copyright (c) 2006-2016 Wilhelm Burger, Mark J. Burge. All rights reserved. 
 * Visit http://imagingbook.com for additional details.
 *******************************************************************************/
package Ch11_Automatic_Thresholding;

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import imagingbook.pub.threshold.BackgroundMode;
import imagingbook.pub.threshold.adaptive.AdaptiveThresholder;
import imagingbook.pub.threshold.adaptive.InterpolatingThresholder;
import imagingbook.pub.threshold.adaptive.InterpolatingThresholder.Parameters;

/**
 * Demo plugin showing the use of the InterpolatingThresholder class.
 * @author W. Burger
 * @version 2013/05/30
 */
public class Threshold_Adaptive_Interpolating implements PlugInFilter {

	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}

	public void run(ImageProcessor ip) {
		ByteProcessor I = (ByteProcessor) ip;
		Parameters params = new Parameters();
		if (!setParameters(params))
			return;
		AdaptiveThresholder thr = new InterpolatingThresholder(params);
		ByteProcessor Q = thr.getThreshold(I);
		thr.threshold(I, Q);
	}
	
	boolean setParameters(Parameters params) {
		GenericDialog gd = new GenericDialog(this.getClass().getSimpleName());
		gd.addNumericField("tile size", params.tileSize, 0);
		gd.addCheckbox("bright background", params.bgMode == BackgroundMode.BRIGHT);
		gd.showDialog();
		if (gd.wasCanceled()) {
			return false;
		}	
		params.tileSize = (int) gd.getNextNumber();
		params.bgMode = (gd.getNextBoolean()) ? BackgroundMode.BRIGHT : BackgroundMode.DARK;
		return true;
	}
}
