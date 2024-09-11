package es.roberto.escuelaidiomas.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacio")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @NotEmpty
    @Column(nullable = false, unique = true, length = 50)
    private String apellidos;

    @Email(message = "El email no es valido")
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @NotNull(message = "El tipo de idioma no puede estar vacio")
    @ManyToOne
    @JoinColumn(name = "tipo_idioma_id")
    private TipoIdioma tipoIdioma;

    @NotNull(message = "El subtipo de idioma no puede estar vacio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtipo_idioma_id")
    private Subtipos subtipo;

    @Past(message = "La fecha de nacimiento debe ser anterior a la actual")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    private boolean presencial;

    private String foto;


}
