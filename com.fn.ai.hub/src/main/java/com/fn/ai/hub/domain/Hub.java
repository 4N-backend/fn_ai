package com.fn.ai.hub.domain;

import com.fn.ai.hub.domain.vo.Address;
import com.fn.ai.hub.domain.vo.Location;
import com.fn.ai.hub.domain.vo.Name;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "p_hub")
@Entity
public class Hub {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_id")
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    @Embedded
    private Location location;
}
