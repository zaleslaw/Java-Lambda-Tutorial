package Chapter_1_Lambda_Start.beans;


/**
 * Very bad designed class
 */
public class BusinessTaskUpdateCustomerAge implements BusinessTask {
    @Override
    public void updateStateInDB() {
        System.out.println("We are read StateFromDB" );
    }
    @Override
    public void updateStateInDBByDefault(){
        System.out.println("Yes, I've overridden that");
    }
}
