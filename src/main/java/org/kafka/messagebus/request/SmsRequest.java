package org.kafka.messagebus.request;

import org.springframework.core.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class SmsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public SmsRequest() {
        this.message = null;
        this.phoneNumber = null;
    }

    private final String phoneNumber;
    private final String message;

    public SmsRequest(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


}
