package com.cavosh.service;

import com.cavosh.model.Cliente;
import com.cavosh.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Cliente> getCliente(String correo, String passwordd) {
        List<Cliente> results = clienteRepository.getCliente(correo, passwordd);
        return results.stream().findFirst();
    }

    /**
     * Maneja el SP setCliente (Insertar/Actualizar)
     * @param cliente 
     * @return 
     */
    public Map<String, Object> setCliente(Cliente cliente) {
        
        Long id = cliente.getId() != null ? cliente.getId() : 0L;
        List<Map<String, Object>> results = clienteRepository.setCliente(
                id, cliente.getNombres(), cliente.getCorreo(), cliente.getPasswordd()
        );

        if (results.isEmpty() && id > 0) {
            return Map.of("update", true);
        }

        if (!results.isEmpty()) {
            Map<String, Object> row = results.get(0);

            if (row.containsKey("insertID")) {
                Long newId = ((Number) row.get("insertID")).longValue();
                cliente.setId(newId);
                return Map.of("id", newId, "nombres", cliente.getNombres(), "correo", cliente.getCorreo());
            }

            if (row.containsKey("error")) {
                return Map.of("error", row.get("error"));
            }
        }

        return Collections.emptyMap();
    }
    
    /**
     * Genera/obtiene el código de verificación para un correo.
     * @param correo 
     * @return 
     */
    public Map<String, Object> getClienteCodigo(String correo) {
        
        List<Map<String, Object>> results;
        try {
            results = clienteRepository.getClienteCodigo(correo);
        } catch (Exception e) {
            return Map.of("error", "El servidor no está disponible");
        }

        if (!results.isEmpty()) {
            Map<String, Object> row = results.get(0);

            if (row.containsKey("id")) {
                return Map.of("id", row.get("id"), "codigo", row.get("codigo"));
            }
            
            if (row.containsKey("error")) {
                 return Map.of("error", row.get("error"));
            }
        }
        
        return Map.of("error", "No se encontró el código o el cliente.");
    }

    /**
     * EValida si el código para un correo es correcto.
     * @param correo
     * @param codigo
     * @return 
     */
    public Map<String, Object> getClienteCodigoValidar(String correo, String codigo) {
        List<Map<String, Object>> results = clienteRepository.getClienteCodigoValidar(correo, codigo);
        
        if (!results.isEmpty()) {
             return results.get(0);
        }

        return Map.of("error", "El código de verificación es inválido o ha caducado.");
    }
    
    
}
