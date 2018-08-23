package com.nikola.example.criminalintent;

import android.support.v4.app.Fragment;

import com.nikola.example.criminalintent.SingleFragmentActivity;

/**
 * Created by Nikola on 23-Aug-18.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
