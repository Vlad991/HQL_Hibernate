package com.infopulse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    @Basic
    private Long id;

    @Column(name = "name")
    private String name;

//    @ManyToMany
//    private List<Client> clients;

    @OneToMany(mappedBy = "bank")
    private List<ClientBank> clientBanks;
}
