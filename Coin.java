package StateVendingMachine;

public enum Coin {
    PENNY(1), DIME(10), NICKLE(5), QUARTER(25);

    private int denomination;

    private Coin(int denomination){
     this.denomination = denomination;
    }

    public int getDenomination(){
        return denomination;
    }
}
