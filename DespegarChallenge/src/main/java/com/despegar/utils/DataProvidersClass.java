package com.despegar.utils;

import com.despegar.model.TestDataJson;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.testng.annotations.DataProvider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class DataProvidersClass {

    @DataProvider(name = "dataFlightsFromJson")
    public static Object[][] getDataFromJson() throws FileNotFoundException {
        String path = System.getProperty("user.dir") + "/src/test/resources/dataproviders/dataFligths.json";
        JsonElement jsonData = new JsonParser().parse(new FileReader(path));
        JsonElement dataSet = jsonData.getAsJsonObject().get("dataFligths");
        List<TestDataJson> testData = new Gson().fromJson(dataSet, new TypeToken<List<TestDataJson>>() {}.getType());
        Object[][] returnValue = new Object[testData.size()][4];
        for(int i = 0; i<testData.size(); i++){
            returnValue[i][0] = testData.get(i).getOriginCity();
            returnValue[i][1] = testData.get(i).getDestinationCity();
            returnValue[i][2] = testData.get(i).getStartDate();
            returnValue[i][3] = testData.get(i).getEndDate();
        }
        return returnValue;
    }
}