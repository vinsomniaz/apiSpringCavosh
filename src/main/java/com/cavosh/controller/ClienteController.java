package com.cavosh.controller;

import com.cavosh.model.Cliente;
import com.cavosh.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/v1/cliente")
@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> getCliente(@RequestBody Cliente loginRequest) {
        
        Optional<Cliente> clienteOpt = clienteService.getCliente(
                loginRequest.getCorreo(), 
                loginRequest.getPasswordd()
        );

        boolean success = clienteOpt.isPresent();
        Object data = success ? clienteOpt.get() : null;
        String message = success ? "Cliente registrado" : "Cliente no registrado";

        return ResponseEntity.ok(
            Map.of("success", success, "data", data, "message", message)
        );
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> setCliente(@RequestBody Cliente cliente) {
        
        Map<String, Object> serviceResult = clienteService.setCliente(cliente);
        
        boolean success = serviceResult.containsKey("id") || serviceResult.containsKey("update");
        Object data = serviceResult.containsKey("id") ? serviceResult : null;
        String message;

        if (serviceResult.containsKey("id")) {
            message = "Cliente registrado";
        } else if (serviceResult.containsKey("update")) {
            message = "Cliente actualizado";
        } else if (serviceResult.containsKey("error")) {
            message = (String) serviceResult.get("error");
        } else {
            message = "No se pudo registrar el cliente";
        }

        return ResponseEntity.ok(
            Map.of("success", success, "data", data, "message", message)
        );
    }
    
    @PostMapping("/codigo/")
    public ResponseEntity<Map<String, Object>> getClienteCodigo(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        
        Map<String, Object> serviceResult = clienteService.getClienteCodigo(correo);

        boolean success = serviceResult.containsKey("codigo");
        Object data = success ? serviceResult : null;
        String message = success ? "Código generado" : (String) serviceResult.getOrDefault("error", "Error desconocido");

        return ResponseEntity.ok(
            Map.of("success", success, "data", data, "message", message)
        );
    }
    
    @PostMapping("/codigo/validar")
    public ResponseEntity<Map<String, Object>> getClienteCodigoValidar(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        String codigo = request.get("codigo");
        
        Map<String, Object> serviceResult = clienteService.getClienteCodigoValidar(correo, codigo);
        
        boolean success = !serviceResult.containsKey("error") && !serviceResult.isEmpty();
        
        String message = success ? "Código validado correctamente" : (String) serviceResult.getOrDefault("error", "Código o correo incorrecto/expirado");

        return ResponseEntity.ok(
            Map.of("success", success, "data", serviceResult, "message", message)
        );
    }

}