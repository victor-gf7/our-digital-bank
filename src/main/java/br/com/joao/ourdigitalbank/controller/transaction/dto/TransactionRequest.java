package br.com.joao.ourdigitalbank.controller.transaction.dto;

import br.com.joao.ourdigitalbank.model.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class TransactionRequest extends RepresentationModel<TransactionRequest> {

    @NotBlank(message = "transferValue is a required attribute")
    private double transferValue;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime realizationDate;

    @NotBlank(message = "docId is a required attribute")
    private Long docId;

    @NotBlank(message = "sourceBank is a required attribute")
    private int sourceBank;

    @NotBlank(message = "sourceAccount is a required attribute")
    private int sourceAccount;

    @NotBlank(message = "sourceAgency is a required attribute")
    private int sourceAgency;

    @NotBlank(message = "nsu is a required attribute")
    private int nsu;

    @NotBlank(message = "destinationAccount is a required attribute")
    private int destinationAccount;

    @NotBlank(message = "destinationAgency is a required attribute")
    private int destinationAgency;

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

    public Transaction convertDTOToEntity() {
        return new ModelMapper().map(this, Transaction.class);
    }

}
