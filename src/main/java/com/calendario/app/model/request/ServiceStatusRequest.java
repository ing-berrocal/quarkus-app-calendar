package com.calendario.app.model.request;

import jakarta.validation.constraints.NotNull;

public class ServiceStatusRequest {

    @NotNull
    private Boolean state;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
