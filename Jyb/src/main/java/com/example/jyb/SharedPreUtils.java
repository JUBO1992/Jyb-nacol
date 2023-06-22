package com.example.jyb;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreUtils
{
    private static final String SP_NAME = "data";
    private static SharedPreferences sp;
    private static String tag = SharedPreUtils.class.getSimpleName();

    public static void clear(Context paramContext)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        sp.edit().clear().commit();
    }

    public static boolean getBoolean(Context paramContext, String paramString, boolean paramBoolean)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        return sp.getBoolean(paramString, paramBoolean);
    }

    public static float getFloat(Context paramContext, String paramString, float paramFloat)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        return sp.getFloat(paramString, paramFloat);
    }

    public static int getInt(Context paramContext, String paramString, int paramInt)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        return sp.getInt(paramString, paramInt);
    }

    public static long getLong(Context paramContext, String paramString, long paramLong)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        return sp.getLong(paramString, paramLong);
    }

    public static String getString(Context paramContext, String paramString1, String paramString2)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        return sp.getString(paramString1, paramString2);
    }

    public static void saveBoolean(Context paramContext, String paramString, boolean paramBoolean)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        sp.edit().putBoolean(paramString, paramBoolean).commit();
    }

    public static void saveFloat(Context paramContext, String paramString, float paramFloat)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        sp.edit().putFloat(paramString, paramFloat).commit();
    }

    public static void saveInt(Context paramContext, String paramString, int paramInt)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        sp.edit().putInt(paramString, paramInt).commit();
    }

    public static void saveLong(Context paramContext, String paramString, long paramLong)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        sp.edit().putLong(paramString, paramLong).commit();
    }

    public static void saveString(Context paramContext, String paramString1, String paramString2)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("data", 0);
        }
        sp.edit().putString(paramString1, paramString2).commit();
    }
}
