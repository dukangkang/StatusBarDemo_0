package com.docking.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by docking on 16/6/22 14:04.
 */
public class StatusBarUtils {

    private static int DEFAULT_COLOR  = R.color.color3097fd;
    /**
     * 默认开启
     */
    private static boolean isEnableImmersion = true;

    /**
     * 是否开启沉浸式
     *
     * @return true 开启 false 关闭
     */
    public static boolean isEnableImmersion() {
        return isEnableImmersion;
    }

    /**
     * 设置沉浸模式
     *
     * @param immersion true: 开启 false: 关闭
     */
    public static void setEnableImmersion(boolean immersion) {
        isEnableImmersion = immersion;
    }

    /**
     * 初始化沉浸式状态栏
     * @param activity
     */
    public static void initStatusBar(Activity activity) {
        initStatusBar(activity, DEFAULT_COLOR);
    }

    /**
     * 初始化沉浸式状态栏
     * @param activity
     * @param color
     *  状态栏背景色
     */
    public static void initStatusBar(Activity activity, int color) {
        if (activity == null || color <= 0) {
            return;
        }

        Log.e("info", "18 = Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);//显示状态栏
            setStatusBarTranslation(activity, true);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(android.R.color.transparent);

            // sdk >= 19 添加自定义状态栏
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            ViewGroup vg = (ViewGroup) contentView.getParent();
            View mStatusBarView = new View(activity);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            mStatusBarView.setBackgroundResource(color);
            vg.addView(mStatusBarView, 0, lp);
        }
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @TargetApi(19)
    public static void setStatusBarTranslation(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 设置状态栏背景色
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {
        if (true == isEnableImmersion) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 19
                ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
                ViewGroup vg = (ViewGroup) contentView.getParent();
                if(null != vg && 0 < vg.getChildCount()) {
                    vg.getChildAt(0).setBackgroundResource(color);
                }
            }
        }
    }

    /**
     * 状态栏显示隐藏设置
     */
    public static void setStatusBarVisibility(Activity activity, int visibility) {
        if (true == isEnableImmersion) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 19
                ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
                ViewGroup vg = (ViewGroup) contentView.getParent();
                if(null != vg && 0 < vg.getChildCount()) {
                    vg.getChildAt(0).setVisibility(visibility);
                }
            }
        }
    }

    /*
    * 全屏
    */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void fullScreen(Activity activity, View view) {
//        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE); // 显示标题的时候沉浸在标题下面
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /*
     * 非全屏
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void unFullScreen(Activity activity, View view) {
//        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); // 显示标题的时候沉浸在标题下面
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /*
     * 全屏
     * 状态显示和内容上下显示,非沉浸
     * @param activity
     */
    public static void lowFullScreen(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*
     * 非全屏
     * 状态显示和内容上下显示,非沉浸
     * @param activity
     */
    public static void lowUnFullScreen(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*
     * 全屏
     * 状态显示的时候浮在上面
     * @param activity
     */
    public static void immersiveFullScreen(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        Log.e("info", "clear activity.getWindow().getAttributes().flags = " + activity.getWindow().getAttributes().flags);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /*
     * 非全屏
     * 状态显示的时候浮在上面
     * @param activity
     */
    public static void immersiveUnFullScreen(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        Log.e("info", "activity.getWindow().getAttributes().flags = " + activity.getWindow().getAttributes().flags);
    }


    public static void reset(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }
}
