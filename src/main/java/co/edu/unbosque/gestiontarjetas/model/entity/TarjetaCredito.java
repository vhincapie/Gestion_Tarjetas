package co.edu.unbosque.gestiontarjetas.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class TarjetaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;

    @Column(name = "fecha_vencimiento")
    private String fechaVencimiento;

    @Column(name = "franquicia")
    private String franquicia;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cupo_total")
    private BigDecimal cupoTotal;

    @Column(name = "cupo_disponible")
    private BigDecimal cupoDisponible;

    @Column(name = "cupo_utilizado")
    private BigDecimal cupoUtilizado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public TarjetaCredito() {
    }

    public TarjetaCredito(Long id, String numeroTarjeta, String fechaVencimiento, String franquicia, String estado, BigDecimal cupoTotal, BigDecimal cupoDisponible, BigDecimal cupoUtilizado, Cliente cliente) {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
