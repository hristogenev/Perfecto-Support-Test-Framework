package io.perfecto.tests.utilities;

import io.perfecto.utilities.CommonUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommonUtilsTests {
  @Test
  public void perfectoRepoPathShouldBeReplacedWithLocalRepoPath() {
    String localRepoPath = "/Users/hgenev/repo/";
    List<String> remoteRepoPaths = Arrays.asList(
        "PUBLIC:testapp.apk",
        "PUBLIC:/testapp.apk",
        "PRIVATE:testapp.apk",
        "PRIVATE:/testapp.apk",
        "GROUP:testapp.apk",
        "GROUP:/testapp.apk"
    );

    for (String remoteRepoPath: remoteRepoPaths) {
      String result = CommonUtils.convertToLocalRepoPath(remoteRepoPath, localRepoPath);
      assertEquals("/Users/hgenev/repo/testapp.apk", result);
    }

  }
}
