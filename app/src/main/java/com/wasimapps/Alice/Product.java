package com.wasimapps.Alice;

/**
 * Created by wasim on 2016-04-24.
 */
public class Product {
    private String mTitle;
    private double mPrice;
    private int mWeight;
    private String mProductType;
    private String mVariantTitle;

    public Product(String title, double price, int weight, String productType, String variantTitle) {
        mTitle = title;
        mPrice = price;
        mWeight = weight;
        mProductType = productType;
        mVariantTitle = variantTitle;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getWeight() {
        return mWeight;
    }

    public String getProductType() {
        return mProductType;
    }


    @Override
    public String toString() {
        return "Product{" +
                "Title=" + mTitle +
                ", Price=" + mPrice +
                ", Weight=" + mWeight +
                ", ProductType=" + mProductType +
                ", VariantTitle= " + mVariantTitle +
                '}';
    }

}
