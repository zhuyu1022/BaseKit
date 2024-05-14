package com.zhuyu.basekit.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.LruCache;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class ScreenShotUtil {

    /**
     * 根据指定的Activity截图（带空白的状态栏）
     *
     * @param context 要截图的Activity
     * @return Bitmap
     */
    public static Bitmap shotActivity(Activity context) {
        View view = context.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }


    /**
     * 根据指定的Activity截图（去除状态栏）
     *
     * @param activity 要截图的Activity
     * @return Bitmap
     */
    public Bitmap shotActivityNoStatusBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }



    /**
     * 根据指定的view截图
     *
     * @param v 要截图的view
     * @return Bitmap
     */
    public static Bitmap shotView(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }

        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bitmap;
    }






    public  static  Bitmap shotRecyclerView(RecyclerView recyclerView) {
        //获取设置的adapter
        RecyclerView.Adapter adapter =  recyclerView.getAdapter();
        //创建保存截图的bitmap
        Bitmap bigBitmap = null;
        if (adapter != null) {
            //获取item的数量
            int size = adapter.getItemCount();
            //recycler的完整高度 用于创建bitmap时使用
            int height = 0;
            //获取最大可用内存
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // 使用1/8的缓存
            final int cacheSize = maxMemory / 8;
            //把每个item的绘图缓存存储在LruCache中
            LruCache<String, Bitmap> bitmapCache = new LruCache<>(cacheSize);

            for (int i = 0; i < size; i++) {
                //手动调用创建和绑定ViewHolder方法，
                RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);

                //测量
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                //布局
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                //开启绘图缓存
                Bitmap drawingCache = Bitmap.createBitmap(holder.itemView.getMeasuredWidth()-holder.itemView.getPaddingLeft()-holder.itemView.getPaddingRight(), holder.itemView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(drawingCache);
                //canvas.drawColor(Color.parseColor("#ffffff"));
                holder.itemView.draw(canvas);

                if (drawingCache != null) {
                    bitmapCache.put(String.valueOf(i), drawingCache);
                }
                //获取itemView的实际高度并累加
                height += holder.itemView.getMeasuredHeight();
            }

            //根据计算出的recyclerView高度创建bitmap
            bigBitmap = Bitmap.createBitmap(recyclerView.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            //创建一个canvas画板
            Canvas canvas = new Canvas(bigBitmap);
            //当前bitmap的高度
            int top = 0;
            int left = 0;
            //画笔
            Paint paint = new Paint();
            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmapCache.get(String.valueOf(i));
                canvas.drawBitmap(bitmap, left, top, paint);

                left = 0;
                top += bitmap.getHeight();
            }
        }
        return bigBitmap;
    }


    /**
     * Scrollview截屏
     *
     * @param scrollView 要截图的ScrollView
     * @return Bitmap
     */
    public static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * Scrollview截屏
     *
     * @param scrollView 要截图的ScrollView
     * @return Bitmap
     */

    public static Bitmap shotScrollView(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
           //scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    /**
     * 截取webView可视区域的截图
     * @param webView 前提：WebView要设置webView.setDrawingCacheEnabled(true);
     * @return
     */
    private Bitmap captureWebViewVisibleSize(WebView webView){
        Bitmap bmp = webView.getDrawingCache();
        return bmp;
    }
    /**
     * 截屏
     *
     * @param context
     * @return
     */
    private Bitmap captureScreen(Activity context) {
        View cv = context.getWindow().getDecorView();
        Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        cv.draw(canvas);
        return bmp;
    }
}
