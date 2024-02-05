package com.demorsy.Flight.Search.API.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Airport")
@Data
public class Airport {

    @Id
    String id;

    String city;

}
