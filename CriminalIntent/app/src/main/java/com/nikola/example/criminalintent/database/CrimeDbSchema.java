package com.nikola.example.criminalintent.database;

/**
 * Created by Nikola on 30-Aug-18.
 */

public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String POLICE_REQUIRED = "police_required";
            public static final String SUSPECT = "suspect";
        }
    }
}
