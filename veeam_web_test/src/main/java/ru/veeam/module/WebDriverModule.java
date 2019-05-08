package ru.veeam.module;

import com.google.inject.AbstractModule;
import ru.veeam.config.Config;
import ru.veeam.selenide.SelenideModule;

public class WebDriverModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new SelenideModule(Config.URL));
    }

}
