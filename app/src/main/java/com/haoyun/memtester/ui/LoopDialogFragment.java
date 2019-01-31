package com.haoyun.memtester.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.haoyun.memtester.R;

import java.util.Locale;

public class LoopDialogFragment extends DialogFragment {

    private static final int MIN_LOOP = 1;
    private static final int MAX_LOOP = 1024;
    private TextView mTvShowLoop;
    private SeekBar mSbLoop;
    private LoopResultListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.fragment_loop, container, false);
        mTvShowLoop = dialogView.findViewById(R.id.tvShowSize);
        mSbLoop = dialogView.findViewById(R.id.sbSize);
        mSbLoop.setMax(MAX_LOOP / MIN_LOOP - 1);
        mSbLoop.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                updateLoopText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button buttonPos = dialogView.findViewById(R.id.pos_button);
        buttonPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoopResult(calcSizeByProgress(mSbLoop.getProgress()));
                dismiss();
            }
        });

        Button buttonNeg = dialogView.findViewById(R.id.neg_button);
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If shown as dialog, cancel the dialog (cancel --> dismiss)
                if (getShowsDialog())
                    getDialog().cancel();
                    // If shown as Fragment, dismiss the DialogFragment (remove it from view)
                else
                    dismiss();
            }
        });
        updateLoopText();
        return dialogView;
    }

    private void updateLoopText() {
        int sizeMB = calcSizeByProgress(mSbLoop.getProgress());
        mTvShowLoop.setText(String.format(Locale.US, "%d", sizeMB));
    }

    private int calcSizeByProgress(int progress) {
        return MIN_LOOP * (progress + 1);
    }

    public void setResultListener(LoopResultListener listener) {
        mListener = listener;
    }

    public interface LoopResultListener {
        void onLoopResult(int loop);
    }
}
