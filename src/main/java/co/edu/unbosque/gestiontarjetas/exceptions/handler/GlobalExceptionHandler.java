package co.edu.unbosque.gestiontarjetas.exceptions.handler;


import co.edu.unbosque.gestiontarjetas.exceptions.exceptions.TarjetaCreditoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TarjetaCreditoException.class)
    public ResponseEntity<Map<String, Object>> manejarTarjetaException(TarjetaCreditoException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());
        error.put("estado", HttpStatus.BAD_REQUEST.value());
        error.put("fecha", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarGenerico(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", "Error interno del servidor");
        error.put("detalle", ex.getMessage());
        error.put("estado", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("fecha", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
