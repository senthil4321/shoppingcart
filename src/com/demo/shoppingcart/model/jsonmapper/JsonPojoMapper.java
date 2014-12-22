package com.demo.shoppingcart.model.jsonmapper;

import java.io.IOException;

import android.content.Context;

import com.demo.shoppingcart.model.InputDataModel;
import com.demo.shoppingcart.utils.DataReader;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPojoMapper {
	private Context context;

	public JsonPojoMapper(Context context)
	{
		this.context = context;
	}
	public InputDataModel getDataModelFromJsonFile()  {
		InputDataModel model = new  InputDataModel();
		try {
			model = getDataObjectFromJson(DataReader.getStringFromFile( context.getAssets().open("sampleData.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public InputDataModel getDataObjectFromJson(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		InputDataModel model = new  InputDataModel();
		try {
			model = mapper.readValue(jsonString, InputDataModel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}
}
