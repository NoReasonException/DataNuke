package com.noreasonexception.datanuke.app;

import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws IOException
    {
        new DataNukeDefaultFactory().loadDefaultConfiguration();
    }

}
