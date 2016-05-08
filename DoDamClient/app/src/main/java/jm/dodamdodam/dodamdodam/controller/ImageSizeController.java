package jm.dodamdodam.dodamdodam.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by IronFactory on 15. 12. 5..
 */
public class ImageSizeController {
    public static Bitmap getSmallImage(Context context, int imageRes) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 6;
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageRes, options);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageRes, options);
            return bitmap;
        }
    }
}
