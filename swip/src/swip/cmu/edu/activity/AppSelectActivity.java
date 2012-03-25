/**
 * 
 */
package swip.cmu.edu.activity;

import swip.cmu.edu.AppListAdapter;
import swip.cmu.edu.PermissionManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * @author Daniel Langdon
 */
public class AppSelectActivity extends ListActivity
{
	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) 
	{
		super.onCreate(icicle);
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		
		// Use your own layout and point the adapter to the UI elements which contains the label
		switch(PermissionManager.getMode())
		{
		case INSTALLING:
			this.setListAdapter(new AppListAdapter(PermissionManager.uninstalledApps, this));
			break;
		case MODIFYING:
			this.setListAdapter(new AppListAdapter(PermissionManager.installedApps, this));
			this.setTitle("Select An Application to Modify ");
			break;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		PermissionManager.setSelectedApp(position);
		switch(PermissionManager.getMode())
		{
		case INSTALLING:
			startActivity(new Intent(this, ShowRiskActivity.class));
			break;
		case MODIFYING:
			startActivity(new Intent(this, ModifyPermissionsActivity.class));
			break;
		}
	}
}
