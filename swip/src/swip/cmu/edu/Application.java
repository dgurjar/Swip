/**
 * 
 */
package swip.cmu.edu;

import java.util.ArrayList;
import java.util.List;

/**
 * AN application installed in the system.
 * @author Daniel Langdon
 */
public class Application
{
	/**
	 * Container as a basic tuple to store the permission information for this app.
	 */
	public static class Request
	{
		Permission permission;
		boolean granted;
		String reason;
		
		public Request(Permission permission, String reason) throws Exception
		{
			super();
			this.permission = permission;
			this.granted = false;
			this.reason = reason;
			
			if(permission == null)
				throw(new Exception(reason));
		}

		/**
		 * @return the granted
		 */
		public boolean isGranted()
		{
			return granted;
		}

		/**
		 * @param granted the granted to set
		 */
		public void setGranted(boolean granted)
		{
			this.granted = granted;
		}

		/**
		 * @return the permission
		 */
		public Permission getPermission()
		{
			return permission;
		}

		/**
		 * @return the reason
		 */
		public String getReason()
		{
			return reason;
		}

		public boolean isRiskingPrivacy()
		{
			return /*granted &&*/ permission.isRisky();
		}
	}
	
	private String name;
	private int icon;
	private String description;
	private List<Request> requests;

	public void revertToDefaults()
	{
		int i=0;
		for(Request r: requests)
		{
			r.granted = r.permission.acceptByDefault();
			i++;
		}
		int a = i;
	}
	
	public boolean isRiskingPrivacy()
	{
		for(Request r: requests)
		{
			if(r.granted && r.permission.isRisky())
				return true;
		}
		return false;
	}
	
	public boolean isLoosingFeatures()
	{
		for(Request r: requests)
		{
			if(!r.granted)
				return true;
		}
		return false;
	}
	
	public Application(String name, int icon, String description)
	{
		super();
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.requests = new ArrayList<Request>();
	}
	
	public void addRequest(Request r)
	{
		requests.add(r);
	}
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return the icon
	 */
	public int getIcon()
	{
		return icon;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * @return the permissions
	 */
	public List<Request> getPermissionRequests()
	{
		return requests;
	}
	
}
