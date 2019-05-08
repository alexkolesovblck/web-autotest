package ru.veeam.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.commands.Commands;
import com.google.inject.AbstractModule;

public class SelenideModule extends AbstractModule {

    private final String baseUrl;

    public SelenideModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    protected void configure() {
        Configuration.baseUrl = baseUrl;

        Configuration.screenshots = false;
        Configuration.savePageSource = false;
        Configuration.reopenBrowserOnFail = false;
        Configuration.startMaximized = true;
        Configuration.clickViaJs = Boolean.parseBoolean(System.getProperty("selenide.click-via-js", "false"));
        Configuration.fastSetValue = Boolean.parseBoolean(System.getProperty("selenide.fastSetValue", "false"));
        Configuration.holdBrowserOpen = Boolean.getBoolean(System.getProperty("selenide.holdBrowserOpen", "false"));
        Configuration.pageLoadStrategy = "normal";
        Configuration.pollingInterval = 1000;

        Commands.getInstance().add("shouldMatched", CustomCommands.shouldMatched());
        Commands.getInstance().add("setText", CustomCommands.setText());
        Commands.getInstance().add("pressPaste", CustomCommands.pressPaste());
        Commands.getInstance().add("pressBackspace", CustomCommands.pressBackspace());
        Commands.getInstance().add("clickAfterLoad", CustomCommands.clickAfterLoad());
        Commands.getInstance().add("selectOptionAfterLoad", CustomCommands.selectOptionAfterLoad());
        Commands.getInstance().add("clickJs", CustomCommands.clickJs());
        Commands.getInstance().add("findElementCollection", CustomCommands.findElementCollection());
        Commands.getInstance().add("select", CustomCommands.select());
        Commands.getInstance().add("clickAfterScroll", CustomCommands.clickAfterScroll());
        Commands.getInstance().add("relativeScroll", CustomCommands.relativeScroll());


        Configuration.browser = DefaultWebDriverProvider.class.getCanonicalName();
    }
}