package com.shoppingcart.ui.categoryList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.demo.shoppingcart.R;
import com.demo.shoppingcart.model.ModelProvider;
import com.demo.shoppingcart.model.Product;

public class CategoryListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> listDataHeader = new ArrayList<String>();
	private HashMap<String, List<Product>> listDataChild;

	public CategoryListAdapter(Context context,
			HashMap<String, List<Product>> hashMap,List<String>listDataHeader) {
		this.context = context;
		//this.listDataHeader.addAll(listChildData.keySet());
		this.listDataHeader = listDataHeader;
		this.listDataChild = hashMap;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final Product product = (Product) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.category_list_item,
					null);
		}

		TextView listItem = (TextView) convertView.findViewById(R.id.listItem);
		listItem.setText(product.getName() + "  € "  + product.getPrice());//todo get the currency from web service
		TextView listItemCount = (TextView) convertView
				.findViewById(R.id.listItemCount);
		
		String cartItemCount = "";
		if (ModelProvider.getInstance().getCartItems().get(product.getId()) != null) {
			cartItemCount = ModelProvider.getInstance().getCartItems().get(product.getId()).toString();
		}
		listItemCount.setText(cartItemCount);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.category_list_group,
					null);
		}

		TextView listHeader = (TextView) convertView
				.findViewById(R.id.listHeader);
		listHeader.setTypeface(null, Typeface.BOLD);
		listHeader.setText(headerTitle);

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


}
