package com.smoothstack.lms.common.util;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.model.Loans;

@Service
@SuppressWarnings("unchecked")
public class LoopTerminator {

	@Autowired
	private LoopTerminator loopTerminator;

	private static LoopTerminator sloopTerminator;

	@Bean
	public LoopTerminator getInstance() {
		return new LoopTerminator();
	}

	@PostConstruct
	public void init() {
		sloopTerminator = loopTerminator;
	}

	/**
	 * Apply loop Terminator to an object
	 * <p>
	 * Can be used as a lambda function or automated loop-terminate
	 * </p>
	 * 
	 * @param object object to be loop-terminated
	 * @param <T>    any support type
	 * @return loop-terminated object
	 */

	public static <T> T apply(T object) {

		if (object instanceof Loans)
			return (T) sloopTerminator.loans((Loans) object);

		if (object instanceof Book)
			return (T) sloopTerminator.book((Book) object);

		if (object instanceof Copies)
			return (T) sloopTerminator.copies((Copies) object);

		if (object instanceof Collection<?>)
			((Collection) object).forEach(LoopTerminator::apply);

		return object;

	}

	@PersistenceContext
	private EntityManager entityManager;

	public Loans loans(Loans loans) {
		book(loans.getBook());
		entityManager.detach(loans);
		return loans;
	}

	public Copies copies(Copies copies) {
		book(copies.getBook());

		entityManager.detach(copies);
		return copies;
	}

	public Book book(Book book) {
		entityManager.detach(book.getPublisher());
		book.getPublisher().setPublisherBookSet(null);
		book.getBookAuthorSet().forEach(eachAuthor -> {
			entityManager.detach(eachAuthor);
			eachAuthor.setAuthorBookSet(null);
		});
		book.getBookGenreSet().forEach(eachGenre -> {
			entityManager.detach(eachGenre);
			eachGenre.setGenreBookSet(null);
		});
		return book;
	}

}
