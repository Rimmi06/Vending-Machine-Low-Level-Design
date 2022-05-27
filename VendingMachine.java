package StateVendingMachine;

import java.util.List;

/*
 - selectItemAndGetPrice
 - Dispense product and its Refund
 - Reset after selected product
 - Cancel product
 - Accept Coins

 Exceptions
 - SoldOut
 - Not Enough Change
 - Not Full PAid

 */
public interface VendingMachine {
    public long selectItemAndGetPrice(Item item);
    public void insertCoin(Coin coin);
    public List<Coin> refund();
    public Bucket<Item, List<Coin>> collectItemAndChange();
    public void Reset();
}
