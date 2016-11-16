package ca.ulaval.glo4002.atm_api.application;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<Class<?>, Object> services = new HashMap<>();

    public static <T> void registerSingleton(Class<T> contract, T service) {
        if (services.containsKey(contract)) {
            throw new CannotRegisterContractTwiceException(contract);
        }

        services.put(contract, service);
    }

    @SuppressWarnings("unchecked")
    public static <T> T resolve(Class<? extends T> contract) {
        if (!services.containsKey(contract)) {
            throw new UnableResolveServiceException(contract);
        }

        return (T) services.get(contract);
    }

    private ServiceLocator() {
    }

    public static void reset() {
        services.clear();
    }

}
