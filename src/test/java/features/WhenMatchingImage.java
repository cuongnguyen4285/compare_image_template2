package features;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.serenitybdd.junit.runners.SerenityRunner;
import utils.OpenCVComparisonUtil;


@RunWith(SerenityRunner.class)
public class WhenMatchingImage extends Hook {

    @Test
    public void whenMatchingImage_thenShouldSeeCompareResult() {
        String template = "src/main/resources/images/matchimage/sub_image1.png";
        String source = "src/main/resources/images/matchimage/image1.png";
        OpenCVComparisonUtil.matchingImage(source, template);
    }

}
