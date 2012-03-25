/**
 * 
 */
package swip.cmu.edu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Langdon
 *
 */
public class Category
{
	private String name;
	private List<Permission> permissions;
	
	public Category(String name)
	{
		super();
		this.name = name;
		this.permissions = new ArrayList<Permission>();
	}
	
	public void addPermission(Permission p)
	{
		permissions.add(p);
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the permissions
	 */
	public List<Permission> getPermissions()
	{
		return permissions;
	}
	
	
}
