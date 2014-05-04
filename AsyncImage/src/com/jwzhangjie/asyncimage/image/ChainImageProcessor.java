package com.jwzhangjie.asyncimage.image;

import android.graphics.Bitmap;

/**
 * Allows multiple image processors to be chained
 */
public class ChainImageProcessor implements ImageProcessor {

    ImageProcessor[] mProcessors;

    /**
     * Create a new ChainImageProcessor.
     * 
     * @param processors An array of {@link ImageProcessor} that will be
     *            sequentially applied
     */
    public ChainImageProcessor(ImageProcessor... processors) {
        mProcessors = processors;
    }

    public Bitmap processImage(Bitmap bitmap) {
        for (ImageProcessor processor : mProcessors) {
            bitmap = processor.processImage(bitmap);
        }
        return bitmap;
    }

}
