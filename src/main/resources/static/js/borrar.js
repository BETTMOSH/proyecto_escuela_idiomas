/*borrado de alumno*/
$(document).ready(() => {
    $('.lnkBorrarAlumno').click(function (event) {
        event.preventDefault();
        const id = $(event.target).closest('tr').find('td:first').text();
        $.ajax({
            type: "get",
            url: "/borrar/show/" + id,
            success: function (htmlRecibido) {
                $('#paraelmodal').html(htmlRecibido);
                $('#borradomodal').modal('show');
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

/* para subir imagen al formulario*/
    $("#fichero").change(function () {
        const file = this.files[0];
        if (file) {
            let reader = new FileReader();
            reader.onload = function (event) {
                $("#image-preview").attr("src", event.target.result);
            };
            reader.readAsDataURL(file);
        }
    });

    /*$("#tipo").change(function () {
        $.ajax({
            type: "get",
            url: "/subtipos",
            data: {
                tipo: $('#tipo').val()
            },
            success: function (data) {
                $('#listaSubtipos').html(data);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });*/


    // Tambien funciona quitando la llamada de ajax.
    // de esta forma se recarga la lista de subtipos cada vez que cambia el tipo de idioma"
     $(document).ready(function () {
         let tipoId = $('#tipoIdioma').val();
         if (tipoId) {
         // Llamada inicial para cargar subtipos al cargar la página
         $.get("/subtipos", { tipo: tipoId}, function (data) {
             $('#listaSubtipos').html(data);
         });
         }
            // Llamada al cambiar el tipo
         $("#tipoIdioma").change(function () {
             $.get("/subtipos", { tipo: $('#tipoIdioma').val() }, function (data) {
                 $('#listaSubtipos').html(data);
             });
         });

     });

    /*$(document).ready(function () {
        let tipoId = $('#tipoIdioma').val();
        if (tipoId) {
            // Llamada inicial para cargar subtipos al cargar la página
            $.get("/subtipos", { tipo: tipoId }, function (data) {
                $('#listaSubtipos').html(data);
            });
        }

        // Llamada al cambiar el tipo
        $("#tipoIdioma").change(function () {
            $.get("/subtipos", { tipo: $('#tipoIdioma').val() }, function (data) {
                $('#listaSubtipos').html(data);
            });
        });
    });*/




    /* funcion para buscar por nombre*/
    $("#buscarPorNombre").keyup(function () {
        $.ajax({
            type: "get",
            url: "/listado/filter",
            data: {
                nombre: $('#buscarPorNombre').val()
            },
            success: function (htmlRecibido) {
                $('#listaAlumnos').html(htmlRecibido);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });
});

$(function() {
    $('[data-toogle="tooltip"]').tooltip()
})

    //Modal para la galeria de imagenes

/*document.addEventListener('DOMContentLoaded', function () {
    let myModal = new bootstrap.Modal(document.getElementById('imagenModal'));
    let imagenModalContenido = document.getElementById('imagenModalContenido');

    // Se agrega un evento de clic a todas las imágenes que deben abrir el modal

    let imagenes = document.querySelectorAll('[data-bs-toggle="modal"]');
    imagenes.forEach(function (imagen) {
        imagen.addEventListener('click', function () {
            let imagenSrc = this.getAttribute('data-imagen');
            imagenModalContenido.src = imagenSrc;
            myModal.show();
        });
    });
});*/

    // Con JQuery también funciona aparte mostramos el título de la imagen en el modal
$(document).ready(function () {
    $('#imagenModal').on('show.bs.modal', function (event) {
        let image = $(event.relatedTarget); // Elemento que activó el modal
        let modal = $(this);

        // Establecer el título del modal con el atributo 'alt' de la imagen
        modal.find('.modal-title').text(image.attr('alt'));

        // Establecer la fuente de la imagen modal con el atributo 'data-imagen' de la imagen
        modal.find('#imagenModalContenido').attr('src', image.data('imagen'));
    });
});



    // JQuery para que el ckeck de modalidad y distancia no se puedan seleccionar a la vez
$(document).ready(function () {
    $('#modalidad').change(function () {
        if ($(this).prop('checked')) {
            $('#distancia').prop('checked', false);
        }
    });

    $('#distancia').change(function () {
        if ($(this).prop('checked')) {
            $('#modalidad').prop('checked', false);
        }
    });
});

    // Con JS también funciona
/*
document.addEventListener('DOMContentLoaded', function () {
    let modalidadCheckbox = document.getElementById('modalidad');
    let distanciaCheckbox = document.getElementById('distancia');
    modalidadCheckbox.addEventListener('change', function () {
        if (this.checked) {
            distanciaCheckbox.checked = false;
        }
    });
    distanciaCheckbox.addEventListener('change', function () {
        if (this.checked) {
            modalidadCheckbox.checked = false;
        }
    });
});*/

/* switch */
function toggleModoOscuro() {
    // Obtiene el estado actual del interruptor
    var switchModoOscuro = document.getElementById("switchModoOscuro");
    // Envía una solicitud al servidor para cambiar el modo oscuro
    window.location.href = "/modo?modoOscuro=" + switchModoOscuro.checked;
}