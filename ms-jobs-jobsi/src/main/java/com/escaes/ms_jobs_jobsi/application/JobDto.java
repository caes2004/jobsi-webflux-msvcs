package com.escaes.ms_jobs_jobsi.application;

import com.escaes.ms_jobs_jobsi.domain.model.Category;
import com.escaes.ms_jobs_jobsi.domain.model.PaymentType;
import com.escaes.ms_jobs_jobsi.domain.model.Status;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class JobDto {

    UUID id;

    String title;

    String description;

    LocalDateTime publicationDate;

    Double pago;

    String ubication;

    UUID applicantUserId;

    UUID workerUserId;

    Category category;

    Status status;

    PaymentType paymentType;
}
