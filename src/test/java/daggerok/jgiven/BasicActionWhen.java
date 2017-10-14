package daggerok.jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

public class BasicActionWhen extends Stage<BasicActionWhen> {

  @ProvidedScenarioState
  public static String state;

  public BasicActionWhen commonAction() {
    state = state.concat("... will no longer empty");
    return self();
  }
}
