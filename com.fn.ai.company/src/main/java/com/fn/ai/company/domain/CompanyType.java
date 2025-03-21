package com.fn.ai.company.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {

    RECEIVER("수령 업체"),
    PRODUCER("생산 업체");

    private final String typeName;
}
