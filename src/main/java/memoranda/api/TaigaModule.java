package memoranda.api;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import memoranda.util.TaigaJsonData;

public class TaigaModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TaigaClient.class).in(Singleton.class);
        bind(TaigaJsonData.class).in(Singleton.class);
    }
}
