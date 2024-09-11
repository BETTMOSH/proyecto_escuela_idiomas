package es.roberto.escuelaidiomas.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Perfil {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String nombre;

  private String descripcion;

  @ManyToMany(mappedBy = "perfiles")
  private Set<Usuario> usuarios;

}
