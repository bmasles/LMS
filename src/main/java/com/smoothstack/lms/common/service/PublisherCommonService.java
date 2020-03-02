package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.exception.DependencyException;
import com.smoothstack.lms.common.model.Publisher;
import com.smoothstack.lms.common.repository.PublisherCommonRepository;
import com.smoothstack.lms.common.repository.RepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public abstract class PublisherCommonService implements CommonService<Publisher, Long> {


    private PublisherCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setPublisherCommonRepository(PublisherCommonRepository authorCommonRepository) {
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
    public JpaRepository<Publisher, Long> getJpaRepository() {
        return authorCommonRepository;
    }

    @Override
    public void afterSave(Publisher publisher) {
        publisher.getPublisherBookSet().forEach(book->{
            if (book.getPublisher()!=null && !book.getPublisher().equals(publisher))
                throw new DependencyException("Book has other publisher!");
            book.setPublisher(publisher);
            RepositoryAdapter.getBookRepository().save(book);
        });
    }

    @Override
    public boolean beforeDelete(Publisher publisher) {

        publisher.getPublisherBookSet().forEach(book->{
            if (book.getPublisher()!=null && book.getPublisher().equals(publisher))
                book.setPublisher(null);
            RepositoryAdapter.getBookRepository().save(book);
        });


        publisher.getPublisherBookSet().clear();

        RepositoryAdapter.getPublisherRepository().save(publisher);

        return true;
    }
}
