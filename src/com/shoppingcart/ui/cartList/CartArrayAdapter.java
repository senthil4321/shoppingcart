package com.shoppingcart.ui.cartList;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.demo.shoppingcart.R;
import com.demo.shoppingcart.model.ModelProvider;
import com.demo.shoppingcart.model.Product;

public class CartArrayAdapter extends ArrayAdapter<Product> {

	private final int layoutResourceId;

	public CartArrayAdapter(Context context, int layoutResourceId,
			List<Product> list) {
		super(context, layoutResourceId, list);
		this.layoutResourceId = layoutResourceId;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ListItemViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(layoutResourceId, null);

			holder = new ListItemViewHolder();
			holder.itemText = (TextView) convertView
					.findViewById(R.id.list_row_cart_item_name);
			holder.itemCount = (TextView) convertView
					.findViewById(R.id.list_row_cart_item_count);
			convertView.setTag(holder);
		} else {
			holder = (ListItemViewHolder) convertView.getTag();
		}

		// Get the items from the model
		Product modelItem = (Product)getItem(position);
		String title = modelItem.getName();
		holder.itemText.setText(title);
		Integer resid = ModelProvider.getInstance().getCartItems().get(modelItem.getId());
		if(resid !=null)
		holder.itemCount.setText(""+resid);
		return convertView;
	}

	private class ListItemViewHolder {
		public TextView itemText;
		public TextView itemCount;
	}

}
