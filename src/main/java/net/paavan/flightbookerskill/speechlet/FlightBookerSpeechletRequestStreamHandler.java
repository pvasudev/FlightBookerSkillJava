package net.paavan.flightbookerskill.speechlet;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class FlightBookerSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> SUPPORTED_APPLICATION_IDS = new HashSet<String>() {{
        add("amzn1.ask.skill.43f2c001-5c2f-4526-9849-8e7d31b2af22");
    }};

    public FlightBookerSpeechletRequestStreamHandler() {
        super(new FlightBookerSpeechlet(), SUPPORTED_APPLICATION_IDS);
    }
}
