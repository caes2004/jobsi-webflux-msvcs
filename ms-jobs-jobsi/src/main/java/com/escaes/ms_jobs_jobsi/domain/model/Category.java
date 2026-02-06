package com.escaes.ms_jobs_jobsi.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
@EqualsAndHashCode
public class Category {

    private final String value;


}
