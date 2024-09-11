package es.roberto.escuelaidiomas.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Preferencias {
  @Id
  private Long id;

  private boolean darkMode;
  private String idioma;

  @OneToOne
  @MapsId
  private Usuario usuario;
}
