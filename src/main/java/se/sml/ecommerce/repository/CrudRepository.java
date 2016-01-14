package se.sml.ecommerce.repository;

import java.util.List;

import se.sml.ecommerce.repository.checkedexception.RepositoryException;

public interface CrudRepository<T>
{
	void create(T object) throws RepositoryException;

	T getById(Long id) throws RepositoryException;

	List<T> getAll() throws RepositoryException;
	
	T getByName(String object) throws RepositoryException;
	
//	void update(T object) throws RepositoryException;
	
	void update(String namen, Object value, String updateProperty) throws RepositoryException;
}
