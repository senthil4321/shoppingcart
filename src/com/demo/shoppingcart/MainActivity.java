package com.demo.shoppingcart;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.demo.shoppingcart.model.InputDataModel;
import com.demo.shoppingcart.model.ModelProvider;
import com.demo.shoppingcart.model.jsonmapper.JsonPojoMapper;
import com.shoppingcart.ui.cartList.CartListFragement;
import com.shoppingcart.ui.categoryList.CategoryList;
import com.shoppingcart.ui.categoryList.OnItemChangedListener;

public class MainActivity extends FragmentActivity implements
		OnItemChangedListener {

	private ViewGroup categoryLayout, cartLayout;
	private boolean newDataAvailableFlag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadData();
		
		if (savedInstanceState != null) {
		} else {
			categoryLayout = (ViewGroup) findViewById(R.id.fragment_category_list_container);
			if (categoryLayout != null) {

				CategoryList categoryFragment = new CategoryList();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(categoryLayout.getId(),
						categoryFragment, CategoryList.class.getName());
				fragmentTransaction.commit();
			}
			cartLayout = (ViewGroup) findViewById(R.id.fragment_cart_list_container);
			if (categoryLayout != null) {

				CartListFragement cartFragment = new CartListFragement();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(cartLayout.getId(), cartFragment,
						CartListFragement.class.getName());
				fragmentTransaction.commit();
			}
		}
	}

	private void loadData() {
		if(newDataAvailableFlag)
		{
		InputDataModel inputDataObject = new JsonPojoMapper(this)
		.getDataModelFromJsonFile();
		ModelProvider.getInstance().setInputDataObject(inputDataObject);
		newDataAvailableFlag = false;//should be enabled after the web service asyn task is complete
		}
	}

	@Override
	public void onItemChange(String item, int position,String eventSource) {
		processEventOnModelData(item, position,eventSource);
		notifyEventToUI(item, position,eventSource);
	}

	private void notifyEventToUI(String item, int position, String source) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		CartListFragement cartListFragement = (CartListFragement) fragmentManager
				.findFragmentByTag(CartListFragement.class.getName());

		CategoryList categoryList = (CategoryList) fragmentManager
				.findFragmentByTag(CategoryList.class.getName());


		if (cartListFragement != null) {
			cartListFragement.onItemChange(item, position,this.getClass().getName());
		}
		if (categoryList != null) {
			categoryList.onItemChange(item, position,this.getClass().getName());
		}
	}

	private void processEventOnModelData(String item, int position,
			String source) {
		if (source.equals(CartListFragement.class.getName())) {
			if (ModelProvider.getInstance().getCartItems().containsKey(item)) {
				ModelProvider.getInstance().getCartItems().remove(item);
			}
		} else {
			if (ModelProvider.getInstance().getCartItems().containsKey(item)) {
				ModelProvider
						.getInstance()
						.getCartItems()
						.put(item,
								ModelProvider.getInstance().getCartItems()
										.get(item) + 1);
			} else {
				ModelProvider.getInstance().getCartItems().put(item, 1);
			}
		}

	}
@Override
protected void onResume() {
	super.onResume();
	notifyEventToUI(null, 0, this.getClass().getName());
}
}
