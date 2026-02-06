package com.escaes.ms_jobs_jobsi.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class Job {

    private UUID id;

    private String title;

    private String description;

    private LocalDateTime publicationDate;

    private Double pago;

    private String ubication;

    private UUID applicantUserId;

    private UUID workerUserId;

    private Category category;

    private Status status;

    private PaymentType paymentType;


}
