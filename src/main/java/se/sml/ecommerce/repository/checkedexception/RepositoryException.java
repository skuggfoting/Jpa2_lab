package se.sml.ecommerce.repository.checkedexception;

public class RepositoryException extends Exception
{

	private static final long serialVersionUID = -1587405498053999703L;

	public RepositoryException()
	{
		super();
	}	

	public RepositoryException(String message)
	{
		super(message);
	}

	public RepositoryException(Throwable cause)
	{
		super(cause);
	}
	
	public RepositoryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
