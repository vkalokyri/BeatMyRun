BEAT MY RUN
INTRODUCTION
---------

This directory contains the full implementation of the Beat My Run application for the Android platform, demonstrating the basic facilities that application will use.


CONFIGURE
--------

The project directory is ready for use from Android Studio. 
You can download the Android Studio from https ://developer.android.com/sdk/ and follow instructions on the setup wizard 
Install the Android SDK and Android 5.0(API 23) SDK platform (Minimum SDK version 19 and Target SDK version 23)
To build the application go to ‘Open an existing Android Studio project and browse through the directory.
Select the downloaded source-> BeatMyRun-master and choose HelloWorld
The Android project should then be created, and you can browse the source to find all the java files.
Project->HelloWorld->app->src->main->java or
Android->app->ja
Get key from Google API and YouTube API for the Login and streaming music from 
https://developers.google.com/api-client-library/php/guide/aaa_apikeys#acquiring-an-api-key and
https://developers.google.com/youtube/v3/getting-started
Include your Android and Browser API keys in the file LoginActivity.java

LIBRARIES INCLUDED
-----------
In order to build/run our application the following softwares should be downloaded and are included in the HelloWorld->app->libs

achartengine-1.1.0.jar
google-api-client-1.20.0.jar
google-api-client-android-1.20.0.jar
google-api-services-youtube-v3-rev151-1.20.0.jar
google-http-client-1.20.0.jar
google-http-client-android-1.20.0.jar
google-http-client-jackson2-1.20.0.jar
google-oauth-client-1.20.0.jar
httpmime-4.3.jar
jackson-core-2.1.3.jar
jEN.jar
json_simple-1.1.jar
jsr305-1.3.9.jar
org.apache.commons.httpclient.jar
org.apache.http.legacy.jar
YouTubeAndroidPlayerApi.jar

PROJECT STRUCTURE
-----------

The project structure of an Android application follows the directory layout prescribed by the Android system (for more details see the documentation page http://developer.android.com/tools/projects/index.html)

Specifically,
The "AndroidManifest.xml" file contains essential information the Android system needs to run the application's code.
The "default.properties" file defines the default API level of an Android
The “layout” under the ‘res’ contains all the xml files for layout.

PROJECT BUILD
---------
We assume here that the android emulator is up and running. If not then 
Select Android Device Monitor-> Android Virtual Device Manager ( At the upper right corner) and Create a new android virtual device 
Select the Device -> Target API level 22 (Minimun API level 19) -> No skin and launch the Emulator.

USER AUTHENTICATION
------------
You should sign in with your Google Account credentials. The system uses real user data. (Therefore, cannot provide a sample Account and password)

E-ARCHIVE STRUCTURE
------------
          
----e-archive
-   -   README
-   -   
-   ----code : Folder containing the code of our application
-   -       code.zip: This zip file contains all the code of our android application.
-   -       readme.txt: readme file about how to install and run our application.
-   -      
-   ----data: Folder containing the database and the data that are required to run the application.
-   -       a2166115_bmrdb.sql : SQL dump file that contains the whole database with populated data for the users that have logged in the application till now.
-   -       
-   -       
-   ----design: Folder containing all the design diagrams of our application which exist in all our reports
-   -       class_diagram.jpg : Class diagram of our application
-   -       Domain Models_Login_nw.jpg : Domain model diagram for our login use case
-   -       Domain Models_Personaldetails_nw.jpg : Domain model diagram  for our personal details use case
-   -       Domain Models_Respondtochallenge_nw.jpg : Domain model diagram for our respond to challenge use case
-   -       Domain Models_Run_nw.jpg : Domain model diagram for our run use case
-   -       Domain Models_Sendchallenge_nw.jpg : Domain model diagram for our send challenge use case
-   -       Domain Models_Songselection_nw.jpg : Domain model diagram for our login use case
-   -       Domain Models_Train_nw.jpg : Domain model diagram for our train use case
-   -       Domain Models_Viewstatistics_nw.jpg : Domain model diagram for our view statistics use case
-   -       
-   ----doc: Folder containing the whole documentation of our application
-   -   -   demo1_slides.pdf : Presentations slides for the first demo
-   -   -   demo2_slides.pdf : Presentations slides for the second demo
-   -   -   flyer.pdf : The flyer of our application
-   -   -   individual_contributions.pdf : Document that explains the individual contributions for the project
-   -   -   Report_1.pdf : Report 1 of our project as it was originally submitted 
-   -   -   Report_2.pdf : Report 2 of our project as it was originally submitted
-   -   -   Report_3.pdf : Report 3 of our project containing concatenated the revised reports 1 and 2 plus some new additions that were required.
-   -   -   songInfoEchonestResponse.PNG : This is a sample response of the Echonest API when we search for a song.
-   -   -   technical_documentation.pdf : This file contains the technical documentation of our system.
-   -   -   user_documentation.pdf : This file contains the user documentation of our system.
-   -   -   
-   -   ----user_documentation : Folder containing the demo videos we presented during the demo
-   -           demo1.mp4 : Demo 1 video
-   -           demo2.mp4 : Demo 2 video
-   -           
-   ----tests: Folder containing all our tests (unit and integration tests)
-       -   
-       ----integration_tests : Folder containing the integration tests
-       -       IntegrationTest.java: Integration test java file
-       -       ReadMe3.txt
-       -       
-       ----unit_tests : Folder containing the unit tests
-               DatePickerTest.java : Unit test for checking the statistics use case when the user selects a specific period of time
-               GoogleConnectionCallbacks.java : Unit test for testing the connection callbacks with the Google server
-               LandingPageTest.java : Unit test for testing the main/landing page of our application
-               LoginActivityTest.java : Unit test for testing the login activity (api connections, google authentication etc.)
-               MonthPickerTest.java : Unit test for checking the statistics use case when the user selects to see monthly statistics
-               PersonalInfoActivityTest.java : Unit test for testing the personal information use case
-               ReadMe2.txt: Readme file mentioning what this folder has inside
-               RunActivityTest.java: Unit test for testing the run activity of our application (Run use case)
-               ShowChallengesTest.java: Unit test for testing the show challenges, send challenges and accept challenges use case
-               StatisticsTest.java: Unit test for the main statistics activity of our application

