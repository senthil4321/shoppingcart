package com.shoppingcart.ui.categoryList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.demo.shoppingcart.R;
import com.demo.shoppingcart.model.ModelProvider;

@SuppressLint("NewApi")
public class CategoryList extends Fragment implements OnItemChangedListener{
	CategoryListAdapter listAdapter;
	ExpandableListView expListView;
	private OnItemChangedListener onItemAddedListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity != null && activity instanceof OnItemChangedListener) {
			onItemAddedListener = (OnItemChangedListener) activity;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.category_fragement, container,
				false);
		expListView = (ExpandableListView) view
				.findViewById(R.id.expandableListView);
		listAdapter = new CategoryListAdapter(getActivity(), ModelProvider.getInstance().getCategoryProductData() , ModelProvider.getInstance().getCategoryHeaderList());
		expListView.setAdapter(listAdapter);

		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				onItemAddedListener.onItemChange(
						ModelProvider.getInstance().getCategoryProductData().get(ModelProvider.getInstance().getCategoryHeaderList().get(groupPosition))
								.get(childPosition).getId(), childPosition,this.getClass().getName());
				return false;
			}
		});
		return view;
	}





	@Override
	public void onItemChange(String item, int position,String source) {
		listAdapter.notifyDataSetChanged();	
	}



}