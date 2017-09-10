package com.lens.bookstore.repository;

import com.lens.bookstore.model.Book;
import com.lens.bookstore.model.Language;
import com.lens.bookstore.util.NumberGenerator;
import com.lens.bookstore.util.TextUtil;
import com.lens.bookstore.util.IsbnGenerator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by lens on 13/08/2017.
 */

@RunWith(Arquillian.class)
public class BookRepositoryTest {
    @Inject
    private BookRepository bookRepository;


    //we expect an exception because the @NotNull validation fails on the title field
    @Test(expected = Exception.class)
    public void createInvalidBook(){
        Book book = new Book("isbn",null,12F,123,Language.DUTCH,new Date(),"http://blah","description");
        book = bookRepository.create(book);
    }

    @Test(expected = Exception.class)
    public void findWithInvalidId(){
        bookRepository.find(null);
    }

    @Test
    public void create() throws Exception{
        //test counting books
        assertEquals(Long.valueOf(0),bookRepository.countAll());
        assertEquals(0,bookRepository.findAll().size());
        //create a book
        Book book = new Book("isbn"," a  title",12F,123,Language.DUTCH,new Date(),"http://blah","description");
        book = bookRepository.create(book);
        Long bookId = book.getId();

        //check created book
        assertNotNull(bookId);

        //find created book
        Book bookFound = bookRepository.find(bookId);

        //check found book
        assertEquals(bookFound.getTitle()," a title");
        assertTrue(bookFound.getIsbn().startsWith("13"));

        //test counting books
        assertEquals(Long.valueOf(1),bookRepository.countAll());
        assertEquals(1,bookRepository.findAll().size());

        //delete book from db
        bookRepository.delete(bookId);

        //test counting books
        assertEquals(Long.valueOf(0),bookRepository.countAll());
        assertEquals(0,bookRepository.findAll().size());
    }

    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(TextUtil.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml","persistence.xml");

    }
}
