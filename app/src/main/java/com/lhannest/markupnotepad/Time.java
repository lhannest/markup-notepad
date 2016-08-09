package com.lhannest.markupnotepad;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time implements Comparable<Time> {
    private Date date;

    public Time() {
        this.date = new Date();
    }

    public Time(long time) {
        this.date = new Date(time);
    }

    public long get() {
        return this.date.getTime();
    }

    @Override
    public String toString() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    @Override
    public int compareTo(Time other) {
        return this.date.compareTo(other.date);
    }
}
