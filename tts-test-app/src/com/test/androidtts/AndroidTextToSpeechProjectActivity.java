package com.test.androidtts;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;
import java.util.Random;
public class AndroidTextToSpeechProjectActivity extends Activity implements
TextToSpeech.OnInitListener {
    private static final String TAG = "TextToSpeechDemo";
    private TextToSpeech mTts;
    private Button mAgainButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Initialize text-to-speech. This is an asynchronous operation.
        // The OnInitListener (second argument) is called after initialization completes.
        mTts = new TextToSpeech(this,
            this  //TextToSpeech.OnInitListener
            );
        mAgainButton = (Button) findViewById(R.id.again_button);
        mAgainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sayHello();
            }
        });
    }
    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }
    // Implements TextToSpeech.OnInitListener.
    public void onInit(int status) {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
                       int result = mTts.setLanguage(Locale.US);
           
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
               // Lanuage data is missing or the language is not supported.
                Log.e(TAG, "Language is not available.");
            } else {
                // Check the documentation for other possible result codes.
                // For example, the language may be available for the locale,
                // but not for the specified country and variant.
                // The TTS engine has been successfully initialized.
                // Allow the user to press the button for the app to speak again.
                mAgainButton.setEnabled(true);
                // Greet the user.
                sayHello();
            }
        } else {
            // Initialization failed.
            Log.e(TAG, "Could not initialize TextToSpeech.");
        }
    }
    private static final Random RANDOM = new Random();
    private static final String[] HELLOS = {
      "Hello World",
      "This is Text to speech demo by Zahid Shaikh"
      
    };
    int i =0;
    private void sayHello() {
        // Select a random hello.
        int helloLength = HELLOS.length;
        String hello = HELLOS[i];
        i++;
        if(i == helloLength) i =0;
        mTts.speak(hello,
            TextToSpeech.QUEUE_FLUSH,  // Drop allpending entries in the playback queue.
            null);
    }
}