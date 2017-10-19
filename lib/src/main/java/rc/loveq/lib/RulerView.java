package rc.loveq.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.OverScroller;

/**
 * @author：Rc
 * @date:2017/10/16 22:12
 */

public class RulerView extends View {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public static final int TOP = 0;
    public static final int BOTTOM = 1;


//    @IntDef({HORIZONTAL, VERTICAL})
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface OrientationMode {
//    }

    private static final boolean DEFAULT_IS_DRAW_TEXT = true;
    private static final int DEFAULT_INDICATE_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_ORIENTATION = HORIZONTAL;
    private static final int DEFAULT_GRAVITY = BOTTOM;
    private static final int DEFAULT_START_INDEX = 0;
    private static final int DEFAULT_END_INDEX = 100;

    private static final int DEFAULT_INDICATE_WIDTH = 12;
    private static final int DEFAULT_INDICATE_HEIGHT = 24;

    private static final int DEFAULT_SMALL_INDICATE_WIDTH = 10;
    private static final int DEFAULT_SMALL_INDICATE_HEIGHT = 10;

    private static final int DEFAULT_SMALL_INDICATE_COUNT = 4;
    private static final int DEFAULT_INDICATE_PADDING = 12;

    private static final int DEFAULT_INDICATE_MARGIN_TEXT = 16;
    private static final int DEFAULT_SMALL_INDICATE_COLOR = Color.YELLOW;


    public int mIndicateColor;
    public int mTextColor;
    private int mTextSize;
    private int mOrientation;
    private int mGravity;
    private int mStartIndex;
    private int mEndIndex;
    private int mIndicateWidth;
    private int mIndicatePadding;
    private int mIndicateMarginText;
    private int mIndicateHeight;
    private int mSmallIndicateWidth;
    private int mSmallIndicateHeight;
    private int mSmallIndicateCount;
    private Paint mIndicatePaint;
    private int mSmallIndicateColor;
    private Paint mTextPaint;
    private Paint mSmallIndicatePaint;
    private int mTouchSlop;
    private OverScroller mScroller;
    private boolean mIsDrawText;
    private int mRulerViewWidth;
    private int mRulerViewHeight;

    private boolean mIsScroll;

    /**
     * 上一次motion event 位置
     */
    private int mLastMotionX;
    private int mLastMotionY;
    private int mInitialMotionX;
    private int mInitialMotionY;


    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private int mOverscrollDistance;
    private int mOverflingDistance;

    private boolean mIsBeingDragged;
    private int mActivePointerId;

    private static final int INVALID_POINTER = -1;
    public int mPaddingLeft;
    public int mPaddingRight;
    public int mPaddingBottom;
    public int mPaddingTop;


    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRulerView();
        initAttrs(context, attrs);
        initPaint();
    }

    private void initRulerView() {
        mScroller = new OverScroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mOverscrollDistance = configuration.getScaledOverscrollDistance();
        mOverflingDistance = configuration.getScaledOverflingDistance();
        setOverScrollMode(OVER_SCROLL_ALWAYS);
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RulerView);
        mIndicateColor = array.getColor(R.styleable.RulerView_indicateColor, DEFAULT_INDICATE_COLOR);
        mTextColor = array.getColor(R.styleable.RulerView_textColor, DEFAULT_TEXT_COLOR);
        mSmallIndicateColor = array.getColor(R.styleable.RulerView_smallIndicateColor, DEFAULT_SMALL_INDICATE_COLOR);
        mTextSize = array.getDimensionPixelSize(R.styleable.RulerView_textSize, DEFAULT_TEXT_SIZE);
        mOrientation = array.getInt(R.styleable.RulerView_orientation, DEFAULT_ORIENTATION);
        mGravity = array.getInt(R.styleable.RulerView_gravity, DEFAULT_GRAVITY);
        mStartIndex = array.getInt(R.styleable.RulerView_startIndex, DEFAULT_START_INDEX);
        mEndIndex = array.getInt(R.styleable.RulerView_endIndex, DEFAULT_END_INDEX);
        mIndicateWidth = array.getDimensionPixelSize(R.styleable.RulerView_indicateWidth, DEFAULT_INDICATE_WIDTH);
        mIndicateHeight = array.getDimensionPixelSize(R.styleable.RulerView_indicateHeight, DEFAULT_INDICATE_HEIGHT);
        mSmallIndicateWidth = array.getDimensionPixelSize(R.styleable.RulerView_smallIndicateWidth, DEFAULT_SMALL_INDICATE_WIDTH);
        mSmallIndicateHeight = array.getDimensionPixelSize(R.styleable.RulerView_smallIndicateHeight, DEFAULT_SMALL_INDICATE_HEIGHT);
        mIndicatePadding = array.getDimensionPixelSize(R.styleable.RulerView_indicatePadding, DEFAULT_INDICATE_PADDING);
        mIndicateMarginText = array.getDimensionPixelSize(R.styleable.RulerView_indicateMarginText, DEFAULT_INDICATE_MARGIN_TEXT);
        mSmallIndicateCount = array.getInt(R.styleable.RulerView_smallIndicateCount, DEFAULT_SMALL_INDICATE_COUNT);
        mIsDrawText = array.getBoolean(R.styleable.RulerView_isDrawText, DEFAULT_IS_DRAW_TEXT);
        array.recycle();
    }

    private void initPaint() {
        mIndicatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatePaint.setStyle(Paint.Style.FILL);
        mIndicatePaint.setColor(mIndicateColor);

        mSmallIndicatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallIndicatePaint.setStyle(Paint.Style.FILL);
        mSmallIndicatePaint.setColor(mSmallIndicateColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRulerViewWidth = w;
        mRulerViewHeight = h;
        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();
        mPaddingBottom = getPaddingBottom();
        mPaddingTop = getPaddingTop();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawIndicate(canvas);
        drawIndicateText(canvas);
    }

    private void drawIndicate(Canvas canvas) {
        for (int i = 0; i < mEndIndex - mStartIndex + 1; i++) {

            if (i % (mSmallIndicateCount + 1) == 0) {
                //画长指示器
                int index = i / (mSmallIndicateCount + 1);
                //中间短指示器的总长度
                int midSmallIndicateWidth = (mSmallIndicateWidth + mIndicatePadding) * mSmallIndicateCount;

                int startIndicateWidth = mIndicateWidth + mIndicatePadding;

                int left = (midSmallIndicateWidth + startIndicateWidth) * index + mIndicatePadding / 2;
                int right = (midSmallIndicateWidth + startIndicateWidth) * (index + 1)
                        - mIndicatePadding / 2 - midSmallIndicateWidth;

                if (mOrientation == HORIZONTAL && mGravity == BOTTOM) {
                    canvas.drawRect(left, 0, right, mIndicateHeight, mIndicatePaint);
                }

                if (mOrientation == HORIZONTAL && mGravity == TOP) {
                    canvas.drawRect(left, mRulerViewHeight - mIndicateHeight, right,
                            mRulerViewHeight, mIndicatePaint);
                }

                if (mOrientation == VERTICAL && mGravity == BOTTOM) {
                    canvas.drawRect(0, left, mIndicateHeight, right, mIndicatePaint);
                }

                if (mOrientation == VERTICAL && mGravity == TOP) {
                    canvas.drawRect(mRulerViewWidth - mIndicateHeight, left, mRulerViewWidth, right, mIndicatePaint);
                }


            } else {
                //画短指示器
                int index = i / (mSmallIndicateCount + 1);
                int innerIndex = i % (mSmallIndicateCount + 1);
                //中间短指示器的总长度
                int midSmallIndicateWidth = (mSmallIndicateWidth + mIndicatePadding) * mSmallIndicateCount;
                int startIndicateWidth = mIndicateWidth + mIndicatePadding;
                int left = (midSmallIndicateWidth + startIndicateWidth) * index +
                        mIndicatePadding + mIndicateWidth +
                        (mIndicatePadding + mSmallIndicateWidth) * (innerIndex - 1) + mIndicatePadding / 2;
                int right = (midSmallIndicateWidth + startIndicateWidth) * index +
                        mIndicatePadding + mIndicateWidth +
                        (mIndicatePadding + mSmallIndicateWidth) * (innerIndex) - mIndicatePadding / 2;

                if (mOrientation == HORIZONTAL && mGravity == BOTTOM) {
                    canvas.drawRect(left, 0, right, mSmallIndicateHeight, mSmallIndicatePaint);
                }
                if (mOrientation == HORIZONTAL && mGravity == TOP) {
                    canvas.drawRect(left, mRulerViewHeight - mSmallIndicateHeight,
                            right, mRulerViewHeight, mSmallIndicatePaint);
                }

                if (mOrientation == VERTICAL && mGravity == BOTTOM) {
                    canvas.drawRect(0, left, mSmallIndicateHeight, right, mSmallIndicatePaint);
                }

                if (mOrientation == VERTICAL && mGravity == TOP) {
                    canvas.drawRect(mRulerViewWidth - mSmallIndicateHeight, left, mRulerViewWidth, right, mSmallIndicatePaint);
                }
            }
        }
    }

    private void drawIndicateText(Canvas canvas) {
        if (!mIsDrawText) {
            return;
        }
        for (int i = 0, text = mStartIndex; i < mEndIndex - mStartIndex + 1; text++, i++) {

            if (i % (mSmallIndicateCount + 1) == 0) {
                int index = i / (mSmallIndicateCount + 1);
                drawText(canvas, text, index);
            }
        }
    }

    private void drawText(Canvas canvas, int text, int index) {
        int midSmallIndicateWidth = (mSmallIndicateWidth + mIndicatePadding) * mSmallIndicateCount;
        int startIndicateWidth = mIndicateWidth + mIndicatePadding;
        if (mOrientation == HORIZONTAL) {
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            int x = (midSmallIndicateWidth + startIndicateWidth) * index +
                    mIndicatePadding / 2 + mIndicateWidth / 2;
            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
            if (mGravity == BOTTOM) {
                int top = mIndicateHeight + mIndicateMarginText;
                canvas.drawText(String.valueOf(text), x, top - metrics.top, mTextPaint);
            }
            if (mGravity == TOP) {
                int bottom = mRulerViewHeight - (mIndicateHeight + mIndicateMarginText);
                canvas.drawText(String.valueOf(text), x, bottom - metrics.bottom, mTextPaint);
            }
        }

        if (mOrientation == VERTICAL) {
            mTextPaint.setTextAlign(Paint.Align.LEFT);
            int y = (midSmallIndicateWidth + startIndicateWidth) * index +
                    mIndicatePadding / 2 + mIndicateWidth / 2;
            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
            int baseLineY = (int) (y + (metrics.bottom - metrics.top) / 2 - metrics.bottom);
            if (mGravity == BOTTOM) {
                int x = mIndicateHeight + mIndicateMarginText;
                canvas.drawText(String.valueOf(text), x, baseLineY, mTextPaint);
            }
            if (mGravity == TOP) {
                int textWidth = (int) mTextPaint.measureText(String.valueOf(text));
                int x = mRulerViewWidth - (mIndicateHeight + mIndicateMarginText) - textWidth;
                canvas.drawText(String.valueOf(text), x, baseLineY, mTextPaint);
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if ((mIsBeingDragged = !mScroller.isFinished())) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                mLastMotionX = mInitialMotionX = (int) event.getX();
                mLastMotionY = mInitialMotionY = (int) event.getY();
                mActivePointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsBeingDragged) {
                    int pointerIndex = event.findPointerIndex(mActivePointerId);
                    if (pointerIndex == -1) {
                        resetTouch();
                        break;
                    }
                    float x = event.getX(pointerIndex);
                    float xDiff = Math.abs(x - mLastMotionX);
                    float y = event.getY(pointerIndex);
                    float yDiff = Math.abs(y - mLastMotionY);
                    if (xDiff > mTouchSlop && xDiff > yDiff && mOrientation == HORIZONTAL) {
                        mIsBeingDragged = true;
                        //因为第一次拖动的距离必须大约mTouchSlop
                        //所以为了使拖动效果连续，这里补偿缺少的距离
                        mLastMotionX = x - mInitialMotionX > 0 ? mInitialMotionX + mTouchSlop :
                                mInitialMotionX - mTouchSlop;
                        if (getParent() != null) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (yDiff > mTouchSlop && yDiff > xDiff && mOrientation == VERTICAL) {
                        mIsBeingDragged = true;
                        mLastMotionY = y - mInitialMotionY > 0 ? mInitialMotionY + mTouchSlop :
                                mInitialMotionY - mTouchSlop;
                        if (getParent() != null) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }

                }
                if (mIsBeingDragged) {
                    performDrag(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int initialVelocity = (int) velocityTracker.getXVelocity(mActivePointerId);
                    int yVelocity = (int) velocityTracker.getYVelocity(mActivePointerId);
                    if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                        if (mOrientation == HORIZONTAL) {
                            fling(-initialVelocity);
                        } else {
                            flingY(-yVelocity);
                        }

                    } else {

                    }
                    mIsBeingDragged = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int index = event.getActionIndex();

                mLastMotionX = mInitialMotionX = (int) event.getX(index);
                mLastMotionY = mInitialMotionY = (int) event.getY(index);
                mActivePointerId = event.getPointerId(index);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(event);
                mLastMotionX = (int) event.getX(event.findPointerIndex(mActivePointerId));
                break;

            default:
                break;
        }
        return true;
    }

    private void flingY(int i) {
        mScroller.fling(getScrollX(), getScrollY(),
                0, i, 0, 0, getMinScrollY(), getContentLength() + getVerticalAlignCenter());
        postInvalidate();
    }

    private void fling(int velocity) {
        if (mOrientation == HORIZONTAL) {
            mScroller.fling(getScrollX(), getScrollY(),
                    velocity, 0, getMinScrollX(), getContentLength() + getHorizontalAlignCenter(), 0, 0);
        }
        if (mOrientation == VERTICAL) {
            mScroller.fling(getScrollX(), getScrollY(),
                    0, velocity, 0, 0, getMinScrollY(), getContentLength() + getVerticalAlignCenter());
        }
        postInvalidate();
    }

    private void resetTouch() {
        mActivePointerId = INVALID_POINTER;
        endDrag();
    }

    private void onSecondaryPointerUp(MotionEvent event) {
        final int pointerIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(pointerIndex);
        //最后一个按下的pointer和松开的相等
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionX = mInitialMotionX = (int) event.getX(newPointerIndex);
            mLastMotionY = mInitialMotionY = (int) event.getY(newPointerIndex);
            mActivePointerId = event.getPointerId(newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

    private void performDrag(MotionEvent event) {
        if (mOrientation == HORIZONTAL) {
            int activePointerIndex = event.findPointerIndex(mActivePointerId);
            int x = (int) event.getX(activePointerIndex);
            int deltaX = mLastMotionX - x;
            if (overScrollBy(deltaX, 0, getScrollX(), getScrollY(),
                    getContentLength(), 0,
                    getHorizontalAlignCenter(), 0, true)) {
                mVelocityTracker.clear();
            }
            mLastMotionX = x;
        }
        if (mOrientation == VERTICAL) {
            int activePointerIndex = event.findPointerIndex(mActivePointerId);
            int y = (int) event.getY(activePointerIndex);
            int deltaY = mLastMotionY - y;
            if (overScrollBy(0, deltaY, getScrollX(), getScrollY(),
                    0, getContentLength(), 0, getVerticalAlignCenter(), true)) {
                mVelocityTracker.clear();
            }
            mLastMotionY = y;
        }

    }

    private void endDrag() {
        mIsBeingDragged = false;

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (!mScroller.isFinished()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            scrollTo(scrollX, scrollY);
            onScrollChanged(scrollX, scrollY, oldX, oldY);
        } else {
            super.scrollTo(scrollX, scrollY);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            if (mOrientation == HORIZONTAL) {
                overScrollBy(x - oldX, y - oldY, oldX, oldY, getContentLength(), 0,
                        getHorizontalAlignCenter(), 0, false);
            } else {
                overScrollBy(x - oldX, y - oldY, oldX, oldY, 0, getContentLength(),
                        0, getVerticalAlignCenter(), false);
            }
            postInvalidate();
        } else {
            adjust();
        }
    }

    private void adjust() {
        if (mIsBeingDragged) {
            return;
        }
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        //TODO:调整位置
    }


    /**
     * @return 获取RulerView内容的宽度(或高度)
     */
    private int getContentLength() {
        int indicateCount = mEndIndex - mStartIndex + 1;
        int cellCount = indicateCount / (mSmallIndicateCount + 1);
        int extraCount = indicateCount % (mSmallIndicateCount + 1);
        int cellIndicateLength = cellCount * (mSmallIndicateCount * (mSmallIndicateWidth + mIndicatePadding) +
                mIndicateWidth + mIndicatePadding) + extraCount * (mSmallIndicateWidth + mIndicatePadding);
        if (mOrientation == HORIZONTAL) {
            return Math.max(0, cellIndicateLength - (mRulerViewWidth - getPaddingRight() - getPaddingRight()));
        }
        if (mOrientation == VERTICAL) {
            return Math.max(0, cellIndicateLength - (mRulerViewHeight - getPaddingTop() - getPaddingBottom()));
        }
        return 0;
    }

    private int getMinScrollX() {
        return -getHorizontalAlignCenter();
    }

    private int getMaxScrollX() {
        return getContentLength() + getMinScrollX();
    }

    private int getMinScrollY() {
        return -getVerticalAlignCenter();
    }


    private int getHorizontalAlignCenter() {
        return mRulerViewWidth / 2;
    }

    private int getVerticalAlignCenter() {
        return mRulerViewHeight / 2;
    }


}
