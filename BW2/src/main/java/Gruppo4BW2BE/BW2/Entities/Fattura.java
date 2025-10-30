package Gruppo4BW2BE.BW2.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "fattura", indexes = {@Index(columnList = "invoice_date"), @Index(columnList = "amount")})
public class Fattura {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "invoice_number", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "invoice_date", nullable = false)
    private LocalDate data;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false)
    private BigDecimal importo;

    //relazione con cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Cliente cliente;

    //stato come stringa
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    @NotNull
    private StatoFattura stato;


    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public Fattura() {}

    public Fattura(String numero, LocalDate data, BigDecimal importo, StatoFattura stato, Cliente cliente) {
        this.numero = numero;
        this.data = data;
        this.importo = importo;
        this.stato = stato;
        this.cliente = cliente;
    }


    //getter e setter
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public BigDecimal getImporto() { return importo; }
    public void setImporto(BigDecimal importo) { this.importo = importo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public StatoFattura getStato() { return stato; }
    public void setStato(StatoFattura stato) { this.stato = stato; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}


