/**
 * 
 */
package swip.cmu.edu;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Daniel Langdon
 */
public class AppListAdapter extends BaseAdapter
{
	private final List<Application> apps;
	private final Activity context;

	public AppListAdapter(List<Application> apps, Activity context)
	{
		super();
		this.apps = apps;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount()
	{
		return apps.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position)
	{
		return apps.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position)
	{
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.app_select_row, parent, false);
		Application app = apps.get(position);
		
		// Set the application icon.
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		imageView.setImageResource(app.getIcon());
		
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(app.getName());

		return rowView;
	}
}