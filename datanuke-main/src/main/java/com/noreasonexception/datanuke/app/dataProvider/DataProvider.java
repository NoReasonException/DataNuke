package com.noreasonexception.datanuke.app.dataProvider;

import java.nio.Buffer;
import java.util.Optional;

abstract public class DataProvider {
    abstract Optional<Buffer> provide();
}
