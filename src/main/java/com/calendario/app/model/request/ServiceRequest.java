package com.calendario.app.model.request;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ServiceRequest {
    
    @NotBlank
    @NotEmpty
    @Size(min = 3,max = 100)
    private String title;
    private String description;
    @NotNull
    @Positive
    @Max(value = 60)
    @Min(value = 1)
    private int durationInMinute;
    @NotNull
    @Positive
    @Min(value = 1)
    private int serviceConcurrency;

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getDurationInMinute() {
        return durationInMinute;
    }
    public void setDurationInMinute(int durationInMinute) {
        this.durationInMinute = durationInMinute;
    }
    public int getServiceConcurrency() {
        return serviceConcurrency;
    }
    public void setServiceConcurrency(int serviceConcurrency) {
        this.serviceConcurrency = serviceConcurrency;
    }

    
}
