package swip.cmu.edu;

/**
 * Class to identify a single permission value.
 * @author Daniel
 */
public class Permission
{
	private String name;
	private String description;
	private boolean acceptByDefault;
	private boolean risky;
	
	/**
	 * @return the risky
	 */
	public boolean isRisky()
	{
		return risky;
	}

	public Permission(String name, String description, boolean risky)
	{
		super();
		this.name = name;
		this.description = description;
		this.acceptByDefault = !risky;
		this.risky = risky;
	}

	/**
	 * @return The permission legible name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return Description of this permission, including possible risks.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return True if the default option for this permission is to accept it.
	 */
	public boolean acceptByDefault()
	{
		return acceptByDefault;
	}
	
	/**
	 * @param acceptByDefault If the permission should be accepted by default.
	 */
	public void setAcceptByDefault(boolean acceptByDefault)
	{
		this.acceptByDefault = acceptByDefault;
	}
}
