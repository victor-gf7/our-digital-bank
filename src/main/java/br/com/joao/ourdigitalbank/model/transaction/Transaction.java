package br.com.joao.ourdigitalbank.model.transaction;

import br.com.joao.ourdigitalbank.controller.transaction.dto.TransactionRequest;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    private double transferValue;

    private LocalDateTime realizationDate;

    @NotBlank
    private Long docId;

    @NotBlank
    private int sourceBank;

    @NotBlank
    private int sourceAccount;

    @NotBlank
    private int sourceAgency;

    @NotBlank
    private int nsu;

    @NotBlank
    private int destinationAccount;

    @NotBlank
    private int destinationAgency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(double transferValue) {
        this.transferValue = transferValue;
    }

    public LocalDateTime getRealizationDate() {
        return realizationDate;
    }

    public void setRealizationDate(LocalDateTime realizationDate) {
        this.realizationDate = realizationDate;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public int getSourceBank() {
        return sourceBank;
    }

    public void setSourceBank(int sourceBank) {
        this.sourceBank = sourceBank;
    }

    public int getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(int sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public int getSourceAgency() {
        return sourceAgency;
    }

    public void setSourceAgency(int sourceAgency) {
        this.sourceAgency = sourceAgency;
    }

    public int getNsu() {
        return nsu;
    }

    public void setNsu(int nsu) {
        this.nsu = nsu;
    }

    public int getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(int destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public int getDestinationAgency() {
        return destinationAgency;
    }

    public void setDestinationAgency(int destinationAgency) {
        this.destinationAgency = destinationAgency;
    }

    public TransactionRequest convertEntityToDTO() {
        return new ModelMapper().map(this, TransactionRequest.class);
    }
}
