// LoadMessageInterface.aidl
package com.arialyy.aidldemo;

// Declare any non-default types here with import statements
import com.arialyy.aidldemo.Product;

interface LoadMessageInterface {

    void loadMessage(String msg);

    void loadEntity(inout Product p);
}
