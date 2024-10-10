# Test Automation Framework for Fancode City:

### The Automation Framework is designed in a modular fashion to support code-reusability and maximum automated test coverage. Following are the aspects of the framework:
- TestNG Java is used to structure and drive the testcases.
- Parameterization for the tests is done using TestNG.
- Rest Assured is utilized to mimic the API invocation.
- Extent Report Library presents the test execution and results in a structured fashion.

### Test Scenarios and Test Cases:
#### Since the requirement was to test whether the users within the Fancode city have more than half of their tasks completed, the following are the cases that were scoped and automated:
- Validate that we have sufficient users in the Fancode city to execute our tests.
- If we have users within the Fancode city, then validate that each one of them have completed more than 50% of the todo tasks.

We have used a single testng.xml file to encapsulate the above tests within a single class. 
```bash
<test name="FancodeUsersTestModule">
  <classes>
    <class name="Testing.Fancode_City_Assignment.FancodeUsersTest" />
  </classes>
</test> <!-- Test -->
```
### Parameterization of the Tests:
#### For simplicity, the API BaseURI and BasePath for all the endpoints have been provided through testng.xml file
```bash
<parameter name="BaseURI" value="http://jsonplaceholder.typicode.com" />
<parameter name="PostsEndpoint" value="/posts" />
<parameter name="CommentsEndpoint" value="/comments" />
<parameter name="AlbumsEndpoint" value="/albums" />
<parameter name="PhotosEndpoint" value="/photos" />
<parameter name="UsersEndpoint" value="/users" />
<parameter name="TodosEndpoint" value="/todos" />
```

### Steps to execute the Automation suite:
## Pre-requisites:
- JDK 1.8/11 or above.
- IDE: IntelliJ IDEA/Eclipse
- Plugin: TestNG/Git
- IDE default Maven/Locally installed Maven.

Note: The default branch is "master" and the code also resides in "master".

## Steps:
1. Clone the project in cmd using:  git clone https://github.com/GauravDevc123/Fancode_City_Assignment.git 
2. Switch to master using: git checkout master
3. Import the repository project in any IDE (IntelliJ IDEA/Eclipse)
4. Make sure the project is imported correctly.
5. If there are issues with maven snapshots, Right click on Project -> Go to Maven -> Update Project.
6. Go to CMD/Terminal and switch to the location of the project
7. Run mvn clean test
8. Alternatively, you can directly run the project from IDE by right clicking on testng.xml -> Run As -> 1 TestNG Suite.
9. Verify the test execution result in Console and Results of running suite.
10. View the report by going to project hierarchy -> test-output -> extentReport.html (Open the file in a browser)
