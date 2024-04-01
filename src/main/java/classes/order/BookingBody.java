package classes.order;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingBody {
    private final String req_type;
    private final String id;
    public BookingBody(String id) {
        this.req_type = "order";
        this.id = id;
    }
    public String getReq_type() {
        return req_type;
    }
    public String getId() {
        return id;
    }
}
