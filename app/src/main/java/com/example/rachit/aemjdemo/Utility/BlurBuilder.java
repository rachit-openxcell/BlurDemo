package com.example.rachit.aemjdemo.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.Type;

public class BlurBuilder {

    public static Bitmap blur(Bitmap bitmap, float radius, Context context) {
        //Create renderscript
        Bitmap bitmapUp = bitmap.copy(bitmap.getConfig(), true);

        RenderScript rs = RenderScript.create(context);

        //Create allocation from Bitmap
        Allocation allocation = Allocation.createFromBitmap(rs, bitmapUp);

        Type t = allocation.getType();

        //Create allocation with the same type
        Allocation blurredAllocation = Allocation.createTyped(rs, t);

        //Create script
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        //Set blur radius (maximum 25.0)
        blurScript.setRadius(radius);
        //Set input for script
        blurScript.setInput(allocation);
        //Call script for output allocation
        blurScript.forEach(blurredAllocation);

        //Copy script result into bitmap
        blurredAllocation.copyTo(bitmapUp);

        //Destroy everything to free memory
        allocation.destroy();
        blurredAllocation.destroy();
        blurScript.destroy();
        t.destroy();
        rs.destroy();
        return bitmapUp;
    }
}
