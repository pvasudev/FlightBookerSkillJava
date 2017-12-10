package net.paavan.flightbookerskill.speechlet;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.json.SpeechletRequestModule;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class FlightBookerSpeechlet implements SpeechletV2 {
    private static final ObjectMapper MAPPER = new ObjectMapper() {{
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // For context object
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        registerModule(new SpeechletRequestModule());
    }};

    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> speechletRequestEnvelope) {
        logSpeechletReqeust("onSessionStarted", speechletRequestEnvelope);
    }

    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> speechletRequestEnvelope) {
        logSpeechletReqeust("onLaunch", speechletRequestEnvelope);

        SpeechletResponse response = new SpeechletResponse();
        response.setShouldEndSession(false);
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Hello. What do you want to do?");
        response.setOutputSpeech(outputSpeech);
        return logSpeechletResponse("onLaunch", response);
    }

    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> speechletRequestEnvelope) {
        logSpeechletReqeust("onIntent", speechletRequestEnvelope);

        SpeechletResponse response = new SpeechletResponse();

        if (speechletRequestEnvelope.getRequest().getDialogState() != IntentRequest.DialogState.COMPLETED) {
            response.setShouldEndSession(false);
            response.setDirectives(Arrays.asList(new DelegateDirective()));
            return logSpeechletResponse("onIntent", response);
        }

        String fromCity = speechletRequestEnvelope.getRequest().getIntent().getSlot("fromCity").getValue();
        String toCity = speechletRequestEnvelope.getRequest().getIntent().getSlot("toCity").getValue();
        String date = speechletRequestEnvelope.getRequest().getIntent().getSlot("date").getValue();

        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Booked your flight from " + fromCity + " to " + toCity + " on " + date);
        response.setOutputSpeech(outputSpeech);
        return logSpeechletResponse("onIntent", response);
    }

    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> speechletRequestEnvelope) {
        logSpeechletReqeust("onSessionEnded", speechletRequestEnvelope);
    }

    // --------------
    // Helper Methods

    private void logSpeechletReqeust(final String tag, final SpeechletRequestEnvelope<?> requestEnvelope) {
        try {
            log.info(tag + " SpeechletRequestEnvelope: " + MAPPER.writeValueAsString(requestEnvelope));
        } catch (JsonProcessingException e) {
            log.error("Error serializing speechlet request", e);
        }
    }

    private SpeechletResponse logSpeechletResponse(final String tag, final SpeechletResponse speechletResponse) {
        try {
            log.info(tag + " SpeechletResponse: " + MAPPER.writeValueAsString(speechletResponse));
            return speechletResponse;
        } catch (JsonProcessingException e) {
            log.error("Error serializing speechlet request", e);
            return null;
        }
    }

}
