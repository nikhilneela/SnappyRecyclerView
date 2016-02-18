package com.example.nikhilneela.snappyrecyclerview;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SnappyRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (SnappyRecyclerView) findViewById(R.id.snappy_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addItemDecoration(new HorizontalItemSpacing(30));
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

    private class SnappyAdapter extends RecyclerView.Adapter<SnappyViewHolder> {

        @Override
        public SnappyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
            return new SnappyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SnappyViewHolder holder, int position) {
            holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
            holder.mRecyclerView.setAdapter(new InnerRecyclerViewAdapter());
            holder.headerTextView.setText("SnappyItem = " + position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private class SnappyViewHolder extends RecyclerView.ViewHolder  {
        private CardView mCardView;
        private RecyclerView mRecyclerView;
        private TextView headerTextView;
        public SnappyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.list_item);
            mRecyclerView = (RecyclerView) mCardView.findViewById(R.id.inner_recycler_view);
            mRecyclerView.setNestedScrollingEnabled(false);
            headerTextView = (TextView) mCardView.findViewById(R.id.header_text);
        }
    }


    private class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewHolder> {
        @Override
        public InnerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_list_item, null);
            return new InnerRecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(InnerRecyclerViewHolder holder, int position) {
            holder.mTextView.setText("Item " + position);
        }

        @Override
        public int getItemCount() {
            return 25;
        }
    }


    private class InnerRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        public InnerRecyclerViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.inner_text_view);
        }
    }
}
