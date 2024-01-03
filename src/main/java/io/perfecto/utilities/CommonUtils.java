package io.perfecto.utilities;

import java.nio.file.Path;
import java.util.Arrays;

public class CommonUtils {


  public static String convertToLocalRepoPath(String remoteRepoPath, String localRepoPath) {
    if (localRepoPath == null || remoteRepoPath == null)
      return remoteRepoPath;

    String[] parts = remoteRepoPath.split(":");
    if (parts.length == 0)
      return remoteRepoPath;

    if (parts[0].equals("PUBLIC") || parts[0].equals("PRIVATE") || parts[0].equals("GROUP")) {
      return Path.of(localRepoPath, Arrays.copyOfRange(parts, 1, parts.length)).toString();
    }

    return remoteRepoPath;
  }
}
