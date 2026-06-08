package com.Guia3JPA.Guia3JPA.controller;

import com.Guia3JPA.Guia3JPA.model.*;
import com.Guia3JPA.Guia3JPA.respository.CredencialesRespository;
import com.Guia3JPA.Guia3JPA.respository.UsuarioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.ref.ReferenceQueue;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// 1. @RestController le dice a Spring: "Esta clase va a recibir peticiones de Postman
// y todo lo que devuelva lo tiene que convertir a JSON".
@RestController
// 2. @RequestMapping define la URL base. Todos los métodos acá adentro arrancarán con esta ruta.
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // 3. @Autowired es la "Inyección de Dependencias".
    // Le pedimos a Spring que nos traiga al "Cocinero" (UsuarioRepository) listo para usar.
    @Autowired
    private UsuarioRespository usuarioRepository;

    @Autowired
    private CredencialesRespository credencialesRespository;

    // 4. @PostMapping indica que este método solo se ejecuta cuando nos mandan un POST (para crear cosas)
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){

        /*
         * 5. @RequestBody agarra el JSON que mandamos desde Postman (que solo tiene nombre,
         * apellido, dni y email) y lo transforma en el objeto 'usuario' de Java.
         */

        // PASO A: Generar Credenciales
        // Armamos un username juntando el apellido y el nombre en minúsculas
        String usernameGenerado = (usuario.getApellido() + usuario.getNombre()).toLowerCase();
        // Creamos la credencial (usamos el DNI como contraseña inicial para simplificar)
        Credenciales credenciales = new Credenciales(usuario, usernameGenerado, usuario.getDni(), Permiso.CLIENTE);

        // PASO B: Generar Cuenta Inicial
        // Creamos una caja de ahorro en 0.0 vinculada a este usuario
        Cuenta cuentaInicial = new Cuenta(usuario, TipoCuenta.CAJA_AHORRO, 0.0);

        // PASO C: Vincular todo al Usuario (Enganchar las relaciones)
        usuario.setCredenciales(credenciales); // Le asignamos su credencial (Relación 1 a 1)
        usuario.getCuentas().add(cuentaInicial); // Agregamos la cuenta a su lista (Relación 1 a N)

        /*
         * PASO D: ¡Guardar en la Base de Datos!
         * Al hacer save() del usuario, gracias al 'cascade = CascadeType.ALL' que pusiste
         * en tus modelos, Hibernate va a hacer automáticamente TRES "INSERT INTO" en MySQL:
         * uno para el usuario, otro para sus credenciales y otro para su cuenta.
         */
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // PASO E: Devolver la respuesta a Postman
        // Devolvemos el usuario ya guardado (ahora tiene ID) con un código 201 (CREATED)
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginData) {

        // Vamos a la base de datos a buscar por el usuario y contraseña que llegaron de Postman
        Optional<Credenciales> credencialOpcional = credencialesRespository.findByUsernameAndPassword(
                loginData.getUsername(),
                loginData.getPassword()
        );

        // Verificamos si la caja tiene algo adentro
        if (credencialOpcional.isPresent()) {
            // ¡Login exitoso! Sacamos la credencial de la caja, obtenemos su usuario y lo devolvemos con un 200 OK
            Usuario usuarioLogueado = credencialOpcional.get().getUsuario();
            return new ResponseEntity<>(usuarioLogueado, HttpStatus.OK);
        } else {
            // ¡Login fallido! Devolvemos un mensaje de error y un código 401 UNAUTHORIZED
            return new ResponseEntity<>("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping
    public ResponseEntity<?> listarUsuarios (
            @RequestHeader("id-solicitante") Integer idSolicitante,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String email) {

        // --- PASO 1: VALIDACIÓN DE EXISTENCIA DEL SOLICITANTE ---
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idSolicitante);

        if (usuarioOptional.isEmpty()){
            // Mejora de mensaje: Le decimos exactamente por qué falló
            return new ResponseEntity<>("Error: El usuario solicitante (ID " + idSolicitante + ") no existe en el sistema.", HttpStatus.NOT_FOUND);
        }

        // --- PASO 2: VALIDACIÓN DE SEGURIDAD (ROLES) ---
        Usuario usuarioSolicitante = usuarioOptional.get();

        if (usuarioSolicitante.getCredenciales().getPermiso() == Permiso.CLIENTE){
            // Mejora de mensaje: Explicamos qué permiso le falta
            return new ResponseEntity<>("Acceso denegado: Esta acción es exclusiva para Gestores o Administradores.", HttpStatus.FORBIDDEN);
        }

        // --- PASO 3: OBTENCIÓN DE DATOS ---
        // Si llegó hasta acá, es porque es Gestor o Admin. Traemos a todos.
        List<Usuario> lista = usuarioRepository.findAll();

        // --- PASO 4: FILTROS (Punto 5 de la Guía) ---
        if (dni != null){
            // Usamos findFirst() porque el DNI es único. Esto nos devuelve un Optional<Usuario>
            Optional<Usuario> usuarioFiltrado = lista.stream()
                    .filter(p -> p.getDni().equals(dni))
                    .findFirst();

            // Si lo encuentra lo devuelve (200 OK), si no, avisa que no existe (404 NOT FOUND)
            if (usuarioFiltrado.isPresent()) {
                return new ResponseEntity<>(usuarioFiltrado.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontró ningún usuario con el DNI: " + dni, HttpStatus.NOT_FOUND);
            }

        } else if (email != null){
            // Misma lógica para el email
            Optional<Usuario> usuarioFiltrado = lista.stream()
                    .filter(p -> p.getEmail().equals(email))
                    .findFirst();

            if (usuarioFiltrado.isPresent()) {
                return new ResponseEntity<>(usuarioFiltrado.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontró ningún usuario con el Email: " + email, HttpStatus.NOT_FOUND);
            }

        } else {
            // --- PASO 5: LISTADO COMPLETO (Punto 4 de la Guía) ---
            // Si no mandaron ni DNI ni Email en la URL, devolvemos la lista entera
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}") // El {id} es el ID del usuario que quiero cambiar
    public ResponseEntity<?> modificarUsuario(
            @RequestHeader("id-solicitante") Integer idSolicitante,
            @PathVariable Integer id, // Atrapa el ID de la URL
            @RequestBody Usuario datosNuevos // Los datos que quiero actualizar
    ) {
        // 1. Buscamos al solicitante y al usuario a modificar
        Optional<Usuario> solicitanteOpt = usuarioRepository.findById(idSolicitante);
        Optional<Usuario> objetivoOpt = usuarioRepository.findById(id);

        if (solicitanteOpt.isEmpty() || objetivoOpt.isEmpty()) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        Usuario solicitante = solicitanteOpt.get();
        Usuario objetivo = objetivoOpt.get();
        Permiso permisoSolicitante = solicitante.getCredenciales().getPermiso();

        // 2. Lógica de permisos
        boolean esElMismo = idSolicitante.equals(id);
        boolean esGestorYModificaCliente = (permisoSolicitante == Permiso.GESTOR && objetivo.getCredenciales().getPermiso() == Permiso.CLIENTE);
        boolean esAdmin = (permisoSolicitante == Permiso.ADMINISTRADOR);

        if (esElMismo || esGestorYModificaCliente || esAdmin) {
            // 3. Si tiene permiso, aplicamos los cambios
            objetivo.setNombre(datosNuevos.getNombre());
            objetivo.setApellido(datosNuevos.getApellido());
            objetivo.setEmail(datosNuevos.getEmail());

            usuarioRepository.save(objetivo);
            return new ResponseEntity<>(objetivo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Acceso denegado: No tienes permiso para modificar a este usuario.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@RequestHeader("id-solicitante") Integer idSolicitante, @PathVariable Integer id){
        Optional<Usuario> usuarioOptional  = usuarioRepository.findById(idSolicitante);
        Optional<Usuario> usuarioEliminar = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty() || usuarioEliminar.isEmpty()){
            return new ResponseEntity<>("El usuario no existe", HttpStatus.NOT_FOUND);
        }

        Usuario usuarioSolicitatante = usuarioOptional.get();
        Usuario usuarioAEliminar = usuarioEliminar.get();

        boolean esGestor = usuarioSolicitatante.getCredenciales().getPermiso() == Permiso.GESTOR && usuarioAEliminar.getCredenciales().getPermiso() == Permiso.CLIENTE;
        boolean esAdmin = usuarioSolicitatante.getCredenciales().getPermiso() == Permiso.ADMINISTRADOR;

        if (esGestor|| esAdmin){
            usuarioRepository.delete(usuarioAEliminar);
            return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No tiene permiso para realizar esta acción.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<?> listarCuentasUsuario (@RequestHeader("id-solicitante") Integer idSolicitante, @PathVariable Integer id){
        // 1. Buscamos al solicitante
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idSolicitante);
        // 2. Buscamos al dueño de las cuentas (el que pedimos en la ruta {id})
        Optional<Usuario> usuarioDueño = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty() || usuarioDueño.isEmpty()){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        Usuario usuarioSolicitante = usuarioOptional.get();
        Usuario usuarioDuenio = usuarioDueño.get();

        // REGLAS DE NEGOCIO:
        // A. Es el dueño (el cliente viendo sus propias cuentas)
        boolean esElMismo = idSolicitante.equals(id);
        // B. Es Gestor o Admin (estos pueden ver cuentas de cualquiera)
        boolean esGestorOAdmin = (usuarioSolicitante.getCredenciales().getPermiso() != Permiso.CLIENTE);

        if (esElMismo || esGestorOAdmin){
            return new ResponseEntity<>(usuarioDuenio.getCuentas(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No tienes permiso para ver estas cuentas", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<?> verSaldo(@RequestHeader("id-solicitante")Integer idSolicitante, @PathVariable Integer id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idSolicitante);
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty() || usuario.isEmpty()){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        Usuario usuarioSolicitante = usuarioOptional.get();
        Usuario usuarioSaldo = usuario.get();

        boolean esElMismo = idSolicitante.equals(id);
        boolean esGestorOAdmin = usuarioSolicitante.getCredenciales().getPermiso() != Permiso.CLIENTE;

        if (esElMismo || esGestorOAdmin){
            // Aplicamos la corrección en la suma
            Double saldoTotal = usuarioSaldo.getCuentas().stream()
                    .mapToDouble(Cuenta::getSaldo)
                    .sum();

            return new ResponseEntity<>("Saldo total: $" + saldoTotal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No tiene permiso", HttpStatus.FORBIDDEN);
        }
    }


}
