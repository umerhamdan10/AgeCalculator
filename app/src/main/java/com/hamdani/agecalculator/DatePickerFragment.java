package com.hamdani.agecalculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;


/**
 * Created by UmerHamdan1 on 11/10/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    Context context;
    communicator com;
    String _currentDay, _currentMonth, _currentYear, _birthDay, _birthMonth, _birthYear, _checker;
    public DatePickerFragment(){}

    public DatePickerFragment(communicator communicator) {
        this.com = communicator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            _currentDay = bundle.getString("cDay");
            _currentMonth = bundle.getString("cMonth");
            _currentYear = bundle.getString("cYear");
            _birthDay = bundle.getString("bDay");
            _birthMonth = bundle.getString("bMonth");
            _birthYear = bundle.getString("bYear");
            _checker = bundle.getString("which");

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR);
        int month = c.get(java.util.Calendar.MONTH);
        int day = c.get(java.util.Calendar.DAY_OF_MONTH);
        // Toast.makeText(DatePickerFragment.this, String.valueOf(month), Toast.LENGTH_SHORT).show();

        if (_currentDay != "" && _currentDay != null) {
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, Integer.parseInt(_currentYear), Integer.parseInt(_currentMonth), Integer.parseInt(_currentDay));
        } else if (_birthDay != "" && _birthDay != null) {
            return new DatePickerDialog(getActivity(), this, Integer.parseInt(_birthYear), Integer.parseInt(_birthMonth), Integer.parseInt(_birthDay));


        }

        return null;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if (_checker == "dob") {
            com.respond(i, i1, i2, _checker);
        } else if (_checker == "cd") {
            com.respond(i, i1, i2, _checker);
        }


    }
}
