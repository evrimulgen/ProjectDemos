package com.projects.demo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

public class ImageUtil {

	/**
	 * through the rectangle zooming images
	 * @param bitmap expect being resized original picture
	 * @param width expected width for bitmap
	 * @param height expected height for bitmap
	 * @return 
	 */
	public static Bitmap createBitmapThumbnail(Bitmap bitmap, int width,
            int height) {
        final int bitmapWidth = bitmap.getWidth();
        final int bitmapHeight = bitmap.getHeight();

        if (width > 0 && height > 0) {

            final Bitmap.Config c = Bitmap.Config.ARGB_8888;
            final Bitmap thumb = Bitmap.createBitmap(width, height, c);
            final Canvas canvas = new Canvas();
            final Paint paint = new Paint();

            canvas.setBitmap(thumb);
            paint.setDither(false);
            paint.setFilterBitmap(true);
            Rect sBounds = new Rect();
            sBounds.set(0, 0, width, height);
            Rect sOldBounds = new Rect();
            sOldBounds.set(0, 0, bitmapWidth, bitmapHeight);
            canvas.drawBitmap(bitmap, sOldBounds, sBounds, paint);
            
            return thumb;
        }

        return bitmap;
    }
	
	 
    /**
     * through the Matirx zooming images
     */
    public static Bitmap zoomBitmap(Bitmap bitmap,int w,int h){

    	int width = bitmap.getWidth();
    	int height = bitmap.getHeight();
    	Matrix matrix = new Matrix();
    	float scaleWidht = ((float)w / width);
    	float scaleHeight = ((float)h / height);
    	matrix.postScale(scaleWidht, scaleHeight);
    	Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    	return newbmp;

    }
	
	public static Bitmap drawable2Bitmap(Drawable drawable) {
        try {
            int width = drawable.getIntrinsicWidth(); 
            int height = drawable.getIntrinsicHeight();
            Bitmap.Config config = Bitmap.Config.ARGB_8888; //RGB565
            Bitmap bitmap = Bitmap.createBitmap(width, height, config); 
            Canvas canvas = new Canvas(bitmap); 
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas); 
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static Bitmap view2Bitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        
        //Bitmap bitmap1 = setAlpha(bitmap, 100);

        return bitmap;
    }
	
}
