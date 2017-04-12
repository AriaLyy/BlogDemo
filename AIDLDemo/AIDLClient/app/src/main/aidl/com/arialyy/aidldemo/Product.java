package com.arialyy.aidldemo;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 需在在gradle中添加
 * sourceSets {
 * main {
 * java.srcDirs = ['src/main/java', 'src/main/aidl']
 * }
 * }
 */
public class Product implements Parcelable {
  public String name;
  public int price;

  @Override public String toString() {
    return "Product{" + "name='" + name + '\'' + ", price=" + price + '}';
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeInt(this.price);
  }

  //手动实现这个方法
  public void readFromParcel(Parcel dest) {
    //注意，这里的读取顺序要writeToParcel()方法中的写入顺序一样
    name = dest.readString();
    price = dest.readInt();
  }

  public Product() {
  }

  protected Product(Parcel in) {
    this.name = in.readString();
    this.price = in.readInt();
  }

  public static final Creator<Product> CREATOR = new Creator<Product>() {
    @Override public Product createFromParcel(Parcel source) {
      return new Product(source);
    }

    @Override public Product[] newArray(int size) {
      return new Product[size];
    }
  };
}