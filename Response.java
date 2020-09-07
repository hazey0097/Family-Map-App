package Result;

import java.util.Objects;

public class Response {
    protected boolean success;
    protected String message;
    /**
     * Default Constructor
     */
    public Response(){}
    //SETTERS
    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    //GETTERS
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return success == response.success &&
                Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message);
    }
}
