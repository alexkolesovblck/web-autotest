package ru.veeam.selenide;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.*;

import java.io.Serializable;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public abstract class ExBy extends By {
    protected static IExpectantForFindBy expectant;
    protected static boolean waitByDefault;
    protected static By[] defaultFrames;
    protected String originalValue = "";
    protected String description = "";
    protected boolean isWait = waitByDefault;
    By[] frames = ExBy.defaultFrames;

    /**
     * Устанавливает то, метод какого класса вызывать когда необходимо ждать ДО и ПОСЛЕ переключения фреймов
     *
     * @param expectant класс реализующий IExpectantForFindBy
     */
    public static void useWait(IExpectantForFindBy expectant) {
        ExBy.expectant = expectant;
    }

    /**
     * Устанавливает, использовать ли ожидание всегда по умолчанию или нет
     */
    public static void waitByDefault(boolean waitByDefault) {
        ExBy.waitByDefault = waitByDefault;
    }

    /**
     * Устанавливает цепочку фреймов по умолчанию
     *
     * @param frames локаторый фреймов
     */
    public static void defaultFrames(By... frames) {
        defaultFrames = frames;
    }

    /**
     * @param id The value of the "id" attribute to search for
     * @return a By which locates elements by the value of the "id" attribute.
     */
    public static ById id(final String id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Cannot find elements with a null id attribute.");

        return id(id, "");
    }

    public static ById id(final String id, String description) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Cannot find elements with a null id attribute.");

        return new ById(id, description);
    }

    /**
     * @param linkText The exact text to match against
     * @return a By which locates A elements by the exact text it displays
     */
    public static ByLinkText linkText(final String linkText) {
        if (linkText == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when link text is null.");

        return linkText(linkText, "");
    }

    public static ByLinkText linkText(final String linkText, String description) {
        if (linkText == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when link text is null.");

        return new ByLinkText(linkText, description);
    }

    /**
     * @param linkText The text to match against
     * @return a By which locates A elements that contain the given link text
     */
    public static ByPartialLinkText partialLinkText(final String linkText) {
        if (linkText == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when link text is null.");

        return partialLinkText(linkText, "");
    }

    public static ByPartialLinkText partialLinkText(final String linkText, String description) {
        if (linkText == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when link text is null.");

        return new ByPartialLinkText(linkText, description);
    }

    /**
     * @param name The value of the "name" attribute to search for
     * @return a By which locates elements by the value of the "name" attribute.
     */
    public static ByName name(final String name) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when name text is null.");

        return name(name, "");
    }

    public static ByName name(final String name, String description) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when name text is null.");

        return new ByName(name, description);
    }

    /**
     * @param name The element's tagName
     * @return a By which locates elements by their tag name
     */
    public static ByTagName tagName(final String name) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when name tag name is null.");

        return tagName(name, "");
    }

    public static ByTagName tagName(final String name, String description) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when name tag name is null.");

        return new ByTagName(name, description);
    }

    /**
     * @param xpathExpression The xpath to use
     * @return a By which locates elements via XPath
     */
    public static ByXPath xpath(final String xpathExpression) {
        if (xpathExpression == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the XPath expression is null.");

        return xpath(xpathExpression, "");
    }

    public static ByXPath xpath(final String xpathExpression, String description) {
        if (xpathExpression == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the XPath expression is null.");

        return new ByXPath(xpathExpression, description);
    }

    /**
     * Finds elements based on the value of the "class" attribute. If an element has many classes then
     * this will match against each of them. For example if the value is "one two onone", then the
     * following "className"s will match: "one" and "two"
     *
     * @param className The value of the "class" attribute to search for
     * @return a By which locates elements by the value of the "class" attribute.
     */
    public static ByClassName className(final String className) {
        if (className == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the class name expression is null.");
        return className(className, "");
    }

    public static ByClassName className(final String className, String description) {
        if (className == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the class name expression is null.");

        return new ByClassName(className, description);
    }

    /**
     * Finds elements via the driver's underlying W3 Selector engine. If the browser does not
     * implement the Selector API, a best effort is made to emulate the API. In this case, we strive
     * for at least CSS2 support, but offer no guarantees.
     */
    public static ByCssSelector cssSelector(final String selector) {
        if (selector == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the selector is null");

        return cssSelector(selector, "");
    }

    public static ByCssSelector cssSelector(final String selector, String description) {
        if (selector == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the selector is null");

        return new ByCssSelector(selector, description);
    }

    public abstract WebElement findElement(SearchContext context);

    /**
     * Конструирует копию локатора, предварительно заменив все {0} ... {n} конструкции на параметры из params
     *
     * @param params параметры для локатора (в локаторе места под параметры должны быть вида {0})
     * @return сам себя
     */
    public abstract ExBy withParams(Object... params);

    /**
     * @param description user-friendly описание локатора
     * @return сам себя
     */
    public ExBy as(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return описание локатора
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Вызов метода определяет, что перед поиском элемента нужно перейти во фрейм
     *
     * @param framesId локаторы фреймов из дерева фреймов, в последнем из которых находится элемент
     * @return сам себя
     */
    public ExBy inFrame(By... framesId) {
        this.frames = framesId;
        return this;
    }

    /**
     * Вызов метода определяет, что перед поиском элемента нужно вернуться в корень страницы
     *
     * @return сам себя
     */
    public ExBy inRoot() {
        this.frames = new By[]{};
        return this;
    }

    /**
     * Устанавливает флаг, который запрещает выполнять ожидание ДО и ПОСЛЕ переключения фреймов
     * <br/>
     * Актуально, только если до этого был определен класс-ожидающий ExBy.useWait(new SomeClassWithWaitMethod());
     *
     * @return сам себя
     */
    public ExBy noWait() {
        isWait = false;
        return this;
    }

    /**
     * Устанавливает флаг, который заставит исполняться метод waitWhileAjax ДО и ПОСЛЕ переключения фреймов
     * <br/>
     * Актуально, только если до этого был определен класс-ожидающий ExBy.useWait(new SomeClassWithWaitMethod());
     *
     * @return сам себя
     */
    public ExBy withWait() {
        isWait = true;
        return this;
    }

    protected ExBy withWait(boolean isWait) {
        this.isWait = isWait;
        return this;
    }

    protected void switchToFrameAndWaitAjax() {
        try {
            if (frames != null) {
                if (frames.length > 0) {
                    switchTo().defaultContent();
                    for (int i = 0; i < frames.length; i++) {
                        Selenide.switchTo().frame($(frames[i]));
                    }
                } else {
                    switchTo().defaultContent();
                }
            }

            if (expectant != null && isWait) expectant.waitAjaxFinish(this);
        } catch (UnhandledAlertException ex) {
        } catch (Throwable ex2) {
            if (ex2.getCause() != null
                    && ex2.getCause().getMessage() != null
                    && ex2.getCause().getMessage().contains("UnhandledAlertException")) return;
        }
    }

    protected String getParametized(Object[] param) {
        String expression = originalValue;
        for (int i = 0; i < param.length; i++) {
            if (expression.indexOf("{" + i + "}") < 0) {
                throw new IllegalArgumentException("Ошибка в параметризованном локаторе: " + toString());
            }
            expression = expression.replace("{" + i + "}", param[i].toString());
        }
        return expression;
    }


    public static interface IExpectantForFindBy {
        public void waitAjaxFinish(ExBy by);
    }

    public static class ById extends ExBy implements Serializable {

        private static final long serialVersionUID = 5341968046120372169L;

        private final String id;

        public ById(String id) {
            this.id = id;
            originalValue = id;
        }

        public ById(String id, String description) {
            this(id);
            super.as(description);
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsById)
                return ((FindsById) context).findElementsById(id);
            return ((FindsByXPath) context).findElementsByXPath(".//*[@id = '" + id
                    + "']");
        }

        @Override
        public WebElement findElement(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsById)
                return ((FindsById) context).findElementById(id);
            return ((FindsByXPath) context).findElementByXPath(".//*[@id = '" + id
                    + "']");
        }

        @Override
        public String toString() {
            return description.isEmpty() ? ("By.id: " + id) : description;
        }

        @Override
        public ExBy withParams(Object... params) {
            return new ExBy.ById(super.getParametized(params)).inFrame(frames).as(description).withWait(isWait);
        }
    }

    public static class ByLinkText extends ExBy implements Serializable {

        private static final long serialVersionUID = 1967414585359739708L;

        private final String linkText;

        public ByLinkText(String linkText) {
            this.linkText = linkText;
            originalValue = linkText;
        }

        public ByLinkText(String linkText, String description) {
            this(linkText);
            super.as(description);
        }


        @Override
        public List<WebElement> findElements(SearchContext context) {
            switchToFrameAndWaitAjax();

            return ((FindsByLinkText) context).findElementsByLinkText(linkText);
        }

        @Override
        public WebElement findElement(SearchContext context) {
            switchToFrameAndWaitAjax();

            return ((FindsByLinkText) context).findElementByLinkText(linkText);
        }

        @Override
        public String toString() {
            return description.isEmpty() ? ("By.linkText: " + linkText) : description;
        }

        @Override
        public ExBy withParams(Object... params) {
            return new ExBy.ByLinkText(super.getParametized(params)).inFrame(frames).as(description).withWait(isWait);
        }
    }

    public static class ByPartialLinkText extends ExBy implements Serializable {

        private static final long serialVersionUID = 1163955344140679054L;

        private final String linkText;

        public ByPartialLinkText(String linkText) {
            this.linkText = linkText;
            originalValue = linkText;
        }

        public ByPartialLinkText(String linkText, String description) {
            this(linkText);
            super.as(description);
        }


        @Override
        public List<WebElement> findElements(SearchContext context) {
            switchToFrameAndWaitAjax();

            return ((FindsByLinkText) context)
                    .findElementsByPartialLinkText(linkText);
        }

        @Override
        public WebElement findElement(SearchContext context) {
            switchToFrameAndWaitAjax();

            return ((FindsByLinkText) context).findElementByPartialLinkText(linkText);
        }

        @Override
        public String toString() {
            return description.isEmpty() ? ("By.partialLinkText: " + linkText) : description;
        }

        @Override
        public ExBy withParams(Object... params) {
            return new ExBy.ByPartialLinkText(super
                    .getParametized(params))
                    .inFrame(frames)
                    .as(description)
                    .withWait(isWait);
        }
    }

    public static class ByName extends ExBy implements Serializable {

        private static final long serialVersionUID = 376317282960469555L;

        private final String name;

        public ByName(String name) {
            this.name = name;
            originalValue = name;
        }

        public ByName(String name, String description) {
            this(name);
            super.as(description);
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsByName)
                return ((FindsByName) context).findElementsByName(name);
            return ((FindsByXPath) context).findElementsByXPath(".//*[@name = '"
                    + name + "']");
        }

        @Override
        public WebElement findElement(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsByName)
                return ((FindsByName) context).findElementByName(name);
            return ((FindsByXPath) context).findElementByXPath(".//*[@name = '"
                    + name + "']");
        }

        @Override
        public String toString() {
            return description.isEmpty() ? ("By.name: " + name) : description;
        }

        @Override
        public ExBy withParams(Object... params) {
            return new ExBy.ByName(super.getParametized(params)).inFrame(frames).as(description).withWait(isWait);
        }
    }

    public static class ByTagName extends ExBy implements Serializable {

        private static final long serialVersionUID = 4699295846984948351L;

        private final String name;

        public ByTagName(String name) {
            this.name = name;
            originalValue = name;
        }

        public ByTagName(String name, String description) {
            this(name);
            super.as(description);
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsByTagName)
                return ((FindsByTagName) context).findElementsByTagName(name);
            return ((FindsByXPath) context).findElementsByXPath(".//" + name);
        }

        @Override
        public WebElement findElement(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsByTagName)
                return ((FindsByTagName) context).findElementByTagName(name);
            return ((FindsByXPath) context).findElementByXPath(".//" + name);
        }

        @Override
        public String toString() {
            return description.isEmpty() ? ("By.tagName: " + name) : description;
        }

        @Override
        public ExBy withParams(Object... params) {
            return new ExBy.ByTagName(super.getParametized(params)).inFrame(frames).as(description).withWait(isWait);
        }
    }

    public static class ByXPath extends ExBy implements Serializable {

        private static final long serialVersionUID = -6727228887685051584L;

        private final String xpathExpression;

        public ByXPath(String xpathExpression) {
            this.xpathExpression = xpathExpression;
            originalValue = xpathExpression;
        }

        public ByXPath(String xpathExpression, String description) {
            this(xpathExpression);
            super.as(description);
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            switchToFrameAndWaitAjax();

            return ((FindsByXPath) context).findElementsByXPath(xpathExpression);
        }

        @Override
        public WebElement findElement(SearchContext context) {
            switchToFrameAndWaitAjax();

            return ((FindsByXPath) context).findElementByXPath(xpathExpression);
        }

        @Override
        public String toString() {
            return description.isEmpty() ? ("By.xpath: " + xpathExpression) : description;
        }

        @Override
        public ExBy withParams(Object... params) {
            return new ExBy.ByXPath(super.getParametized(params)).inFrame(frames).as(description).withWait(isWait);
        }
    }

    public static class ByClassName extends ExBy implements Serializable {

        private static final long serialVersionUID = -8737882849130394673L;

        private final String className;

        public ByClassName(String className) {
            this.className = className;
            originalValue = className;
        }

        public ByClassName(String className, String description) {
            this(className);
            super.as(description);
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsByClassName)
                return ((FindsByClassName) context).findElementsByClassName(className);
            return ((FindsByXPath) context).findElementsByXPath(".//*["
                    + containingWord("class", className) + "]");
        }

        @Override
        public WebElement findElement(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsByClassName)
                return ((FindsByClassName) context).findElementByClassName(className);
            return ((FindsByXPath) context).findElementByXPath(".//*["
                    + containingWord("class", className) + "]");
        }

        /**
         * Generates a partial xpath expression that matches an element whose specified attribute
         * contains the given CSS word. So to match &lt;div class='foo bar'&gt; you would say "//div[" +
         * containingWord("class", "foo") + "]".
         *
         * @param attribute name
         * @param word      name
         * @return XPath fragment
         */
        private String containingWord(String attribute, String word) {
            return "contains(concat(' ',normalize-space(@" + attribute + "),' '),' "
                    + word + " ')";
        }

        @Override
        public String toString() {
            return description.isEmpty() ? ("By.className: " + className) : description;
        }

        @Override
        public ExBy withParams(Object... params) {
            return new ExBy.ByClassName(super.getParametized(params)).inFrame(frames).as(description).withWait(isWait);
        }
    }

    public static class ByCssSelector extends ExBy implements Serializable {

        private static final long serialVersionUID = -3910258723099459239L;

        private String selector;

        public ByCssSelector(String selector) {
            this.selector = selector;
            originalValue = selector;
        }

        public ByCssSelector(String selector, String description) {
            this(selector);
            super.as(description);
        }

        @Override
        public WebElement findElement(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsByCssSelector) {
                return ((FindsByCssSelector) context)
                        .findElementByCssSelector(selector);
            }

            throw new WebDriverException(
                    "Driver does not support finding an element by selector: " + selector);
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            switchToFrameAndWaitAjax();

            if (context instanceof FindsByCssSelector) {
                return ((FindsByCssSelector) context)
                        .findElementsByCssSelector(selector);
            }

            throw new WebDriverException(
                    "Driver does not support finding elements by selector: " + selector);
        }

        @Override
        public String toString() {
            return description.isEmpty() ? ("By.cssSelector: " + selector) : description;
        }

        @Override
        public ExBy withParams(Object... params) {
            return new ExBy.ByCssSelector(super
                    .getParametized(params))
                    .inFrame(frames)
                    .as(description)
                    .withWait(isWait);
        }
    }
}
