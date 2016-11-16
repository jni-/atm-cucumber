package ca.ulaval.glo4002.atm_api.application;

public class UnableResolveServiceException extends RuntimeException {

    public UnableResolveServiceException(Class<?> contract) {
        super("Unable to resolve a service for '" + contract.getCanonicalName() + "'. Did you forget to register it?");
    }

    private static final long serialVersionUID = 1L;

}
