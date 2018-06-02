package com.noreasonexception.datanuke.app.dataProvider;

import java.nio.Buffer;
import java.util.Optional;

public class NetworkDataProvider extends DataProvider {
    @Override
    public Optional<Buffer> provide() {
        return Optional.empty();
    }
}
