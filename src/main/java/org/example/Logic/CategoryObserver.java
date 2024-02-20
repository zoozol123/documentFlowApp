package org.example.Logic;

public interface CategoryObserver {
    void onElementAdded(IProduct product);
    Category getObservedCategory();

}
