package com.fn.ai.hub.domain;

import com.fn.ai.hub.domain.vo.Address;
import com.fn.ai.hub.domain.vo.Location;
import com.fn.ai.hub.domain.vo.Name;
import jakarta.persistence.Column;
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

    public Hub(String name,String address,Double latitude,Double longitude){
        this.name = new Name(name);
        this.address = new Address(address);
        this.location = new Location(latitude, longitude);
    }

    public void updateHub(String newName, String newAddress, double newlatitude,
        double newlongitude) {
        updateName(newName);
        updateAddress(newAddress);
        updatedLocation(newlatitude,newlongitude);
    }

    public void updateName(String value){
        this.name = this.name.update(value);
    }

    public void updateAddress(String value){
        this.address = this.address.update(value);
    }

    public void updatedLocation(double latitude,double longitude){
        this.location = this.location.update(latitude, longitude);
    }
}
