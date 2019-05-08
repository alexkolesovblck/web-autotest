package ru.veeam;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.*;
import ru.veeam.module.WebDriverModule;
import ru.veeam.step.BaseSteps;

import javax.inject.Inject;

@Guice(modules = {WebDriverModule.class})
public class PageTest {

    @Inject
    private BaseSteps baseSteps;

    @BeforeMethod
    public void SetUp(){
        baseSteps.openStartPage();
    }

    @AfterMethod
    public void tearDown(){
        Selenide.close();
    }

    @DataProvider
    public Object[][] validVacancy() {
        return new Object[][]{
                {"Canada", "French", "1"},
        };
    }

    @Test(dataProvider = "validVacancy")
    public void findJobWithFilters(String country, String lang, String count){
        baseSteps.selectCountry(country);
        baseSteps.selectLanguages(lang);
        baseSteps.checkResult(count);
    }

}
