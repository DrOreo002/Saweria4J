package co.saweria;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.Getter;
import okhttp3.WebSocket;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SaweriaClient implements EventHandler {

    private final EventSource eventSource;

    @Getter
    private String streamKey;
    @Getter
    private Consumer<Donation> onDonationReceived;

    public SaweriaClient(String streamKey) {
        this.streamKey = streamKey;
        this.eventSource = new EventSource.Builder(this, URI.create("https://api.saweria.co/streams?channel=donation." + streamKey))
                .connectTimeoutMs((int) TimeUnit.SECONDS.toMillis(30))
                .reconnectTimeMs((int) TimeUnit.SECONDS.toMillis(30))
                .build();
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

    /**
     * Called when donation is received
     *
     * @param onDonationConsumer Consumer of {@link Donation}
     */
    public void onDonationReceived(Consumer<Donation> onDonationConsumer) {
        this.onDonationReceived = onDonationConsumer;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) throws Exception {

    }

    @Override
    public void onComment(String comment) throws Exception {

    }

    @Override
    public void onError(Throwable t) {

    }
}
