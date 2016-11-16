package ca.ulaval.glo4002.atm_api.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.atm_api.domain.accounts.AccountNotFoundException;

@Provider
public class AccountNotFoundExceptionMapper implements ExceptionMapper<AccountNotFoundException> {

    @Override
    public Response toResponse(AccountNotFoundException exception) {
        return Response.status(Status.NOT_FOUND).build();
    }

}
