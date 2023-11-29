package io.perfecto.utilities.device;

import com.google.common.collect.ImmutableMap;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
import io.perfecto.utilities.scripts.mobile.Handset;

import java.util.Collections;
import java.util.Map;

public class Device {

  private final ExtendedMobileDriver driver;

  public Device(ExtendedMobileDriver driver) {
    this.driver = driver;
  }

  /**
   * For iOS and Android devices, the device is unlocked and returned to its default rotate orientation.
   * For example, iPhone devices are returned to portrait mode and iPad devices to landscape mode.
   * Use this function at the beginning of a script, to ensure a known starting point for the user.
   * @param target - Specify what should be reset - menu navigation state, device orientation, or both.
   * all (default) - Reset both location in the navigation tree (device menus to IDLE state) and device state to default (rotate / flip state)
   * menu - Reset the location in the navigation tree
   * position - Reset the device state (rotate / flip) to default position
   */
  public void ready(String target) {
    driver.executeScript(Handset.READY, ImmutableMap.of("target", target));
  }

  public void ready() {
    driver.executeScript(Handset.READY, ImmutableMap.of("target", "all"));
  }

  public void reboot() {
    driver.executeScript(Handset.REBOOT, Map.of());
  }

  /**
   * The device will not be rebooted; it will be disconnected and reconnected to the Perfecto data center, and returned unlocked.
   * Recovery will take a few minutes to complete. Use this function at the end of your script instance of the device in error.
   * This is useful for your next run.
   * Important: Devices continuously in error state should be checked. In such cases, contact your Perfecto system administrator for support.
   * Restriction: When using this command in Automation scripts, the execution report video may not be available.
   */
  public void recover() {
    driver.executeScript(Handset.RECOVER, Map.of());
  }

  /**
   * Use to run an adb shell command on an Android device.
   * @param command A key-value pair of string to object, where the first part is the command and the second part is the adb shell command.
   */
  public void shell(String command) {
    driver.executeScript(Handset.SHELL, ImmutableMap.of("command", command));
  }
}
