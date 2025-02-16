package memoranda.api;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class TaigaModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TaigaClient.class).in(Singleton.class);
    }
}
