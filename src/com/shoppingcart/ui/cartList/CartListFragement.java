package com.shoppingcart.ui.cartList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.demo.shoppingcart.R;
import com.demo.shoppingcart.model.ModelProvider;
import com.demo.shoppingcart.model.Product;
import com.shoppingcart.ui.categoryList.OnItemChangedListener;

public class CartListFragement extends Fragment implements OnItemClickListener ,OnItemChangedListener{
	private ListView listView;
	private CartArrayAdapter cartArrayAdapter;
	List<Product> list = new ArrayList<Product>();
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
		
		View v = inflater.inflate(R.layout.cart_fragement, container, false);

		listView = (ListView) v.findViewById(R.id.cartListView);
		listView.setOnItemClickListener(this);


		cartArrayAdapter = new CartArrayAdapter(getActivity(),
				R.layout.cart_list_item, list );
		cartArrayAdapter.setNotifyOnChange(true);
		listView.setAdapter(cartArrayAdapter);

		listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

		if (cartArrayAdapter.getCount() > 0) {
			listView.setItemChecked(1, true);
		}
		return v;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position >= 0 && position < listView.getCount()) {
			onItemAddedListener.onItemChange(cartArrayAdapter.getItem(position).getId(),
					position,this.getClass().getName());
		}
	}


	public void onItemChange(String item, int position, String source) {
		Set<String> keySet = ModelProvider.getInstance().getCartItems().keySet();
		list.clear();
		list.addAll(ModelProvider.getInstance().getProducetNameList(new ArrayList<String>(keySet)));
		//Collections.sort(list);
		cartArrayAdapter.notifyDataSetChanged();		
	}

	
}
