package daggerok.jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

public class BasicStateGiven extends Stage<BasicStateGiven> {

  @ProvidedScenarioState
  public static String state;

  BasicStateGiven commonState() {
    state = "this state";
    return self();
  }
}
