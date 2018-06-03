package com.noreasonexception.datanuke.app.threadRunner.etc;

import java.text.DateFormat;
import java.util.Date;

public class DateClassPair implements Comparable<DateClassPair> {
    private  Date   date;
    private  String classname;

    public DateClassPair(Date date, String classname) {

        this.date = date;
        this.classname = classname;
    }

    public DateClassPair(String str,String classname) {
        this(new Date(Long.parseLong(str)),classname);
    }

    @Override
    public int compareTo(DateClassPair dateClassPair) {
        return date.compareTo(dateClassPair.getDate());

    }

    public Date getDate() {
        return date;
    }

    public String getClassname() {
        return classname;
    }
}
