package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() {
        // Crear un libro
        Book book = new Book();
        book.setTitle("Libro de Prueba");
        book.setAuthor("Autor de Prueba");
        book.setIsbn("123-456-789");
        book.setCategory("Prueba");

        // Guardar el libro en la base de datos
        Book savedBook = bookRepository.save(book);

        // Verificar que el libro se guardó correctamente
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    public void testFindByTitleContainingIgnoreCase() {
        // Crear y guardar un libro
        Book book = new Book();
        book.setTitle("Libro de Prueba");
        book.setAuthor("Autor de Prueba");
        book.setIsbn("123-456-789");
        book.setCategory("Prueba");
        bookRepository.save(book);

        // Buscar libros por título
        List<Book> libros = bookRepository.findByTitleContainingIgnoreCase("Prueba");

        // Verificar que se encontró el libro
        assertThat(libros).hasSize(1);
        assertThat(libros.get(0).getTitle()).isEqualTo("Libro de Prueba");
    }
}