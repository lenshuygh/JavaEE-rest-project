package com.lens.bookstore.rest;

import com.lens.bookstore.model.Book;
import com.lens.bookstore.model.Language;
import com.lens.bookstore.repository.BookRepository;
import com.lens.bookstore.util.IsbnGenerator;
import com.lens.bookstore.util.NumberGenerator;
import com.lens.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static org.junit.Assert.assertEquals;

/**
 * Created by lens on 14/08/2017.
 */
@RunWith(Arquillian.class)
@RunAsClient
public class BookEndpointTest {



    @Test
    public void createBook(@ArquillianResteasyResource("api/books") WebTarget webTarget) throws Exception{
        // test counting books
        Response response = webTarget.path("count").request().get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());

        // test findAll

        response = webTarget.request(APPLICATION_JSON).get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());

        //create a book

        Book book = new Book("isbn","a title",12F,123,Language.DUTCH,new Date(),"http://blah","description");
        response = webTarget.request(APPLICATION_JSON).post(Entity.entity(book,APPLICATION_JSON));
        assertEquals(CREATED.getStatusCode(), response.getStatus());
    }



    @Deployment(testable = false)
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(WebArchive.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(BookRepository.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addClass(TextUtil.class)
                .addClass(BookEndpoint.class)
                .addClass(JAXRSConfiguration.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }
}
