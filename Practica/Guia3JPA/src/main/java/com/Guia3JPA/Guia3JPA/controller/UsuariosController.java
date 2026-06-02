package com.Guia3JPA.Guia3JPA.controller;

import com.Guia3JPA.Guia3JPA.model.*;
import com.Guia3JPA.Guia3JPA.respository.CredencialRepository;
import com.Guia3JPA.Guia3JPA.respository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CredencialRepository credencialRepository;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){

        // 1. Generamos las credenciales (username = apellido+nombre en minúsculas, password = DNI)
        String usernameGenerado = (usuario.getApellido() + usuario.getNombre()).toLowerCase();
        Credenciales credenciales = new Credenciales(usuario, usernameGenerado, usuario.getDni(), Permiso.CLIENTE);

        // 2. Creamos la cuenta inicial (Caja de Ahorro con saldo 0)
        Cuenta cuenta = new Cuenta(usuario, TipoCuenta.CAJA_AHORRO, 0.0);

        // 3. Enganchamos los objetos al usuario
        usuario.setCredenciales(credenciales);
        usuario.getCuentas().add(cuenta); // <- CORRECCIÓN 1: Usamos .add() porque es una Lista

        // 4. Guardamos en la base de datos
        // <- CORRECCIÓN 2: Usamos "usuarioRepository" (minúscula), asumiendo que arriba pusiste el @Autowired
        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        // 5. Respondemos a Postman
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginData){

        Optional<Credenciales> credenciales = credencialRepository.findByUsernameAndPassword(loginData.getUsername(), loginData.getPassword());

        if (credenciales.isPresent()){
            return new ResponseEntity<>(credenciales.get().getUsuario(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario o contrasenia incorrecta.", HttpStatus.UNAUTHORIZED);
        }

    }

    @g


}
