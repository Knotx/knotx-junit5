package io.knotx.junit5;

import io.knotx.junit5.wiremock.ClasspathResourcesMockServer;
import io.vertx.reactivex.core.Vertx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(KnotxExtension.class)
@KnotxApplyConfiguration({"config/example_wiremock_config.conf", "config/modules_config.conf" })
public class KnotxExtensionInheritanceTest {

  @Test
  @DisplayName("Inject port from class level configuration file.")
  void configurationClassScope(@ClasspathResourcesMockServer Integer minimalRequiredService) {
    Assertions.assertEquals(Integer.valueOf(3000), minimalRequiredService);
  }

  @Test
  @DisplayName("Inject port from method level configuration file.")
  @KnotxApplyConfiguration("config/method_level_config.conf")
  void configurationMethodScope(@ClasspathResourcesMockServer Integer mockService) {
    Assertions.assertEquals(new Integer(4001), mockService);
  }

  @Test
  @DisplayName("Inject port from property level configuration file.")
  void configurationParamScope(@KnotxApplyConfiguration("config/param_level_config.conf") Vertx vertx,
      @ClasspathResourcesMockServer Integer queryOnlyRepository) {
    Assertions.assertEquals(new Integer(5001), queryOnlyRepository);
  }

  @Test
  @DisplayName("Inject ports from class/method/param level configuration files.")
  @KnotxApplyConfiguration("config/method_level_config.conf")
  void configurationMixedScope(@KnotxApplyConfiguration("config/param_level_config.conf") Vertx vertx,
      @ClasspathResourcesMockServer Integer minimalRequiredService,
      @ClasspathResourcesMockServer Integer mockService,
      @ClasspathResourcesMockServer Integer queryOnlyRepository) {
    Assertions.assertEquals(Integer.valueOf(3000), minimalRequiredService);
    Assertions.assertEquals(new Integer(4001), mockService);
    Assertions.assertEquals(new Integer(5001), queryOnlyRepository);
  }

  @Test
  @DisplayName("Inject port from param level configuration files when both method and param configurations overrides the class one.")
  @KnotxApplyConfiguration("config/method_level_config.conf")
  void configurationMethodParamScope(
      @KnotxApplyConfiguration("config/param_level_config.conf") Vertx vertx,
      @ClasspathResourcesMockServer Integer allPropertiesService) {
    Assertions.assertEquals(Integer.valueOf(6002), allPropertiesService);
  }

}
