package timeinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;

@JsonIgnoreProperties
//@Scope("prototype")
public class ServerTime {
    private long serverTime;

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }
}
