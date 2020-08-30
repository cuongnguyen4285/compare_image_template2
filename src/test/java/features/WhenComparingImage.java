package features;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

import constants.Constants;
import drivermanager.DriverFactory;
import pages.HomePage;
import utils.OpenCVComparisonUtil;
import utils.ShutterBugImageUtil;

@RunWith(SerenityRunner.class)
public class WhenComparingImage extends Hook {

    @Steps
    HomePage homePage;

    @Test
    public void whenComparingImage_thenShouldSeeCompareResult() {

        homePage.navigateTo(
                "https://dantri.com.vn/nhip-song-tre/thieu-nu-xinh-dep-doi-non-la-khoe-sac-voi-hoa-ban-20190214111529691.htm#&gid=1&pid=1");

        BufferedImage expectedScreenshot = ShutterBugImageUtil
                .takeScreenshot(DriverFactory.getWebDriver(), Constants.EXPECTED_IMAGE_FILE_PATH, "expectedimage");

        homePage.navigateTo(
                "https://dantri.com.vn/nhip-song-tre/thieu-nu-xinh-dep-doi-non-la-khoe-sac-voi-hoa-ban-20190214111529691.htm#&gid=1&pid=1");

        BufferedImage actualScreenshot = ShutterBugImageUtil
                .takeScreenshot(DriverFactory.getWebDriver(), Constants.ACTUAL_IMAGE_FILE_PATH, "actualimage");

        assertThat(OpenCVComparisonUtil.compare_image(
                actualScreenshot, expectedScreenshot), is(true));
    }
}
