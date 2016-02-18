package com.example.nikhilneela.snappyrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by nikhilneela on 18/2/16.
 */
public class SnappyRecyclerView extends RecyclerView {

    private ClippingInfo left, middle, right;

    private static final String TAG = SnappyRecyclerView.class.getSimpleName();

    public SnappyRecyclerView(Context context) {
        super(context);
        left = new ClippingInfo();
        middle = new ClippingInfo();
        right = new ClippingInfo();
    }

    public SnappyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        left = new ClippingInfo();
        middle = new ClippingInfo();
        right = new ClippingInfo();
    }

    public SnappyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        left = new ClippingInfo();
        middle = new ClippingInfo();
        right = new ClippingInfo();
    }

    private boolean mAutoSet = false;
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if(mAutoSet && state == RecyclerView.SCROLL_STATE_IDLE) {
                LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) getLayoutManager());
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                left.view   = linearLayoutManager.findViewByPosition(position);
                left.adapterPosition = position;
                middle.view = linearLayoutManager.findViewByPosition(position + 1);
                middle.adapterPosition = position + 1;
                right.view  = linearLayoutManager.findViewByPosition(position + 2);
                right.adapterPosition = position + 2;

                getClippedArea(left);
                getClippedArea(middle);
                getClippedArea(right);

                Log.d(TAG, left.toString() + " " + middle.toString() + " " + right.toString());

                ClippingInfo inter = ClippingInfo.min(left, middle);
                ClippingInfo inter1 = ClippingInfo.min(inter, right);

                Log.d(TAG, "Final = " + inter1.toString());
                mAutoSet = false;
                int scrollNeeded = inter1.view.getLeft() - 70;
                smoothScrollBy(scrollNeeded, 0);

        }
        if(state == RecyclerView.SCROLL_STATE_DRAGGING) {
            mAutoSet = true;
        }
    }

    private void getClippedArea(final ClippingInfo clippingInfo) {
        int clippedArea;
        View view = clippingInfo.view;

        if (view == null) {
            clippingInfo.clippedArea = Integer.MAX_VALUE;
            return;
        } else {
            if (view.getLeft() < getLeft()) {
                clippedArea = Math.abs(view.getLeft() + getLeft());
            } else if (view.getRight() > getRight()) {
                clippedArea = Math.abs(view.getRight() - getRight());
            } else {
                clippedArea = 0;
            }
        }
        clippingInfo.clippedArea = clippedArea;
    }


    private static class ClippingInfo {
        private View view;
        private int clippedArea;
        private int adapterPosition;

        public static ClippingInfo min(ClippingInfo i1, ClippingInfo i2) {
            if (i1.clippedArea < i2.clippedArea) {
                return i1;
            } else {
                return i2;
            }
        }

        @Override
        public String toString() {
            if (view != null) {
                return "Width " + view.getWidth() + " CA = " + clippedArea + " POS = " + adapterPosition;

            } else {
                return " CA = " + clippedArea + " POS = " + adapterPosition;
            }
        }
    }
}
