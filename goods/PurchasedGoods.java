package goods;

import map.GridPoint;

public class PurchasedGoods extends Goods {

    public final int purchasedValue;
    public final GridPoint purchasedFrom;
    private int quantity;

    public PurchasedGoods(GoodsForSale goods, int quantityPurchased, GridPoint purchaseOrigin) {
        super(goods.id,goods.name,goods.legal,goods.baseValue);
        purchasedValue = goods.getPurchaseValue();
        purchasedFrom = purchaseOrigin;
        quantity = quantityPurchased;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int q) { quantity = q; }

}
