package co.saweria4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.Getter;

import java.net.URI;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SaweriaClient implements EventHandler {

    private final Gson gson;
    private final EventSource eventSource;

    @Getter
    private final String streamKey;
    @Getter
    private final SaweriaEventHandler eventHandler;

    public SaweriaClient(String streamKey, SaweriaEventHandler eventHandler) {
        this.streamKey = streamKey;
        this.eventSource = new EventSource.Builder(this, URI.create("https://api.saweria.co/streams?channel=donation." + streamKey))
                .connectTimeoutMs((int) TimeUnit.SECONDS.toMillis(30))
                .reconnectTimeMs((int) TimeUnit.SECONDS.toMillis(30))
                .build();
        this.eventHandler = eventHandler;
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * Start listening to event. Will be done on current thread
     */
    public void start() {
        this.eventSource.start();
    }

    /**
     * Close this event source, after closing {@link #start()} will not be able execute
     */
    public void close() {
        this.eventSource.close();
    }

    @Override
    public void onOpen() {
        this.eventHandler.onOpen();
    }

    @Override
    public void onClosed() {
        this.eventHandler.onClosed();
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        if (event.equals("donations")) {
            SaweriaDonation[] donations = this.gson.fromJson(messageEvent.getData(), SaweriaDonation[].class);
            for (SaweriaDonation donation : donations) {
                donation.setDate(Instant.now());
            }
            this.eventHandler.onDonationReceived(donations);
        }
    }

    @Override
    public void onError(Throwable t) { }

    @Override
    public void onComment(String comment) throws Exception { }
}
