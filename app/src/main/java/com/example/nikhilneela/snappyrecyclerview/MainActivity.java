package com.example.nikhilneela.snappyrecyclerview;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SnappyRecyclerView mRecyclerView;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (SnappyRecyclerView) findViewById(R.id.snappy_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addItemDecoration(new HorizontalItemSpacing(15));
        mRecyclerView.setAdapter(new SnappyAdapter());
    }


    private class HorizontalItemSpacing extends RecyclerView.ItemDecoration {
        private int mHorizontalSpacing;

        public HorizontalItemSpacing(int horizontalSpacing) {
            mHorizontalSpacing = horizontalSpacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mHorizontalSpacing;
        }
    }


    private class GridItemSpacing extends RecyclerView.ItemDecoration {
        private int mItemSpacing;

        public GridItemSpacing(int itemSpacing) {
            mItemSpacing = itemSpacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mItemSpacing;
            outRect.right = mItemSpacing;
            outRect.bottom = mItemSpacing;
            outRect.top = mItemSpacing;
        }
    }

    private class SnappyAdapter extends RecyclerView.Adapter<SnappyViewHolder> {

        @Override
        public SnappyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
            SnappyViewHolder viewHolder = new  SnappyViewHolder(view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2, LinearLayoutManager.VERTICAL, false);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 2;
                    }
                    return 1;
                }
            });
            viewHolder.mRecyclerView.setLayoutManager(gridLayoutManager);
            viewHolder.mRecyclerView.setAdapter(new InnerRecyclerViewAdapter());
            viewHolder.mRecyclerView.addItemDecoration(new GridItemSpacing(3));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SnappyViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    private class SnappyViewHolder extends RecyclerView.ViewHolder  {
        private CardView mCardView;
        private RecyclerView mRecyclerView;
        public SnappyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.list_item);
            mRecyclerView = (RecyclerView) mCardView.findViewById(R.id.inner_recycler_view);
            mRecyclerView.setNestedScrollingEnabled(false);
        }
    }


    private class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewHolder> {

        private int SMALL_IMAGE_TYPE = 0;
        private int LARGE_IMAGE_TYPE = 1;

        @Override
        public InnerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == SMALL_IMAGE_TYPE) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_list_item_small, null);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_list_item_large, null);
            }
            return new InnerRecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(InnerRecyclerViewHolder holder, int position) {
            holder.mSimpleTextView.setText("Item " + position);
        }

        @Override
        public int getItemCount() {
            return 125;
        }

        @Override
        public void onViewRecycled(InnerRecyclerViewHolder holder) {
            super.onViewRecycled(holder);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return LARGE_IMAGE_TYPE;
            } else {
                return SMALL_IMAGE_TYPE;
            }
        }
    }


    private class InnerRecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView mSimpleCardView;
        private TextView mSimpleTextView;
        public InnerRecyclerViewHolder(View itemView) {
            super(itemView);
            mSimpleCardView = (CardView) itemView.findViewById(R.id.simple_card_view);
            mSimpleTextView = (TextView) itemView.findViewById(R.id.simple_text_view);
        }
    }
}
