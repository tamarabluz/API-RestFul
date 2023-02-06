package io.github.tamarabluz.apirestful.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
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
    private String logradouro;
    @Column
    private String cep;
    @Column
    private Long numero;
    @Column(nullable = false)
    private String cidade;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "people_id")
    private People people;

    @Column(nullable = false)
    private Boolean isPrincipal = false;
}
