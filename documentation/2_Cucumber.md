# 2. Cucumber introduction
Before we start with writing your first test in SALUTE, a quick cucumber introduction

Cucumber reads executable specifications written in plain text and validates that the software does what those specifications say. The specifications consist of multiple _examples_, or _scenarios_. For example:
```
Scenario: Breaker guesses a word
  Given the Maker has chosen a word
  When the Breaker makes a guess
  Then the Maker is asked to score
```
Each scenario is a list of _steps_ for Cucumber to work through. Cucumber verifies that the software conforms with the specification and generates a report indicating ✅ success or ❌ failure for each scenario.
In order for Cucumber to understand the scenarios, they must follow some basic syntax rules, called Gherkin.

### What is Gherkin?
Gherkin is a set of grammar rules that makes plain text structured enough for Cucumber to understand. The scenario above is written in Gherkin.
Gherkin serves multiple purposes:
* Unambiguous executable specification
* Automated testing using Cucumber
* Document how the system _actually_ behaves in English
![](2.%20Cucumber%20introduction/single-source-of-truth-256x256.png)

Gherkin documents are stored in .feature text files and are typically versioned in source control alongside the software.

# What are Step Definitions?
 Step definitions connect Gherkin steps to programming code. A step definition carries out the action that should be performed by the step. So step definitions hard-wire the specification to the implementation.
```
|Steps in Gherkin├─matched with─>│Step Definitions├─manipulates─>│System│
```

Step definitions can be written in many programming languages. Here is an example using Java:

```
@When("I navigate to the contact us form")
public void iNavigateToTheContactUsForm() {
    homePage.navigateToContactForm();
}
```