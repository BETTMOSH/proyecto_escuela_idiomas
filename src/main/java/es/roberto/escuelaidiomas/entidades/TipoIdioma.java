package es.roberto.escuelaidiomas.entidades;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_idioma")
public class TipoIdioma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length=50)
    private String nombre;
    @OneToMany(mappedBy = "tipo")
    private List<Subtipos> subtipos;
}

