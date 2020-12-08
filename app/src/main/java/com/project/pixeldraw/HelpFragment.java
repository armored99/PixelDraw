package com.project.pixeldraw;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class HelpFragment extends DialogFragment {

    // Host activity must implement
    public interface OnSubjectEnteredListener {
        void onSubjectEntered(String subject);
    }

    private OnSubjectEnteredListener mListener;

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {

        final TextView mText = new TextView(getActivity());
        mText.setText(R.string.info);
        mText.setTextColor(getResources().getColor(R.color.colorPrimary));

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.help)
                .setView(mText)
                .setPositiveButton(R.string.ok, null)
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}