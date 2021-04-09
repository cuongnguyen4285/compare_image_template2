package features;

import static pages.HomePage.DAN_TRI_LOGO;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Steps;

import constants.Constants;
import drivermanager.DriverFactory;
import pages.HomePage;
import utils.OpenCVComparisonUtil;
import utils.ShutterBugImageUtil;

@RunWith(SerenityRunner.class)
public class WhenMatchingImage extends Hook {
//    @Steps
//    HomePage homePage;

    @Test
    public void whenMatchingImage_thenShouldSeeCompareResult() {
        String template = "src/main/resources/images/matchimage/dan_tri_logo.png";
        String source = "src/main/resources/images/matchimage/dantri.png";

//        homePage.navigateTo(
//                "https://dantri.com.vn/nhip-song-tre/thieu-nu-xinh-dep-doi-non-la-khoe-sac-voi-hoa-ban-20190214111529691.htm#&gid=1&pid=1");
//
//        BufferedImage expectedScreenshot = ShutterBugImageUtil
//                .takeScreenshot(DriverFactory.getWebDriver(),
//                                       Constants.ACTUAL_IMAGE_FILE_PATH, "dan_tri");

        OpenCVComparisonUtil.matchingImage(source, template);
        float coord[] = OpenCVComparisonUtil.getTemplateImageCoord(source, template);

    }

}
