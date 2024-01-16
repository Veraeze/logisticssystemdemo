package com.Raveralogistics.Demo.data.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PackageHistory {
    private LocalDateTime dateTime;
    private String action;
}
