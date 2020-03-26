# BeatMyRun

## ABOUT

## SCREENSHOTS

<div style="content:"";clear:both;display: table;">
  <div class="float: left;
  width: 33.33%;
  padding: 5px;">
    <img src="UI/ui1.png" width="200px"  />
  </div>
  <div class="float: left;
  width: 33.33%;
  padding: 5px;">
    <img src="UI/ui5.png" width="200px"  />
  </div>
  <div class="float: left;
  width: 33.33%;
  padding: 5px;">
    <img src="UI/ui7.png" width="200px"  />
  </div>
</div>

<div style="content:"";clear:both;display: table;">
  <div class="float: left;
  width: 33.33%;
  padding: 5px;">
    <img src="UI/ui11.png" width="200px"  />
  </div>
  <div class="float: left;
  width: 33.33%;
  padding: 5px;">
    <img src="UI/ui17.png" width="200px"  />
  </div>
  <div class="float: left;
  width: 33.33%;
  padding: 5px;">
    <img src="UI/ui19.png" width="200px"  />
  </div>
</div>


## CONFIGURE

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

## LIBRARIES INCLUDED

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

## PROJECT STRUCTURE

The project structure of an Android application follows the directory layout prescribed by the Android system (for more details see the documentation page http://developer.android.com/tools/projects/index.html)

Specifically,
The "AndroidManifest.xml" file contains essential information the Android system needs to run the application's code.
The "default.properties" file defines the default API level of an Android
The “layout” under the ‘res’ contains all the xml files for layout.

## PROJECT BUILD

We assume here that the android emulator is up and running. If not then 
Select Android Device Monitor-> Android Virtual Device Manager ( At the upper right corner) and Create a new android virtual device 
Select the Device -> Target API level 22 (Minimun API level 19) -> No skin and launch the Emulator.

## USER AUTHENTICATION

You should sign in with your Google Account credentials. The system uses real user data. (Therefore, cannot provide a sample Account and password)
