package com.haoyun.memtester;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

class MemTesterAdapter extends RecyclerView.Adapter<MemTesterAdapter.ViewHolder> {
    private final String TAG = MemTesterAdapter.class.getSimpleName();
    private String[] mTests;
    private Context mContext;
    public MemTesterAdapter(Context c, MemTester mMemTester) {
        mContext = c;
        mTests = mMemTester.getTests();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.v(TAG, "onCreateViewHolder: viewGroup=" + viewGroup +", i=" + i);
        View view = LayoutInflater.from(mContext).inflate(R.layout.cell_test, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        holder.tvName = view.findViewById(R.id.tvName);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.v(TAG, "onBindViewHolder: viewHolder=" + viewHolder +", i=" + i);
        viewHolder.tvName.setText(mTests[i]);
    }

    @Override
    public int getItemCount() {
        return mTests.length;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvProgressText;
        public ProgressBar pbProgress;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
