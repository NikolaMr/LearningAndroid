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
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
        mCrimeMap.put(c.getId(), c);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id)
    {
        return mCrimeMap.get(id);
    }

    public void removeCrime(UUID id) {
        Crime c = getCrime(id);
        mCrimeMap.remove(id);
        mCrimes.remove(c);
    }
}
