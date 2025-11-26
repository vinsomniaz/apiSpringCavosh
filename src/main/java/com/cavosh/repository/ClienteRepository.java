package com.cavosh.repository;

import com.cavosh.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cliente> getCliente(String correo, String passwordd) {
        String sql = "CALL sp_getCliente(?, ?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong("id"));
            cliente.setNombres(rs.getString("Nombres"));
            cliente.setCorreo(rs.getString("Correo"));
            cliente.setPasswordd(rs.getString("Passwordd"));
            return cliente;
        }, correo, passwordd);
    }

    public List<Map<String, Object>> setCliente(Long id, String nombres, String correo, String passwordd) {
        String sql = "CALL sp_setCliente(?, ?, ?, ?)";
        return jdbcTemplate.queryForList(sql, id, nombres, correo, passwordd);
    }

    public List<Map<String, Object>> getClienteCodigo(String correo) {
        String sql = "CALL sp_getClienteCodigo(?)";
        return jdbcTemplate.queryForList(sql, correo);
    }

    public List<Map<String, Object>> getClienteCodigoValidar(String correo, String codigo) {
        String sql = "CALL sp_getCodigoVerificacion(?, ?)";
        return jdbcTemplate.queryForList(sql, correo, codigo);
    }

}
