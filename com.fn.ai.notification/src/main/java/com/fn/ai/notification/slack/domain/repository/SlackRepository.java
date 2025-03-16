package com.fn.ai.notification.slack.domain.repository;

import com.fn.ai.notification.slack.domain.model.Slack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlackRepository extends JpaRepository<Slack, UUID> {

    // recipientSlackId 기반 필터링으로 페이징 조회
    Page<Slack> findByRecipientSlackId(String recipientSlackId, Pageable pageable);
}
