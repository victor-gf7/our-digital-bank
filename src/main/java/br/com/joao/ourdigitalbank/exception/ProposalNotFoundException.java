package br.com.joao.ourdigitalbank.exception;

public class ProposalNotFoundException extends Exception {

    public ProposalNotFoundException(){
        super();
    }

    public ProposalNotFoundException(String msg){
        super(msg);
    }

    public ProposalNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }
}
