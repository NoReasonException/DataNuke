package com.noreasonexception.datanuke.app.ValueFilter.interfaces;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostRecentUnixTimestampFileFilter implements FilenameFilter {
    Pattern unixTimeStampCsvPattern =Pattern.compile("\\d*\\.csv",Pattern.DOTALL);
    @Override
    public boolean accept(File dir, String name) {
        return unixTimeStampCsvPattern.matcher(name).matches();

    }
}
