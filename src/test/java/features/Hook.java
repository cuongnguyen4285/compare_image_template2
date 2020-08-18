package features;

import org.junit.After;
import org.junit.Before;
import org.opencv.core.Core;

import net.thucydides.core.webdriver.SerenityWebdriverManager;

import nu.pattern.OpenCV;

public class Hook {

    static {
        OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("=============Start OpeningCV==========");
    }

    @Before
    public void beforeEachTest() {

    }

    @After
    public void afterEachTest() {
        SerenityWebdriverManager.inThisTestThread().closeAllDrivers();
    }
}
