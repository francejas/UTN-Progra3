package com.guia2JPA.Guia2JPA.controller;

import com.guia2JPA.Guia2JPA.modelo.Alumno;
import com.guia2JPA.Guia2JPA.modelo.Direccion;
import com.guia2JPA.Guia2JPA.repository.AlumnoRepository;
import com.guia2JPA.Guia2JPA.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 * @RestController: Esta es la evolución del controlador tradicional en el patrón MVC.
 * Le dice a Spring que esta clase va a manejar peticiones web (API REST) y que
 * TODO lo que devuelva se transformará automáticamente a formato JSON directamente.
 */
@RestController
/*
 * @RequestMapping: Define la URL base para toda la clase.
 * Todos los métodos (GET, POST, PUT, DELETE) que definamos acá adentro
 * van a arrancar desde la dirección: http://localhost:8080/api/alumnos
 */
@RequestMapping("/api/alumnos")
public class AlumnoController {

    /*
     * @Autowired: Inyección de Dependencias.
     * En lugar de hacer "AlumnoRepository repo = new AlumnoRepository()",
     * dejamos que Spring cree los objetos y nos los entregue listos para usar[cite: 547, 548, 561].
     */
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private DireccionRepository direccionRepository;


    /* ======================================================================================
     * 1. OBTENER TODOS LOS ALUMNOS (Punto 4 de tu Guía #2 original)
     * ====================================================================================== */

    /*
     * @GetMapping: Reacciona cuando el cliente (Postman) envía una petición HTTP tipo GET.
     * GET se utiliza siempre para obtener o leer datos sin modificarlos.
     */
    @GetMapping
    public List<Alumno> listarAlumnos() {
        // alumnoRepository.findAll() hace la magia de JPA.
        // Equivale a hacer "SELECT * FROM alumnos", armar la lista y devolverla.
        return alumnoRepository.findAll();
    }


    /* ======================================================================================
     * 2. INSERTAR UN NUEVO ALUMNO (Punto 2 de tu Guía #2 original)
     * ====================================================================================== */

    /*
     * @PostMapping: Reacciona a peticiones HTTP tipo POST.
     * POST se utiliza para crear registros nuevos en la base de datos.
     */
    @PostMapping
    public ResponseEntity<Alumno> crearAlumno(@RequestBody Alumno alumno) {
        /*
         * @RequestBody: Atrapa el JSON que mandaste en la pestaña "Body" de Postman
         * y lo convierte automáticamente en un objeto de la clase Alumno en Java.
         */
        Alumno nuevoAlumno = alumnoRepository.save(alumno); // save() hace el INSERT INTO automático.

        /*
         * ResponseEntity: En vez de devolver el JSON pelado, nos permite agregar el
         * "Código de Estado HTTP". Como estamos creando algo con éxito, lo correcto
         * es devolver un código de la familia "2xx (Éxito)", en este caso: 201 CREATED.
         */
        return new ResponseEntity<>(nuevoAlumno, HttpStatus.CREATED);
    }


    /* ======================================================================================
     * 3. INSERTAR UNA NUEVA DIRECCIÓN A UN ALUMNO (Punto 3 de tu Guía #2 original)
     * ====================================================================================== */

    /*
     * La URL será: POST a http://localhost:8080/api/alumnos/{id}/direcciones
     */
    @PostMapping("/{id}/direcciones")
    public ResponseEntity<?> agregarDireccion(@PathVariable Integer id, @RequestBody Direccion direccion) {
        /*
         * @PathVariable: Agarra el ID dinámico que pusiste en la URL (ej: /alumnos/5/direcciones)
         * y lo mete en la variable Integer id para poder buscar al alumno.
         */

        // Buscamos si el alumno existe usando el ID de la URL.
        // Usamos Optional porque la búsqueda puede traer un alumno, o puede ser Nulo si no existe.
        Optional<Alumno> alumnoOpcional = alumnoRepository.findById(id);

        if (alumnoOpcional.isPresent()) {
            // isPresent() verifica que el alumno existe. Si existe, lo sacamos del Optional con .get()
            Alumno alumno = alumnoOpcional.get();

            // Le asignamos el alumno dueño a esta dirección (esto completa la Foreign Key).
            direccion.setAlumno(alumno);

            direccionRepository.save(direccion); // Hacemos el INSERT INTO de la dirección.
            return new ResponseEntity<>(direccion, HttpStatus.CREATED);
        } else {
            /*
             * Manejo de errores: Si buscaste el ID de un alumno que no existe,
             * respondemos con un texto explicativo y un código de estado "4xx (Error del Cliente)"[cite: 281].
             * Usamos el clásico 404 NOT FOUND (No Encontrado).
             */
            return new ResponseEntity<>("El alumno no existe", HttpStatus.NOT_FOUND);
        }
    }


    /* ======================================================================================
     * 4. ACTUALIZAR LA EDAD DE UN ALUMNO (Punto 6 de tu Guía #2 original)
     * ====================================================================================== */

    /*
     * @PutMapping: Reacciona a peticiones HTTP tipo PUT.
     * PUT se usa para actualizar o modificar un registro que ya existe[cite: 243, 244].
     * La URL será: PUT a http://localhost:8080/api/alumnos/{id}/edad?nuevaEdad=XX
     */
    @PutMapping("/{id}/edad")
    public ResponseEntity<?> actualizarEdad(@PathVariable Integer id, @RequestParam Integer nuevaEdad) {
        /*
         * @RequestParam: Agarra el parámetro que se envía al final de la URL
         * después del signo de interrogación (ej: ?nuevaEdad=25).
         */
        Optional<Alumno> alumnoOpcional = alumnoRepository.findById(id);

        if (alumnoOpcional.isPresent()) {
            Alumno alumno = alumnoOpcional.get();

            // Actualizamos la edad del objeto Java.
            alumno.setEdad(nuevaEdad);

            /*
             * En JPA, el método save() sirve para dos cosas:
             * Si el objeto NO tiene ID, hace un INSERT.
             * Si el objeto YA TIENE ID (como este, que lo sacamos de la BD), hace un UPDATE.
             */
            alumnoRepository.save(alumno);

            // Devolvemos el alumno modificado con un código 200 OK (Éxito estándar)
            return new ResponseEntity<>(alumno, HttpStatus.OK);
        }

        return new ResponseEntity<>("Alumno no encontrado para actualizar", HttpStatus.NOT_FOUND);
    }


    /* ======================================================================================
     * 5. ELIMINAR UN ALUMNO (Punto 7 de tu Guía #2 original)
     * ====================================================================================== */

    /*
     * @DeleteMapping: Reacciona a peticiones HTTP tipo DELETE.
     * Se usa exclusivamente para borrar recursos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable Integer id) {
        Optional<Alumno> alumnoOpcional = alumnoRepository.findById(id);

        if (alumnoOpcional.isPresent()) {
            /*
             * deleteById(id) borra el alumno.
             * Como en el modelo Alumno pusimos "cascade = CascadeType.ALL",
             * al borrarse el alumno se borrarán automáticamente todas sus direcciones en MySQL.
             */
            alumnoRepository.deleteById(id);
            return new ResponseEntity<>("Alumno eliminado correctamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("No se pudo eliminar: Alumno no encontrado", HttpStatus.NOT_FOUND);
    }
}