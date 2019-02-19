**Project ePars and Modules**
 It's self project for testing project API and other idea

**Install**
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}Copy
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.JastAir:Android-ProgressView:Tag'
	}


**Today have**
 - [![](https://jitpack.io/v/JastAir/Android-ProgressView.svg)](https://jitpack.io/#JastAir/Android-ProgressView)
 ProgressBar (with percent and indeterminate)

