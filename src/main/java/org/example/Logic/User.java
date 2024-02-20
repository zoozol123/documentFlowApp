package org.example.Logic;

public class User implements CategoryObserver {
    private int id;
    private String password;
    private Category observedCategory = null;
    private boolean displayObservedCategoryAlert = false;

    public User(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public boolean getAlert() {
        return displayObservedCategoryAlert;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setObservedCategory(Category category) {
        observedCategory = category;
    }

    @Override
    public void onElementAdded(IProduct product) {
        displayObservedCategoryAlert = true;
    }

    @Override
    public Category getObservedCategory() {
        return observedCategory;
    }


}
