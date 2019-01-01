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

public class SizeDialogFragment extends DialogFragment {

    private static final int MIN_TEST_SIZE_MB = 8;
    private static final int MAX_TEST_SIZE_MB = 1024;
    private TextView mTvShowSize;
    private SeekBar mSbSize;
    private SizeResultListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.fragment_size, container, false);
        mTvShowSize = dialogView.findViewById(R.id.tvShowSize);
        mSbSize = dialogView.findViewById(R.id.sbSize);
        mSbSize.setMax(MAX_TEST_SIZE_MB / MIN_TEST_SIZE_MB - 1);
        mSbSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                updateSizeText();
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
                mListener.onSizeResult(calcSizeByProgress(mSbSize.getProgress()));
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
        updateSizeText();
        return dialogView;
    }

    private void updateSizeText() {
        int sizeMB = calcSizeByProgress(mSbSize.getProgress());
        mTvShowSize.setText(String.format("%d MB", sizeMB));
    }

    private int calcSizeByProgress(int progress) {
        return MIN_TEST_SIZE_MB * (progress + 1);
    }

    public void setResultListener(SizeResultListener listener) {
        mListener = listener;
    }

    public interface SizeResultListener {
        void onSizeResult(int size);
    }
}
