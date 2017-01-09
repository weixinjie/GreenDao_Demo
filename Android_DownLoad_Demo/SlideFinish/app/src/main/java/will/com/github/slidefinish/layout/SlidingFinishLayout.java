package will.com.github.slidefinish.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by will on 2017/1/9.
 */

public class SlidingFinishLayout extends FrameLayout {
    private Scroller mScroller; //滑动帮助类
    private int mTouchSlop; //最小的滑动距离，与机型有关，需要动态获取
    private ViewGroup parentView; //顶层ViewGroup,我们的布局文件的最外层系统都会帮我们包装一层FrameLayout
    private int screenWidth; //屏幕宽度

    private int last_x; //上次按下的x y
    private int last_y;

    private int delay_x;
    private int delay_y;

    private boolean isFinish = false;

    private Activity mActivity;

    private SlidingFinishLayout.OnActivityFinishListener onActivityFinish;

    public SlidingFinishLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void attachActivity(Activity activity) {
        this.mActivity = activity;
    }

    public void detachActivity() {
        if (this.mActivity != null) {
            mActivity = null;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            ViewGroup decor = (ViewGroup) mActivity.getWindow().getDecorView();
            mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mActivity.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
            View decorChild = decor.findViewById(android.R.id.content);
            while (decorChild.getParent() != decor) {
                decorChild = (View) decorChild.getParent();
            }
            parentView = decor;
            screenWidth = getWidth();
        }
    }


    /**
     * 进行事件拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last_x = (int) ev.getRawX();
                last_y = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int current_x = (int) ev.getRawX();
                int current_y = (int) ev.getRawY();

                delay_x = current_x - last_x;
                delay_y = current_y - last_y;
                if ((Math.abs(delay_x) / (Math.abs(delay_y) + 1) >= 2)) { //30度角的时候才开始拦截事件
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return isIntercept;
    }

    /**
     * 进行事件处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last_x = (int) ev.getRawX();
                last_y = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int current_x = (int) ev.getRawX();
                int current_y = (int) ev.getRawY();

                delay_x = current_x - last_x;
                delay_y = current_y - last_y;

                if (Math.abs(delay_x) > mTouchSlop) {
                    if ((Math.abs(delay_x) / (Math.abs(delay_y) + 1) >= 2)) { //30度角的时候才开始处理事件
                        parentView.scrollBy(-delay_x, 0); //开始滑动
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

                last_x = current_x;
                last_y = current_y;
                break;
            case MotionEvent.ACTION_UP:

                if (Math.abs(parentView.getScrollX()) > (screenWidth / 3)) { //如果滑动距离超出屏幕三分之一，则进行滑动销毁，否则恢复原位
                    scrollFinish();
                } else {
                    scrollOriginal();
                }
                break;
        }
        return true;
    }

    /**
     * 滑动销毁
     */
    private void scrollFinish() {
        final int delta = (screenWidth + parentView.getScrollX());
        mScroller.startScroll(parentView.getScrollX(), 0, -delta + 1, 0);
        postInvalidate();
        isFinish = true;
    }

    /**
     * 恢复到原始状态
     */
    private void scrollOriginal() {
        mScroller.startScroll(parentView.getScrollX(), 0, -parentView.getScrollX(), 0);
        postInvalidate();
        isFinish = false;
    }

    /**
     * Activity销毁的时候通知回调
     *
     * @param onActivityFinish
     */
    public void setOnActivityFinishListener(SlidingFinishLayout.OnActivityFinishListener onActivityFinish) {
        this.onActivityFinish = onActivityFinish;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            parentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished() && isFinish) {
                onActivityFinish.onActivityFinish();
            }
        }
    }

    /**
     *
     */
    public interface OnActivityFinishListener {
        void onActivityFinish();
    }
}
