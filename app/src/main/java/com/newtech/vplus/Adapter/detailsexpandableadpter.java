package com.newtech.vplus.Adapter;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.newtech.vplus.Model.shadeclass;
import com.newtech.vplus.R;


//For expandable list view use BaseExpandableListAdapter
public class detailsexpandableadpter extends BaseExpandableListAdapter {
	private Context _context;
	public List<String> header; // header titles
	// Child data in format of header title, child title
	private Map <String, List<shadeclass>> child;


	public detailsexpandableadpter(Context context, List<String> listDataHeader,
								   Map<String, List<shadeclass>> listChildData) {
		this._context = context;
		this.header = listDataHeader;
		this.child = listChildData;

	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.child.get(this.header.get(groupPosition)).get(
				childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		final RegHolder holder;
		final int gpindex=groupPosition;
		final shadeclass childText = (shadeclass) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.oredrlistsub, parent, false);
			holder = new RegHolder();
			holder.child_text1 = (TextView) convertView.findViewById(R.id.stxt);
			holder.child_text2 = (TextView) convertView.findViewById(R.id.qtytxt);

			convertView.setTag(holder);
		}else{
			holder = (RegHolder)convertView.getTag();
		}

		if (childText.PK_SHADE=="PK_SHADE" && childText.CL_QTY=="CL_QTY")
		{
			holder.child_text1.setText(Html.fromHtml("<b><font color='#F44336'>SHADE</font></b>"));
			holder.child_text2.setText(Html.fromHtml("<b><font color='#F44336'>QTY</font></b>"));
		}
		else
		{
			holder.child_text1.setText(childText.PK_SHADE);
			holder.child_text2.setText(String.valueOf(childText.CL_QTY));
		}

		return convertView;
	}

	private void DisplayMsg(final String s) {
		Toast.makeText(_context, s.toString(), Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.child.get(this.header.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.header.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.header.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		final HederHolder holder;

		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.l_sub1, parent, false);
			holder = new HederHolder();
			holder.HederHolder1 = (TextView) convertView.findViewById(R.id.header);
			holder.HederHolder2 = (TextView) convertView.findViewById(R.id.headerqty);
			convertView.setTag(holder);
		}else{
			holder = (HederHolder)convertView.getTag();
		}

		holder.HederHolder1.setText(headerTitle);
		holder.HederHolder2.setText(String.valueOf(getChildrenCount(groupPosition)-1));

		if (isExpanded) {
			holder.HederHolder2.setTypeface(null, Typeface.BOLD);
			holder.HederHolder2.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_up, 0);
		} else {
			holder.HederHolder2.setTypeface(null, Typeface.NORMAL);
			holder.HederHolder2.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_down, 0);
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	static class RegHolder
	{
		TextView child_text1;
		TextView child_text2;

	}

	static class HederHolder
	{
		TextView HederHolder1;
		TextView HederHolder2;
	}
}