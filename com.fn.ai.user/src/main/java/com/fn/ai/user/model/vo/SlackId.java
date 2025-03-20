package com.fn.ai.user.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
@Getter
public class SlackId {

    @Column(name = "slack_id", nullable = false)
    private String value;

    public SlackId(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new NullPointerException("SlackId is Null");
        }
        // TODO : 테스트 끝난후 주석 해제
        // U로 시작하는 문자열
//        if (!value.startsWith("U")) {
//            throw new IllegalArgumentException("SlackId must start with 'U'");
//        }
    }

    public SlackId update(String value) {
        return new SlackId(value);
    }
}
