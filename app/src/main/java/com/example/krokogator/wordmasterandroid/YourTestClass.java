package com.example.krokogator.wordmasterandroid;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;

public class YourTestClass extends ActivityInstrumentationTestCase2 {


    public YourTestClass(String pkg, Class activityClass) {
        super(pkg, activityClass);
    }

    public void testHoldSwipe() {
        long downTime = SystemClock.uptimeMillis();
// event time MUST be retrieved only by this way!
        long eventTime = SystemClock.uptimeMillis();
        Instrumentation inst = getInstrumentation();

// Just init your variables, or create your own coords logic 🙂
        float xStart = 200;
        float yStart = 200;
// simulating thick finger touch
        float x0 = 205;
        float y0 = 205;
        float x1 = 205;
        float y1 = 208;
        float x2 = 235;
        float y2 = 215;
// move finger to bottom and right
// increment previous coords
        float x3 = 245;
        float y3 = 255;
        float x4 = 247;
        float y4 = 275;
        float x5 = 252;
        float y5 = 300;
// proceed one more movement to bottom and left
        float x6 = 230;
        float y6 = 320;
        float x7 = 200;
        float y7 = 357;
        float x8 = 180;
        float y8 = 380;
        float x9 = 160;
        float y9 = 400;
// release finger, logically to use coords from last movent
        float x10 = 160;
        float y10 = 400;

        try {
// sending event – finger touched the screen
            MotionEvent event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, xStart, yStart, 0);
            inst.sendPointerSync(event);
// sending events – finger is moving over the screen
            eventTime = SystemClock.uptimeMillis();
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x0, y0, 0);
            inst.sendPointerSync(event);
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x1, y1, 0);
            inst.sendPointerSync(event);
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x2, y2, 0);
            inst.sendPointerSync(event);
// simulating pause by incrementing eventTime with 1 second
// finger is still touching the screen
            eventTime = SystemClock.uptimeMillis() + 1000;
// moving finger across the screen
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x3, y3, 0);
            inst.sendPointerSync(event);
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x4, y4, 0);
            inst.sendPointerSync(event);
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x5, y5, 0);
            inst.sendPointerSync(event);
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x6, y6, 0);
// simulating one more pause
            eventTime = SystemClock.uptimeMillis() + 1000;
// moving finger again
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x7, y7, 0);
            inst.sendPointerSync(event);
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x8, y8, 0);
            inst.sendPointerSync(event);
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x9, y9, 0);
            inst.sendPointerSync(event);
// release finger, gesture is finished
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x10, y10, 0);
            inst.sendPointerSync(event);

        } catch (Exception ignored) {
// Handle exceptions if necessary
        }
    }

    @Override
    public void tearDown() throws Exception {

        getActivity().finish();
        super.tearDown();
    }

}