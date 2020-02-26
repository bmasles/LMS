package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.model.Author;
import com.smoothstack.lms.common.repository.AuthorCommonRepository;
import com.smoothstack.lms.common.repository.RepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class AuthorCommonService implements CommonService<Author,Long>{

    private AuthorCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    private void setAuthorCommonRepository(AuthorCommonRepository authorCommonRepository) {
        this.authorCommonRepository = authorCommonRepository;
    }

    @Autowired
    public void setValidator(@Qualifier("DefaultValidator") Validator validator) {
        this.validator = validator;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public JpaRepository<Author, Long> getJpaRepository() {
        return authorCommonRepository;
    }

    @Override
    public void afterSave(Author author) {
        author.getAuthorBookSet().forEach(book -> {
            book.getBookAuthorSet().add(author);
            RepositoryAdapter.getBookRepository().save(book);
        });
    }

    @Override
    public boolean beforeDelete(Author author) {
        author.getAuthorBookSet().forEach(book -> {
            book.getBookAuthorSet().remove(author);
            RepositoryAdapter.getBookRepository().save(book);
        });
        author.getAuthorBookSet().clear();

        RepositoryAdapter.getAuthorRepository().save(author);
        return true;
    }
}
