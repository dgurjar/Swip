package swip.cmu.edu.activity;

import java.util.ArrayList;
import java.util.List;

import swip.cmu.edu.Category;
import swip.cmu.edu.Permission;
import swip.cmu.edu.PermissionManager;
import swip.cmu.edu.R;
import swip.cmu.edu.activity.ModifyPermissionsActivity.PermissionRow;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Dev Gurjar 
 */
public class ModifyDefaultsActivity extends Activity {  

	private int lastGroupOpened;

	/* ArrayLists for the groups and for the children */
	private ArrayList<String> groups;
	private ArrayList<ArrayList<Permission>> childs;
	private myExpandableAdapter adapter;
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {  

		super.onCreate(savedInstanceState);  
		setContentView(R.layout.modify_defaults);
		setTitle("Manage Permission Defaults");

		/* Initially we don't want to collapse a child */
		lastGroupOpened = -1;

		final ExpandableListView l = (ExpandableListView) findViewById(R.id.ExpandableListView);

		/* Populate global arrayLists */
		loadData();
		
		/* Make it so that only one group can be expanded at once */
		l.setOnGroupExpandListener(new OnGroupExpandListener(){
			@Override
			public void onGroupExpand(int arg0) {
				if(lastGroupOpened < 0 || lastGroupOpened != arg0)
					l.collapseGroup(lastGroupOpened);
				lastGroupOpened = arg0;
			}
		});
		
		/* When a child is clicked, we want to go to the corresponding activity */
		l.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, 
					int childPosition, long id) {
				Intent intent = new Intent(getBaseContext(), PermissionDetailsActivity.class);
				PermissionManager.setSelectedPermission(childs.get(groupPosition).get(childPosition));
				startActivityForResult(intent, 0);
				return false;
			}
			
		});

		adapter = new myExpandableAdapter(this, groups, childs);
		l.setAdapter(adapter);

		
		// Wait for actions on the buttons.
		Button modify = (Button) findViewById(R.id.revert);
		modify.setOnClickListener(new View.OnClickListener() 
		{
	        public void onClick(View v) 
	        {
	        	for(ArrayList<Permission> p: childs){
	        		for(Permission perm: p)
	        			perm.setAcceptByDefault(!perm.isRisky());
	        	}
	        	startActivity(getIntent()); finish();
	        }
	    });
		Button accept = (Button) findViewById(R.id.accept);
		accept.setOnClickListener(new View.OnClickListener() 
		{
	        public void onClick(View v) 
	        {
	        	// Update the app status and go back to the first screen.
	        	//PermissionManager.installSelected();
	    		startActivity(new Intent(getBaseContext(), MainMenuActivity.class));
	        	finish();
	        }
	    });


	}



	public class myExpandableAdapter extends BaseExpandableListAdapter { 

		private ArrayList<String> groups;

		private ArrayList<ArrayList<Permission>> children;

		private Context context;

		public myExpandableAdapter(Context context, ArrayList<String> groups, ArrayList<ArrayList<Permission>> children) {
			this.context = context;
			this.groups = groups;
			this.children = childs;
		}


		@Override
		public boolean areAllItemsEnabled()
		{
			return true;
		}


		@Override
		public Permission getChild(int groupPosition, int childPosition) {
			return children.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}


		@Override
		/* Change this! */
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild,View convertView, ViewGroup parent) {

			final Permission child = (Permission) getChild(groupPosition, childPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.exandablelistview_child, null);
			}
			
			final ImageView imgchld = (ImageView) convertView.findViewById(R.id.chld_img);
			imgchld.setImageResource(R.drawable.warn20);
			if(!child.isRisky())
				imgchld.setVisibility(View.GONE);
			else
				imgchld.setVisibility(View.VISIBLE);

			
			final CheckBox cb = (CheckBox)convertView.findViewById( R.id.check );
	        if(child.acceptByDefault())
	        	cb.setChecked(true);
	        else
	        	cb.setChecked(false);
	        
	        cb.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                 boolean checked = cb.isChecked();
	                 child.setAcceptByDefault(checked);
	                 /* TODO: ADD code here if we want to dynamically change the icon
	                 if(!checked && child.isRisky())
	                	 
	                 If it is checked and risky */
	                 
	                	 
	            }
	        });
	        
			TextView childtxt = (TextView) convertView.findViewById(R.id.TextViewChild01);
			childtxt.setText(child.getName());
			

			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return children.get(groupPosition).size();
		}

		@Override
		public String getGroup(int groupPosition) {
			return groups.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return groups.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

			String group = (String) getGroup(groupPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.expandablelistview_group, null);
			}

			TextView grouptxt = (TextView) convertView.findViewById(R.id.TextViewGroup);

			grouptxt.setText(group);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}

	}

	
	/**
	 * Get data from the model, place it into globals
	 */
	private void loadData(){
		groups= new ArrayList<String>();
		childs= new ArrayList<ArrayList<Permission>>();
		
		/* Access the global permission data structures */
		PermissionManager.initialize();
		List<Category> cats = PermissionManager.categories;
		
		int i = 0;
		/* Insert the group and child names */
		for(Category c: cats){
			groups.add(c.getName());
			childs.add(new ArrayList<Permission>());
			for(Permission p: c.getPermissions())
				childs.get(i).add(p);
			i++;
		}
	}
	
	/**
	 * Update any changes in the request being observed.
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		adapter.notifyDataSetChanged();
	}
	
}