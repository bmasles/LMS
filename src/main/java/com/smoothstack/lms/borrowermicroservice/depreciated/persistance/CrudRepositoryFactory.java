package com.smoothstack.lms.borrowermicroservice.depreciated.persistance;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Entity;
import com.smoothstack.lms.borrowermicroservice.depreciated.context.util.EntityClassInfo;
import com.smoothstack.lms.borrowermicroservice.depreciated.model.Book;
import com.smoothstack.lms.borrowermicroservice.depreciated.model.Borrower;
import com.smoothstack.lms.borrowermicroservice.depreciated.model.Library;
import com.smoothstack.lms.borrowermicroservice.depreciated.model.Loans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class CrudRepositoryFactory {

    private static Map<Class, CrudRepository> repositoryMap = new HashMap<>();

    public static void registerRepository(Class clazz, CrudRepository crudRepository) {
        repositoryMap.put(clazz, crudRepository);
    }

    public static <R> CrudRepository<R> getRepository(Class<R> clazz) {
        Debug.printf("Lookup repository for %s\n", clazz.getSimpleName());
        if (repositoryMap.containsKey(clazz))
            return (CrudRepository<R>) repositoryMap.get(clazz);

        try {
            EntityClassInfo eci = EntityClassInfo.of(clazz);

            Optional<Entity> entity = eci.getAnnotation(Entity.class);

            if (entity.isPresent()) {
                CrudRepository<R> repository = (CrudRepository<R>) entity.get().repository().getDeclaredConstructor(Class.class).newInstance(clazz);
                //repository.init(clazz);
                repositoryMap.put(clazz, repository);
            } else {
                throw new InstantiationException("Missing @Entity for" + clazz.getSimpleName());
            }

            return (CrudRepository<R>) repositoryMap.get(clazz);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Debug.printException(e);
        }

        return null;
    }

    @Bean(name = "BookRepository")
    public CrudRepository<Book> bookRepository() {
        return getRepository(Book.class);
    }

    @Bean(name = "LibraryRepository")
    public CrudRepository<Library> libraryRepository() {
        return getRepository(Library.class);
    }

    @Bean(name = "BorrowerRepository")
    public CrudRepository<Borrower> borrowerRepository() {
        return getRepository(Borrower.class);
    }

    @Bean(name = "LoanRepository")
    public CrudRepository<Loans> loanRepository() {
        return getRepository(Loans.class);
    }
}
