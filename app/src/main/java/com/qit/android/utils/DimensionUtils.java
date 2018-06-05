package com.qit.android.utils;

import android.content.Context;
import android.util.TypedValue;

public class DimensionUtils {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float px)  {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px,  context.getResources().getDisplayMetrics());
    }
}
