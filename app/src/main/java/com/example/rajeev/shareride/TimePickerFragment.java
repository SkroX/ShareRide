package com.example.rajeev.shareride;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;


import java.util.Calendar;

/**
 * Created by rajeev on 4/6/18.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), R.style.DateAndTimePicker,this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Button b = getActivity().findViewById(R.id.time);
        String stringOfMinute = "";
        String stringOfHour = "";
        if(minute < 10)
            stringOfMinute = "0" + minute ;
        else
            stringOfMinute = "" + minute;
        if(hourOfDay < 10)
            stringOfHour = "0" + hourOfDay;
        else
            stringOfHour = "" + hourOfDay;

        b.setText(stringOfHour + ":" + stringOfMinute);
    }
}
