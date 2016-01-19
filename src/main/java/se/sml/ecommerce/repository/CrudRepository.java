package se.sml.ecommerce.repository;

import java.util.Collection;

import se.sml.ecommerce.repository.checkedexception.RepositoryException;

public interface CrudRepository<T>
{
	void create(T object) throws RepositoryException;

	T getById(Long id) throws RepositoryException;

	Collection<T> getAll() throws RepositoryException;
	
	T getByName(String object) throws RepositoryException;
		
	void update(Long objectName, String updateProperty, Object updatedValue) throws RepositoryException;
}
