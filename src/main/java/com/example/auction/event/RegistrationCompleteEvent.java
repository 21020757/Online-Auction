package com.example.auction.event;

import com.example.auction.dto.SignupRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private SignupRequest request;
    private String applicationUrl;

    public RegistrationCompleteEvent(SignupRequest request,
                                     String applicationUrl) {
        super(request);
        this.request = request;
        this.applicationUrl = applicationUrl;
    }
}
