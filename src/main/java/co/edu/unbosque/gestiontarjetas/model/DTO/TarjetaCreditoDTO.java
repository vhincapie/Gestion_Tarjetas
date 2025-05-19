package co.edu.unbosque.gestiontarjetas.model.DTO;

import java.math.BigDecimal;

public class TarjetaCreditoDTO {

    private Long id;
    private String numeroTarjeta;
    private String fechaVencimiento;
    private String franquicia;
    private String estado;
    private BigDecimal cupoTotal;
    private BigDecimal cupoDisponible;
    private BigDecimal cupoUtilizado;
    private ClienteDTO cliente;

    public TarjetaCreditoDTO() {
    }

    public TarjetaCreditoDTO(Long id, String numeroTarjeta, String fechaVencimiento, String franquicia, String estado, BigDecimal cupoTotal, BigDecimal cupoDisponible, BigDecimal cupoUtilizado, ClienteDTO clienteId) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.franquicia = franquicia;
        this.estado = estado;
        this.cupoTotal = cupoTotal;
        this.cupoDisponible = cupoDisponible;
        this.cupoUtilizado = cupoUtilizado;
        this.cliente = cliente;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getCupoTotal() {
        return cupoTotal;
    }

    public void setCupoTotal(BigDecimal cupoTotal) {
        this.cupoTotal = cupoTotal;
    }

    public BigDecimal getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(BigDecimal cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public BigDecimal getCupoUtilizado() {
        return cupoUtilizado;
    }

    public void setCupoUtilizado(BigDecimal cupoUtilizado) {
        this.cupoUtilizado = cupoUtilizado;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
}
