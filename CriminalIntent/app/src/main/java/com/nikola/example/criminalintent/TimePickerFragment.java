package com.nikola.example.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by Nikola on 28-Aug-18.
 */

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_HOUR = "com.nikola.example.criminalintent.hour";
    public static final String EXTRA_MINUTE = "com.nikola.example.criminalintent.minute";

    private static final String ARG_HOUR = "hour";
    private static final String ARG_MINUTE = "minute";

    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(int hour, int minute) {
        Bundle args = new Bundle();
        args.putInt(ARG_HOUR, hour);
        args.putInt(ARG_MINUTE, minute);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = getArguments().getInt(ARG_HOUR);
        int minute = getArguments().getInt(ARG_MINUTE);

        View view = (View) LayoutInflater
                .from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_picker);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        } else {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Time of crime:")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int hour;
                                int minute;

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    hour = mTimePicker.getHour();
                                    minute = mTimePicker.getMinute();
                                } else {
                                    hour = mTimePicker.getCurrentHour();
                                    minute = mTimePicker.getCurrentMinute();
                                }

                                sendResult(Activity.RESULT_OK, hour, minute);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, int hour, int minute) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MINUTE, minute);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
