package StateVendingMachine;

public class VendingMachineFactory {
    public static VendingMachine createVendingMachine(){
        return new VendingMachineImpl();
    }
}
