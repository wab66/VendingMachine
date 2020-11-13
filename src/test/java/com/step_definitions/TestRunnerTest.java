package com.step_definitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(value = Cucumber.class)
@CucumberOptions(
        features = {},
        glue = {},
        plugin= { "pretty", "html:target/report" },
        monochrome = true
)

public class TestRunnerTest {
    //System.out.println("ran TestrunnerTest");
}
