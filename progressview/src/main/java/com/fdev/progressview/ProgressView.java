package com.fdev.progressview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import androidx.annotation.Nullable;

/*
 * -
 * Project: ePars
 * Package: com.fdev.progressview
 * -
 * Author: Neikovich Sergey
 * Email: s_neiko@outlook.com
 * Skype: jastair & Telegram: @sneiko
 * -
 * Date: 18.02.2019
 */
public class ProgressView extends View {

    // region View Attrs Variables
    private Integer ringColor;
    private Integer progressColor;
    private Integer animProgressColor;
    private Integer textSize = 72;
    private Integer max = 100;
    private boolean isIndeterminate = true;
    private int progressBarPadding = 30;
    private double currentProgress = 30;

    int width = 0;
    int height = 0;
    Point center = new Point();

    int mDiametre = 0;
    int mRadius = 0;
    float mAnimProgressRadius = 0;
    float mAnimDegrees = 0;
    float mStartAnimDegrees = 0;
    int mAnimDegreesStopRandom = 0;

    Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint animProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint progressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    RectF progressRect = new RectF();

    ValueAnimator animProgressValueAnimator;
    ValueAnimator animDegreesValueAnimator;

    // endregion

    // region Constructors
    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    // endregion

    void init(Context context, AttributeSet attrs) {
        TypedArray tAttrsArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        ringColor = tAttrsArray.getColor(R.styleable.ProgressView_ring_color, Color.LTGRAY);
        progressColor = tAttrsArray.getColor(R.styleable.ProgressView_progress_color, Color.DKGRAY);
        animProgressColor = tAttrsArray.getColor(R.styleable.ProgressView_anim_progress_color, Color.DKGRAY);
        progressBarPadding = tAttrsArray.getInt(R.styleable.ProgressView_progress_bar_padding, 30);
        max = tAttrsArray.getInt(R.styleable.ProgressView_max, 100);
        isIndeterminate = tAttrsArray.getBoolean(R.styleable.ProgressView_indeterminate, false);
        tAttrsArray.recycle();

        setupPaintSettings();
    }

    void setupPaintSettings() {

        // ring
        ringPaint.setColor(ringColor);

        // anim progress
        animProgressPaint.setColor(animProgressColor);
        animProgressPaint.setStrokeWidth(15);
        animProgressPaint.setStyle(Paint.Style.STROKE);
        animProgressPaint.setStrokeJoin(Paint.Join.ROUND);
        animProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        // progress
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(25);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeJoin(Paint.Join.ROUND);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        // progress text
        progressTextPaint.setColor(progressColor);
        progressTextPaint.setTextSize(textSize);
        progressTextPaint.setTextAlign(Paint.Align.CENTER);
        progressTextPaint.setTypeface(Typeface.DEFAULT_BOLD);

    }

    @Override
    public void setVisibility(int visibility) {
        int prevVisibility = getVisibility();
        super.setVisibility(visibility);

        if (visibility == VISIBLE && prevVisibility != VISIBLE) {
            setupAnimation();
        } else if (visibility != VISIBLE && prevVisibility == VISIBLE) {
            stopAnimation();
        }
    }

    void setupAnimation() {
        animProgressValueAnimator = ValueAnimator.ofFloat(mAnimProgressRadius, 0);
        animProgressValueAnimator.setInterpolator(new AnticipateInterpolator());
        animProgressValueAnimator.setDuration(1000);
        animProgressValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animProgressValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        animProgressValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimProgressRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animProgressValueAnimator.start();


        animDegreesValueAnimator = ValueAnimator.ofFloat(0, 360);
        animDegreesValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        animDegreesValueAnimator.setDuration(1000);
        animDegreesValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animDegreesValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        animDegreesValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimDegrees += (float) animation.getAnimatedValue();

                invalidate();
            }
        });
        animProgressValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                mAnimDegreesStopRandom = (int) ((Math.random() * 360) - 1);
            }
        });
        animDegreesValueAnimator.start();
    }

    void stopAnimation() {
        if (animProgressValueAnimator != null) {
            animProgressValueAnimator.cancel();
            animProgressValueAnimator = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(width, height);

        center.x = width / 2;
        center.y = height / 2;

        mDiametre = Math.min(width, height) - (Math.min(width, height) / 4 - 8);
        mRadius = mDiametre / 2;
        mAnimProgressRadius = mRadius;

        progressRect = new RectF(
                (center.x - mRadius) - progressBarPadding,
                (center.y - mRadius) - progressBarPadding,
                (center.x + mRadius) + progressBarPadding,
                (center.y + mRadius) + progressBarPadding
        );

        setupAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(center.x, center.y, mRadius, ringPaint);

        animProgressPaint.setStrokeWidth(mAnimProgressRadius / 4);
        animProgressPaint.setAlpha((int) ((mAnimProgressRadius / mRadius) * 100));
        canvas.drawCircle(center.x, center.y, mAnimProgressRadius, animProgressPaint);

        if (!isIndeterminate) {

            double drawProgress = (currentProgress / max) * 360;
            canvas.drawArc(progressRect, -90, (float) drawProgress, false, progressPaint);

            progressTextPaint.setTextSize(textSize + mAnimProgressRadius / 20);
            progressTextPaint.setAlpha(225);

            double progressInPercent = (currentProgress / max) * 100;
            canvas.drawText(String.valueOf((int) progressInPercent) + "%", center.x, center.y + textSize / 2, progressTextPaint);
        } else {
            canvas.drawArc(progressRect, mStartAnimDegrees, mAnimDegrees, false, progressPaint);
        }
    }


    void setProgress(int progress) {
        if (!isIndeterminate && progress <= max) {
            currentProgress = progress;
        }
    }
}
