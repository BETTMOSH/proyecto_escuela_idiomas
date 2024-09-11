package es.roberto.escuelaidiomas.entidades;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "subtipo_alumno")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Subtipos {
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @Column(nullable = false, unique = true, length=100)
        private String nombre;
        @ManyToOne
        @JoinColumn( name="subtipo_idioma_id" )
        private TipoIdioma tipo;
}
