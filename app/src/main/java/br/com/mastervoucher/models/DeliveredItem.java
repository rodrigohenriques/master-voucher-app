package br.com.mastervoucher.models;

public class DeliveredItem extends ShopCartItem {
    public String status;

    public DeliveredItem(Product product, int quantity) {
        super(product, quantity);
    }

    public DeliveredItem(Product product, int quantity, String status) {
        super(product, quantity);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
