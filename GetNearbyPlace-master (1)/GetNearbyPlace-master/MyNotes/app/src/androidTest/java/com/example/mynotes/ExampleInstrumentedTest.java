package com.example.mynotes;

import android.content.Context;
//What is import Android content context?
//content. Context provides the connection to the Android system and the resources of the project. It is the interface to global information about the application environment. The Context also provides access to Android Services, e.g. the Location Service. Activities and Services extend the Context class.
import androidx.test.platform.app.InstrumentationRegistry;
//Above class is depreceated
//An exposed registry instance that holds a reference to the instrumentation running in the process and its arguments. Also provides an easy way for callers to get a hold of instrumentation, application context and instrumentation arguments Bundle.
import androidx.test.ext.junit.runners.AndroidJUnit4;
//A JUnit4 runner for Android tests.

// This runner offers several features on top of the standard JUnit4 runner,

// Supports running on Robolectric. This implementation will delegate to RobolectricTestRunner if test is running in Robolectric enviroment. A custom runner can be provided by specifying the full class name in a 'android.junit.runner' system property.
// Supports a per-test timeout - specfied via a 'timeout_msec' AndroidJUnitRunner argument.
// Supports running tests on the application's UI Thread, for tests annotated with ERROR(/UiThreadTest).
import org.junit.Test;
// JUnit is a Regression Testing Framework used by developers to implement unit testing in Java, and accelerate programming speed and increase the quality of code. JUnit Framework can be easily integrated with either of the following −

// Eclipse
// Ant
// Maven
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
// A set of assertion methods useful for writing tests. Only failed assertions are recorded.
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.mynotes", appContext.getPackageName());
    }
}
