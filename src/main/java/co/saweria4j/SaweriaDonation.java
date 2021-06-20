package co.saweria4j;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * SaweriaDonation, just a simple class to wrap the data
 * contains stuff that I use mainly. Feel free to add more
 */
@ToString
public class SaweriaDonation {

    @Getter @Expose
    private String amount, donator, message;
    @Getter @Setter
    private Instant date;
}
