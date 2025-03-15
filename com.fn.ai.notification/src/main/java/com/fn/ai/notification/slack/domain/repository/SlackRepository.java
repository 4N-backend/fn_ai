package com.fn.ai.notification.slack.domain.repository;

import com.fn.ai.notification.slack.domain.model.Slack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlackRepository extends JpaRepository<Slack, UUID> {
}
