/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.veeam.selenide;


import com.codeborne.selenide.Configuration;

import java.util.logging.Logger;

/**
 * Класс - хранилище всех дополнительных конфигурационных констант
 */
public class TaskConfiguration extends Configuration {

    /**
     * Максимальное время ожидания завершения ajax активностей (мс) <br/>
     * Используется в Helper в методах waitWhileAjax <br/>
     */
    public static final int CONFIG_AJAX_WAIT = Integer.parseInt(System.getProperty("common.ajaxWaitMs", "60000"));
    /**
     * Количество успешных проверок на отсутствие ajax активностей подряд, после которого считается, что ajax нет <br/>
     */
    public static final int CONFIG_AJAX_CHECKS = Integer.parseInt(System.getProperty("common.ajaxChecks", "2"));
    /**
     * Папка для скачивания файлов<br/>
     */
    public static final String CONFIG_DEFAULT_DOWNLOAD_PATH = System.getProperty("tempDirectory", ".");
    public final Logger LOGGER = Logger.getLogger(TaskConfiguration.class.getName());
    /**
     * Используемый браузер, валидные значения: <br/>
     * common_chrome<br/>
     * common_firefox<br/>
     */
    public final String CONFIG_BROWSER = System.getProperty("common.browser", "common_chrome");
    /**
     * Максимальное время ожидания завершения скачивания файла (мс) <br/>
     */
    public final long CONFIG_DOWNLOAD_TIMEOUT = Long.parseLong(System.getProperty("common.downloadTimeoutMs", "60000"));

    public static String getConfigDefaultDownloadPath() {
        return CONFIG_DEFAULT_DOWNLOAD_PATH + Thread.currentThread().getId() + "\\";
    }

    public boolean isFirefox() {
        return "common_firefox".equalsIgnoreCase(CONFIG_BROWSER);
    }

    public boolean isChrome() {
        return CONFIG_BROWSER.contains("common_chrome");
    }

    public boolean isChromeIe() {
        return "common_chromeIe".equalsIgnoreCase(CONFIG_BROWSER);
    }

    public boolean isIe() {
        return "common_ie".equalsIgnoreCase(CONFIG_BROWSER);
    }

    public enum RencreditTimeout {
        SMALL_TIMEOUT(6000L),
        MIDDLE_TIMEOUT(30000L),
        LARGE_TIMEOUT(60000L),
        VERY_LARGE_TIMEOUT(180000L);

        Long value;

        RencreditTimeout(Long value) {
            this.value = value;
        }

        public Long getValue() {
            return value;
        }
    }
}