package io.github.tamarabluz.apirestful.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String cep;
    @Column
    private String logradouro;
    @Column
    private Long numero;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private Boolean isPrincipal = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peopleId")
    private People people;

}
