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

import java.util.ArrayList;

class MemTesterAdapter extends RecyclerView.Adapter<MemTesterAdapter.ViewHolder> {
    private final String TAG = MemTesterAdapter.class.getSimpleName();
    private final MemTester mMemTester;
    private final ArrayList<MemTest> mMemTests;
    private Context mContext;

    public MemTesterAdapter(Context c, MemTester memTester) {
        mContext = c;
        mMemTester = memTester;
        mMemTests = mMemTester.getMemTests();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.v(TAG, "onCreateViewHolder: viewGroup=" + viewGroup +", i=" + i);
        View view = LayoutInflater.from(mContext).inflate(R.layout.cell_test, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        holder.tvName = view.findViewById(R.id.tvName);
        holder.pbProgress = view.findViewById(R.id.progressBar);
        holder.tvStatus = view.findViewById(R.id.tvStatus);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.v(TAG, "onBindViewHolder: viewHolder=" + viewHolder +", i=" + i);
        viewHolder.tvName.setText(mMemTests.get(i).name);
        float progress = mMemTests.get(i).progress;

        viewHolder.pbProgress.setProgress((int) progress);
        viewHolder.tvStatus.setText(mMemTests.get(i).status.toString());
    }

    @Override
    public int getItemCount() {
        return mMemTests.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ProgressBar pbProgress;
        public TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
