package com.noreasonexception.loadable.base.error;

import java.io.IOException;

public class ConvertionSourceToTextException extends IOException {
    public ConvertionSourceToTextException(Class sourceThrown,Throwable cause) {
        super(sourceThrown.getName()+ " Was unable to convert their source to text",cause);
    }
}
