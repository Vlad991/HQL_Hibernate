package com.infopulse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "clients")
@Inheritance(strategy = TABLE_PER_CLASS)
@DiscriminatorColumn(name = "Typecli", discriminatorType = STRING, length = 20)
@DiscriminatorValue("CLIENT")
public class Client {
    //single primary key
    @Id
    @GenericGenerator(name="increment", strategy = "increment")
    @GeneratedValue(generator="increment")
    @Basic
    private Long id;

    // complex primary key
//    @EmbeddedId
//    private ComplexKey key;

    @Column(name = "cname", unique = false, nullable = false, length = 255)
    @Basic
    private String name;

    @Column(name = "surename", nullable = false)
    private String surename;

    @Type(type = "com.infopulse.entity.AddressType")
    @Column(name = "addr")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Telephone telephone;

//    @ManyToMany(mappedBy = "clients")
//    private List<Bank> banks;

    @OneToMany(mappedBy = "client")
    private List<ClientBank> clientBanks;
}