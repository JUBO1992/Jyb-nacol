package com.example.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author jubo
 * @date 2024/01/14
 */
public class ImageUtils {


    //将Bitmap转换成InputStream 压缩率 100表示不压缩、10表示压缩90%
    public static InputStream bitmapInputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }


    // 将InputStream转换成Bitmap
    public static Bitmap inputStreamBitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);

    }

    // Drawable转换成InputStream
    public static InputStream drawableInputStream(Drawable d) {
        Bitmap bitmap = drawableBitmap(d);
        return bitmapInputStream(bitmap, 100);
    }

    // InputStream转换成Drawable
    public static Drawable inputStreamDrawable(InputStream is) {
        Bitmap bitmap = inputStreamBitmap(is);
        return bitmapDrawable(bitmap);
    }

    // Drawable转换成byte[]
    public static byte[] drawableBytes(Drawable d) {
        Bitmap bitmap = drawableBitmap(d);
        return bitmapBytes(bitmap);
    }

    // byte[]转换成Drawable
    public static Drawable bytesDrawable(byte[] b) {
        Bitmap bitmap = bytesBitmap(b);
        return bitmapDrawable(bitmap);

    }

    // Bitmap转换成byte[]
    public static byte[] bitmapBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // byte[]转换成Bitmap
    public static Bitmap bytesBitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    // 将byte[]转换成InputStream
    public static InputStream byteInputStream(byte[] b) {
        return new ByteArrayInputStream(b);
    }

    // 将InputStream转换成byte[]
    public static byte[] inputStreamBytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Drawable转换成Bitmap
    public static Bitmap drawableBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap转换成Drawable
    public static Drawable bitmapDrawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }

    //将Bitmap转换成Base64
    public static String getImageStr(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩104byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
    }

    //将Base64转换成bitmap
    public static Bitmap getImage(String str) {
        byte[] bytes;
        bytes = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}