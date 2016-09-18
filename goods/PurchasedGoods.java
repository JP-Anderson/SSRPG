package goods;

public class PurchasedGoods extends Goods {

    public final int purchasedValue;

    private int quantity;

    public PurchasedGoods(GoodsForSale goods, int quantityPurchased) {
        super(goods.id,goods.name,goods.legal,goods.baseValue);
        purchasedValue = goods.getPurchaseValue();
        quantity = quantityPurchased;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int q) { quantity = q; }

}
