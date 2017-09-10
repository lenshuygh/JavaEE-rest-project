package com.lens.bookstore.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Created by lens on 13/08/2017.
 */

//everything under 'api' is a jax-rs endpoint
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
}
