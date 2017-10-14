package daggerok.jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BasicOutcomeThen extends Stage<BasicOutcomeThen> {

  @ProvidedScenarioState
  public static String state;

  public BasicOutcomeThen commonOutcome() {
    state = state.concat("... anymore!");
    assertThat("something wring here...", state, is("this state... will no longer empty... anymore!"));
    return self();
  }
}
