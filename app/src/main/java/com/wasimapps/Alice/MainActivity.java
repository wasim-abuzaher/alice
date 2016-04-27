package com.wasimapps.Alice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetShopifyJsonData.OnResultsReadyListener {
    private List<Product> allProducts;
    private int pairsPossible;

    private String LOG_TAG = MainActivity.class.getSimpleName();



    @Override
    public void onResultsReady(List<Product> products) {
        // allProducts contains products for all requests that have completed so far
        allProducts.addAll(products);
        Log.v(LOG_TAG, allProducts.size() + " total products downloaded.");
        for(Product singleProduct : allProducts) {
            Log.v(LOG_TAG, singleProduct.toString());
        }

        itemCount(allProducts);
        calculateWeightAndPrice(this.allProducts, pairsPossible);

    }




    public void itemCount(List<Product> allProducts) {
        int keyboardCount = 0;
        int computerCount = 0;

        for(Product singleProduct : allProducts) {

            String productType = singleProduct.getProductType();
            switch (productType) {
                case "Keyboard" :
                    keyboardCount += 1;
                    break;
                case "Computer" :
                    computerCount += 1;
                    break;
                default:
                    Log.v(LOG_TAG, "Error");
            }
        }

        pairsPossible = Math.min(keyboardCount, computerCount);

        Log.v(LOG_TAG, "There is " + keyboardCount + " Keyboards and " + computerCount +
                " Computers");
        Log.v(LOG_TAG, "Possible pairs = " + pairsPossible);


    }

    private void calculateWeightAndPrice(List<Product> allProducts, int pairsPossible) {
        double keyboardsPrice = 0;
        int keyboardsWeight = 0;
        double computersPrice = 0;
        int computerWeight = 0;
        int computedKeyboards = 0;
        int computedComputers = 0;
        double totalPrice = 0;


        for (Product singleProduct : allProducts) {
            String productType = singleProduct.getProductType();
                switch (productType) {
                    case "Keyboard" :
                        if (computedKeyboards == pairsPossible || totalPrice > 1000 ) {
                            break;
                        } else {
                            keyboardsPrice += singleProduct.getPrice();
                            totalPrice += singleProduct.getPrice();
                            keyboardsWeight += singleProduct.getWeight();
                            computedKeyboards += 1;
                        break;
                        }
                    default:
                }

                switch (productType) {
                    case "Computer" :
                        if (computedComputers == pairsPossible || totalPrice > 1000) {
                            break;
                        } else {
                            computersPrice += singleProduct.getPrice();
                            computerWeight += singleProduct.getWeight();
                            totalPrice += singleProduct.getPrice();
                            computedComputers += 1;
                            break;
                        }
                    default:
                }

        }

        Log.v(LOG_TAG, computedKeyboards + " Keyboards cost: $" + keyboardsPrice + " and " +
                computedComputers + " computers cost: $" + computersPrice);
        Log.v(LOG_TAG, computedKeyboards + " Keyboards weigh: " + keyboardsWeight + " grams and " +
                computedComputers + " computers weigh: " + computerWeight + " grams");
        Log.v(LOG_TAG, "Total cart: $" + totalPrice + ", and total products weight is: " +
                (keyboardsWeight + computerWeight) + " grams.");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allProducts = new ArrayList<>();


        for (int i = 1; i < 6; i++) {
            GetShopifyJsonData jsonData = new GetShopifyJsonData(i, this);
            jsonData.execute();
        }

    }
}