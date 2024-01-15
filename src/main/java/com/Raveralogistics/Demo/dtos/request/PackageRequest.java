package com.Raveralogistics.Demo.dtos.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PackageRequest {
    private LocalDateTime dateTime;
    private String action;
}
