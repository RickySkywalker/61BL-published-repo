/**
 * This class represents a bank account whose current balance is a nonnegative
 * amount in US dollars.
 */
public class Account {

    private int balance;

    /** Initialize an account with the given balance. */
    public Account(int balance) {
        this.balance = balance;
        this.parentAccount = null;
    }

    /** Returns the balance for the current account. */
    public int getBalance() {
        return balance;
    }

    /** Deposits amount into the current account. */
    public void deposit(int amount) {
        if (amount < 0) {
            System.out.println("Cannot deposit negative amount.");
        } else {
            balance += amount;
        }
    }

    /**
     * Subtract amount from the account if possible. If subtracting amount
     * would leave a negative balance, print an error message and leave the
     * balance unchanged.
     */
    public boolean withdraw(int amount) {
        // TODO

        //withdraw protection
        boolean a = true;
        if (parentAccount != null){
            if (amount<0){
                System.out.println("Cannot withdraw negative amount.");
                a = false;
            }
            else if (balance>amount){
                balance -= amount;
            }
            else if(balance < amount && balance + parentAccount.balance >= amount){
                parentAccount.balance = parentAccount.balance - amount + balance;
                this.balance = 0;
            } else{
                System.out.println("Insufficient funds");
                a = false;
            }
        }


        if(parentAccount == null) {
            if (amount < 0) {
                System.out.println("Cannot withdraw negative amount.");
                a = false;
            } else if (balance < amount) {
                System.out.println("Insufficient funds");
                a = false;
            } else {
                balance -= amount;

            }
        }
        return a;
    }

    /**
     * Merge account other into this account by removing all money from other
     * and depositing it into this account.
     */
    public void merge(Account other) {
        // TODO
        this.balance = this.balance+ other.balance;
        other.balance = 0;
    }


    //Creat instant variable of parentAccount
    public Account parentAccount;


    //constructor of the Account with withdraw protection
    public Account(int balance, Account parentAccount){
        this.balance = balance;
        this.parentAccount = parentAccount;
    }


/* test main part
    public static void main(String[] args){
    Account zoe = new Account(500);
    Account allyson = new Account(100, zoe);
    allyson.withdraw(200);
    }
*/

}
