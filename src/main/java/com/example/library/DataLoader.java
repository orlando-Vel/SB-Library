package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya hay libros en la base de datos
        if (bookRepository.count() == 0) {
            // Crear libros de prueba
            Book book1 = new Book();
            book1.setTitle("El Principito");
            book1.setAuthor("Antoine de Saint-Exupéry");
            book1.setIsbn("978-3-16-148410-0");
            book1.setCategory("Ficción");

            Book book2 = new Book();
            book2.setTitle("Cien Años de Soledad");
            book2.setAuthor("Gabriel García Márquez");
            book2.setIsbn("978-0-14-118499-6");
            book2.setCategory("Ficción");

            Book book3 = new Book();
            book3.setTitle("1984");
            book3.setAuthor("George Orwell");
            book3.setIsbn("978-0-452-28423-4");
            book3.setCategory("Ciencia Ficción");

            // Guardar los libros en la base de datos
            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);

            System.out.println("Libros de prueba insertados correctamente.");
        } else {
            System.out.println("Ya existen libros en la base de datos.");
        }
    }
}