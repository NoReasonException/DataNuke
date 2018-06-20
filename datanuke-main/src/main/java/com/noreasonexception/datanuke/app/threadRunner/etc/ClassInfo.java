package com.noreasonexception.datanuke.app.threadRunner.etc;

import java.util.Date;

/****
 * ClassInfo class
 * this class represents a www node , where we want to retrieve information
 * every node has a an previous point in history , witch the changing value event happened , an interval , and the associated
 * class name
 */
public class ClassInfo implements Comparable<ClassInfo> {
    private  Date   date;
    private  long   interval;
    private  String classname;

    public ClassInfo(Date date,long interval, String classname) {
        this.interval=interval;
        this.date = date;
        this.classname = classname;
    }

    @Override
    public int compareTo(ClassInfo classInfo) {
        return this.getDate().compareTo(classInfo.getDate());
    }

    public Date getDate() {
        return date;
    }

    public long getInterval() {
        return interval;
    }

    public String getClassname() {
        return classname;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
