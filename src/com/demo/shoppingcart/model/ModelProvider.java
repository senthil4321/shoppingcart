package com.demo.shoppingcart.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ModelProvider {
	private static final ModelProvider instance = new ModelProvider();

	HashMap<String, Integer> cartItemCount = new HashMap<String, Integer>();
	HashMap<String, Product> productHashMap = new HashMap<String, Product>();
	InputDataModel inputDataObject;
	HashMap<String, List<Product>> categoryProductMap = new HashMap<String, List<Product>>();

	private List<String> categoryListHeader = new ArrayList<String>();

	public static ModelProvider getInstance() {
		return instance;
	}

	public HashMap<String, Integer> getCartItems() {
		return cartItemCount;
	}

	public void setInputDataObject(InputDataModel inputDataObj) {
		inputDataObject = inputDataObj;
		prepareListData();
	}

	public InputDataModel getInputDataObject() {
		return inputDataObject;
	}

	private void prepareListData() {
		updateProductObject();
		updateCategoryObject();
	}

	private void updateCategoryObject() {
		
		categoryListHeader.clear();
		List<Category> categoryList = inputDataObject.getCategories();
		for (Iterator<Category> iterator = categoryList.iterator(); iterator
				.hasNext();) {
			Category category = (Category) iterator.next();
			categoryListHeader.add(category.getName());
			List<String> products = category.getProducts();
			categoryProductMap.put(category.getName(),
					getProducetNameList(products));
		}
		Collections.sort(categoryListHeader);
	}

	private void updateProductObject() {
		productHashMap.clear();
		List<Product> productList = inputDataObject.getProducts();
		for (Iterator<Product> iterator = productList.iterator(); iterator
				.hasNext();) {
			Product product = (Product) iterator.next();
			productHashMap.put(product.getId(), product);
		}
	}

	public List<Product> getProducetNameList(List<String> productIds) {
		List<Product> productNameList = new ArrayList<Product>();
		Map<String, String> productNameHashMap = new HashMap<String, String>();
		for (Iterator<String> iterator = productIds.iterator(); iterator
				.hasNext();) {
			String productId = (String) iterator.next();
			
			if (productHashMap.containsKey(productId)) {
				
				productNameHashMap.put( productId,productHashMap.get(productId).getName());
			}
		}
		ValueComparator vc =  new ValueComparator(productNameHashMap);
		Map<String, String> productNameTreeMap = new TreeMap<String, String>(vc);//to sort based on product name
		productNameTreeMap.putAll(productNameHashMap);
		for (Map.Entry<String, String> entry : productNameTreeMap.entrySet())
		{
			productNameList.add(productHashMap.get(entry.getKey())) ; 
		}
		return productNameList;
	}
	class ValueComparator implements Comparator<String> {
		 
	    Map<String, String> map;
	 
	    public ValueComparator(Map<String, String> base) {
	        this.map = base;
	    }
	 
	    public int compare(String a, String b) {
	        if (map.get(a) .compareTo(map.get(b) ) >0) {
	            return 1;
	        } else {
	            return -1;
	        } // returning 0 would merge keys 
	    }}
	public List<String> getCategoryHeaderList() {
		return categoryListHeader;
	}

	public HashMap<String, List<Product>> getCategoryProductData() {
		return categoryProductMap;
	}

	public Integer getProductPrice(String productId) {
		Integer price = -1;
		if (productHashMap.containsKey(productId)) {
			price = productHashMap.get(productId).getPrice();
		}
		return price;
	}
}
