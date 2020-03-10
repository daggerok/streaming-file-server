package daggerok.jgiven;

import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

public class BasicJGivenTestScenario extends ScenarioTest<BasicStateGiven, BasicActionWhen, BasicOutcomeThen> {

  @Test
  public void common_test_scenario() {
    given().commonState();
    when().commonAction();
    then().commonOutcome();
  }
}
