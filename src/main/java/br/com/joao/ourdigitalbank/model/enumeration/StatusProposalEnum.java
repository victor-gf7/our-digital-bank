package br.com.joao.ourdigitalbank.model.enumeration;

public enum StatusProposalEnum {

    WAITING("WAITING"), ACCEPTED("ACCEPTED"), REFUSED("REFUSED"), LIBERATED("LIBERATED"), SECONDATTEMPT("SECONDATTEMPT") ;

    private String value;

    private StatusProposalEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
