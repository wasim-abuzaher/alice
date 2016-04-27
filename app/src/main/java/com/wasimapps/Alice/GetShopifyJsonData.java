package com.wasimapps.Alice;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wasim on 2016-04-24.
 */
public class GetShopifyJsonData extends GetRawData {

    private String LOG_TAG = GetShopifyJsonData.class.getSimpleName();
    private List<Product> mProduct;
    private Uri mDestination;

    public interface OnResultsReadyListener {
        void onResultsReady(List<Product> products);
    }

    private OnResultsReadyListener mResultsListener;

    public GetShopifyJsonData(int page, OnResultsReadyListener resultsListener) {
        super(null);
        createUri(page);
        mProduct = new ArrayList<Product>();
        mResultsListener = resultsListener;
    }


    public void execute(){
        super.setRawUrl(mDestination.toString());
        DownloadShopifyData downloadShopifyData = new DownloadShopifyData();
        Log.v(LOG_TAG, "Built URI = " + mDestination.toString());
        downloadShopifyData.execute(mDestination.toString());
    }


    public boolean createUri(int page) {
        final String SHOPIFY_BASE_URL = "https://shopicruit.myshopify.com/products.json";
        final String SHOPIFY_PAGE_PARAM = "page";

        mDestination = Uri.parse(SHOPIFY_BASE_URL).buildUpon()
                .appendQueryParameter(SHOPIFY_PAGE_PARAM, String.valueOf(page)).build();

        return mDestination != null;
    }

    public void processResults() {

        if(getDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG, "Error Downloading Raw Data");
            return;
        }

        final String SH_PRODUCTS = "products";
        final String SH_TYPE = "product_type";
        final String SH_VARIANTS = "variants";
        final String SH_TITLE = "title";
        final String SH_PRICE = "price";
        final String SH_GRAMS = "grams";

        try {

            JSONObject jsonData = new JSONObject(getData());
            JSONArray productsArray = jsonData.getJSONArray(SH_PRODUCTS);
            for (int i=0; i<productsArray.length(); i++ ) {
                JSONObject jsonProduct = productsArray.getJSONObject(i);
                String productType =jsonProduct.getString(SH_TYPE);
                String title = jsonProduct.getString(SH_TITLE);

                JSONArray variantsArray = jsonProduct.getJSONArray(SH_VARIANTS);
                JSONObject variantProduct = variantsArray.getJSONObject(0);
                String variantTitle = variantProduct.getString(SH_TITLE);
                double price = variantProduct.getDouble(SH_PRICE);
                int grams = variantProduct.getInt(SH_GRAMS);

                if (productType.equals("Keyboard") || productType.equals("Computer")) {

                    Product productObject = new Product(title, price, grams, productType, variantTitle);
                    this.mProduct.add(productObject);

                }

            }



            for(Product singleProduct : mProduct){
                Log.v(LOG_TAG, singleProduct.toString());
            }


        } catch (JSONException jsone) {

            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error Processing JSON data");

        }

        if(mResultsListener != null) {
            mResultsListener.onResultsReady(mProduct);
        }


    }



    public class DownloadShopifyData extends DownloadRawData {
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResults();
        }

        protected String doInBackground(String... prams) {
            return super.doInBackground(prams);
        }



    }

}
