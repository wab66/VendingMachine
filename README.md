# VendingMachine
# PRACTICAL EXERCISE for interviews
* Write a program to design Vending Machine using your 'favourite language' with all possible tests
* Accepts coins of 1,5,10,25 Cents i.e. penny, nickel, dime, and quarter.
* Allow user to select products Coke(25), Pepsi(35), Soda(45)
* Allow user to take refund by cancelling the request.
* Return selected product and remaining change if any.
* Allow reset operation for vending machine supplier.


# API automation
* As a biker I would like to know the exact location of city bikes around the world in a given application.
* Endpoint: http://api.citybik.es/v2/networks
* Auth: No
* HTTPS: No
* Understands how the API works.
* Create some BDD scenarios and automate them using Java to test the API
*Test this particular scenario: “As a user I want to verify that the city Frankfurt is in Germany and return their corresponded latitude and longitude”

# Criteria:
* The code should be runnable from IDE or console (console is best!!!!)
* Include all dependencies in your project and include a Readme file explaining how to run your tests.
* It is preferred that the solutions are provided using the Java platform. But if your skillset does not currently include Java - then add that note and use your preferred coding model (python/perl/C#/C++ etc)
* It is absolutely BEST if the code/projects are put on github so we can fork/ssh/zip/download whatever - please dont send up the zip or tar (security filters, virus etc)
 

# Solution:
## Notes:
* The java Vending Machine app, I admit i did copy quite a bit and then changed it around and fixed a few bugs.  I'm not a java application programmer ( I think I know enough for test automation) so this would have taken me a while to do.  I understand how it has been done.  I may have not done the Inventory as two ENUMS, but rather as classes.  I thought it might be better for being able to add new coin types and new products to the machine (but there might be a better way).  The way it has been done you would need to go in and add extra ENUMS to the ENUM classes. 
* I had a bit of trouble getting the source control to work with GITHub and understanding how it all worked together, as I have never used before.  I think I have managed to understand now.
* My tests are running fine from within IntelliJ. I can run the individual Scenarios or run the entire Feature
* I have only used the standard reporting tool and just create a html version as my report.  I will include one in the repo as an example
* I could not for the life of me get this to run in maven or from the command line using maven. I created my test runner, but when I ran 'mvn test' it all seems to work, i get a green tick next to Test in the Run tab.  But I I'm unable to see the actual results.
    * But, when I run the Test Runner directly from within InteliJ I get an error message: java.lang.NoSuchMethodError: io.cucumber.core.options.RuntimeOptionsBuilder.enablePublishPlugin().  I have raised this as an issue on StackOverflow to try and get some help with it.  But no luck yet.  I will wait in hope!
* Unfortunately I did not complete the API section. I have never done this side before.  It is something I have been meaning to look into.  I have done a lot of reading up on it and I will continue to try and complete the task, just for my own practice.  I have down loaded Postman and have some sort of a basic API Framework I have copied to try and get things going.

# Next Steps:
* As I said in the Solution Notes, I may have changed the Java app to work differently for the Inventory ENUMS
* Although I have tried to clean up my Step code, I would have liked to refactor it a bit more.
* The last go I had at refactoring, I think I was a bit too exuberant with the changes and I introduced a bug that took a while to fix.
    * It was very simple in the end the old '==' instead of '!=', so i figured i would just stick and get it finished and working
* I created 2 extra Features one for Maintenance and one for the Owner as extra Domains that might be used.
    * although I did not create anything to run, it was just a place to dump some ideas in of how it might work
        * these sorts of thing I would use to go back to the business to ask questions about  
    * The Maintenace feature would check any errors, logs and refill coins/products etc. 
    * The Owner Feature would be more about reporting, sales, breakdowns, stocktake and product usage info
        * Do we need to remove SODA (yes!) and replace with a more popular drink etc..
        * Do we need to remove the PENNY as it is filling up too quickly and causing too much maintenance, those sorts of things  
* On the Features and Scenarios: I have used Scenario Outlines and Examples to repeat the same scenario many times with different combinations.  This allows a greater test coverage by using all the inventory in each test. The weigh up is it can look a little confusing at times, so it might need cleaning up to make it read better.
    * I tried to keep the data to a minimum, but also wanted to make sure it would be the right coverage.  So I would like to review this a little more to make sure it is correct
    * I like having the data right there as I think it helps to tell a better story about what is happening and hopefully people can understand why that data is there
* I would have liked to work on the Dependency Injection a little more
* It is all about weighing up how good your tests and code are versus getting this done quickly. It's great to be able to explain what you have done and why and what you think you can improve.

# Setting up:
## Project
* Clone this repository to your local machine in the usual way e.g. using HTTPS:
    > git clone https://github.com/wab66/VendingMachine/

## Maven build
I have included my POM file which you could copy (feel free to fix the issue of not being able to run tests in Maven!)

## IDE option
1. Open the project from your IDE - e.g. IntellijJ IDEA
2. Navigate to /src/test/resources/features/VendingMachinePurchasesTest.feature
3. There are two main ways to run the tests:
    * Run test from the Project Explorer
    * Right click on the feature VendingMachinePurchases
    * Select Run Feature (green play button)
4. Open the Feature file
    * Inside the line number grid on the left you can click on the green play button, from the Feature heading - this will run the entire feature
    * Or if you would like to run an individual Scenario instead you can scroll down to the required scenario and choose the green play button associated to that scenario

## View the test results
* From the 4. Run tab that has been opened by IntelliJ in the left-hand panel, there will be a Test Results section
  * In this section if you click on the 'Export Test Results' icon (that looks like an L with an arrow pointing up), which is in the top right section
  * This will generate a HTML report in the root of your project: Test Results - Feature_VendingMachinePurchases.html
    * Double click the report and it will be opened in the main window
      * when you mouse over the html code you will get a floating list of Browser icons, which you can click to open the html file in a browser
 
 * NB: Just on a side note - I had to work pretty hard to get this far, but i have to say I did really enjoy it and learned a lot.  Thank you for the opportunity and sorry for the lateness.  I look forward to hearing back from you and poosibly discussing my project.
 
 
 
