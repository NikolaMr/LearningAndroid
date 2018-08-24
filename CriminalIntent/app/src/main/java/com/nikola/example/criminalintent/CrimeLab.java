package com.nikola.example.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Nikola on 22-Aug-18.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;
    private Map<UUID, Crime> mCrimeMap = new HashMap<>();

    public static CrimeLab get(Context context) {
        return sCrimeLab = sCrimeLab == null ? new CrimeLab(context) : sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

        Random r = new Random();

        for (int i = 0; i < 100; ++i)
        {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(r.nextBoolean());
            crime.setRequiresPolice(r.nextBoolean());
            mCrimes.add(crime);
            mCrimeMap.put(crime.getId(), crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id)
    {
        return mCrimeMap.get(id);
    }
}
