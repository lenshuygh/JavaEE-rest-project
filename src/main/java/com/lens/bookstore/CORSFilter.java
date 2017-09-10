package com.lens.bookstore;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by lens on 19/08/2017.
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {
    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        String reqHeader = requestContext.getHeaderString("Access-Control-Request-Headers");
        if (reqHeader != null && reqHeader != "") {
            responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", reqHeader);
        }
    }
}