package StateVendingMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendingMachineImpl implements VendingMachine{
    private Inventory<Coin> cashInventory = new Inventory<Coin>();
    private Inventory<Item> productInventory = new Inventory<Item>();
    private long totalSales;
    private Item currentItem;
    private long currentBalance;

    public VendingMachineImpl(){
        initialize();
    }

    private void initialize(){
        for(Coin c: Coin.values()){
            cashInventory.put(c, 5);
        }

        for(Item p: Item.values()){
            productInventory.put(p, 5);
        }
    }

    @Override
    public long selectItemAndGetPrice(Item item) {
        if(productInventory.hasItem(item)){
            currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold Out, Please buy another Product");
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getDenomination();
        cashInventory.addItem(coin);
    }

    public Bucket<Item, List<Coin>> collectItemAndChange(){
        Item item = collectItem();
        totalSales = totalSales + currentItem.getPrice();

        List<Coin> change = collectChange();
        return new Bucket<Item, List<Coin>>(item, change);
    }

    private List<Coin> collectChange() {
        long changeAmount = currentBalance - currentItem.getPrice();
        List<Coin> change = getChange(changeAmount);
        updateCashInventory(change);
        currentBalance = 0;
        currentItem = null;
        return change;
    }

    public Item collectItem() throws NotSufficientChange, NotFullPaidException{
        if(isFullPaid()){
            if(hasSufficientChange()){
                productInventory.deduct(currentItem);
                return currentItem;
            }
            throw new NotSufficientChange("Not Enough change");
        }
        long remainingBalance = currentItem.getPrice() - currentBalance;
        throw new NotFullPaidException("Price is not full Paid, remainingBalance: "+ remainingBalance);
    }

    @Override
    public List<Coin> refund() {
        List<Coin> refund = getChange(currentBalance);
        updateCashInventory(refund);
        currentBalance = 0;
        currentItem = null;
        return refund;
    }

    private boolean isFullPaid(){
        if(currentBalance >= currentItem.getPrice()){
            return true;
        }
        return false;
    }

    public List<Coin> getChange(long amount) throws NotSufficientChange{
        List<Coin> changes = Collections.emptyList();

        if(amount > 0){
            changes = new ArrayList<Coin>();
            long balance = amount;
            while (balance > 0 ){
                if( balance >= Coin.QUARTER.getDenomination() && cashInventory.hasItem(Coin.QUARTER)){
                    changes.add(Coin.QUARTER);
                    balance = balance - Coin.QUARTER.getDenomination();
                    continue;
                }
                else if( balance >= Coin.DIME.getDenomination() && cashInventory.hasItem(Coin.DIME)){
                    changes.add(Coin.DIME);
                    balance = balance - Coin.DIME.getDenomination();
                    continue;
                }
                else if( balance >= Coin.NICKLE.getDenomination() && cashInventory.hasItem(Coin.NICKLE)){
                    changes.add(Coin.NICKLE);
                    balance = balance - Coin.QUARTER.getDenomination();
                    continue;
                }
                else if( balance >= Coin.PENNY.getDenomination() && cashInventory.hasItem(Coin.PENNY)){
                    changes.add(Coin.PENNY);
                    balance = balance - Coin.QUARTER.getDenomination();
                    continue;
                }else{
                    throw new NotSufficientChange("Not Sufficient Chnage, Please try another product");
                }
            }
        }
        return changes;
    }
    @Override
    public void Reset() {
        cashInventory.clear();
        productInventory.clear();
        currentBalance = 0;
        currentItem = null;
        totalSales = 0;
    }

    public void printStats(){
        System.out.println("Total Sales: "+ totalSales);
        System.out.println("Current Item Inventory: "+ cashInventory);
        System.out.println("Product Inventory: "+ productInventory);
    }

    private boolean hasSufficientChange(){
        return hasSufficientChangeForAmount(currentBalance - currentItem.getPrice());
    }

    private boolean hasSufficientChangeForAmount(long amount){
        boolean hasChange = true;
        try{
            getChange(amount);
        }catch (NotSufficientChange nsc){
            return hasChange = false;
        }
        return hasChange;
    }

    private void updateCashInventory(List<Coin> change){
        for(Coin c: change){
            cashInventory.deduct(c);
        }
    }

    public long getTotalSales(){
        return totalSales;
    }

}
