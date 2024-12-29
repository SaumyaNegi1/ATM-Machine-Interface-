import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;//To store user inputs
class Transaction {
    String type;
    double balanceAfterTransaction;
    double amount;

        public Transaction(String type, double amount, double balanceAfterTransaction ) {
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
    public String toString() 
    {     return type + ": $" + amount + ", New Balance: $" + balanceAfterTransaction;
}
}

class ATM
 
 {
    double balance;
    int pin=1234;
    List<Transaction> transactionHistory = new ArrayList<>();
    public void checkpin()
    {
        while(true){
       System.out.println("enter your pin");
        Scanner n = new Scanner(System.in);
        int entered_pin = n.nextInt();
        if(entered_pin==pin){
        menu();
        }
        else{
            System.out.println(" Please enter a valid pin  ");
        }
    }
    }
    public void menu()
    {
        System.out.println("!    choose any option  !");
        System.out.println("1.Check balance;");
        System.out.println("2.Withdraw your money");
        System.out.println("3.Deposite your money");
        System.out.println("4.Transfer your Amount");
        System.out.println("5.View Transection Histry");
        System.out.println("6.Exit");
       
            

        
        Scanner sn = new Scanner(System.in);
        int opt =sn.nextInt();
        if(opt==1){
            checkBalance();
        }
        else if(opt==2){
            withdraw();
        }
        else if(opt==3){
            deposit();
        }
        else if(opt==4){
            transfer();
        }
        else if(opt==5){
            transactionHistory();
        }
        else if(opt==6){
            System.out.println(" Thanks for using our ATM,Goodbyy!  ");
            System.exit(0);
        }
        else{
            System.out.println("Please enter a valid choice");
        }
       
    }
    public void checkBalance() 
      {
        System.out.println("balance:"+ balance);
        menu();
    }
    public void  withdraw()
    {
        System.out.println("Please enter a withdrwal amount");
        Scanner sn=new Scanner(System.in);
        int w_amount=sn.nextInt();
        if (w_amount>balance)
        {
            System.out.println("you have insuficient balance");
        }
        else{
            System.out.println("amount withdrwal successfully");
            balance-=w_amount;
            transactionHistory.add(new Transaction("Withdraw", w_amount, balance)); // Add transaction
        }menu();

    }
    public void deposit()
     {
        System.out.println("Please enter a deposit amount:");
        Scanner sn = new Scanner(System.in);
        double dAmount = sn.nextDouble();
        balance += dAmount;
        System.out.println("Amount deposited successfully.");
        transactionHistory.add(new Transaction("Deposit", dAmount, balance));
        menu();
     }
    public void transfer()
      {
        while(true){
       System.out.println("Enter the 12 digit account no. in which amount will transfer");
       Scanner sn =new Scanner(System.in);
        String acc=sn.nextLine();
        if (acc.length()!=12){
        System.out.println("enter valid account no.");}
        else{
            System.out.println("enter amount to be transfered ");    
        Scanner k=new Scanner(System.in);
        int T_amount=k.nextInt();
        if(T_amount>balance)
        {System.out.println("insufficient balance");}
        else{
            System.out.println("Transfer successfully");
            balance-= T_amount;
            System.out.println("balance left:"+balance);
            transactionHistory.add(new Transaction("Transfer to " + T_amount, balance, balance)); // Add transaction
        }
         menu();}
    }}
    public void transactionHistory()
     {
        System.out.println("        Transaction History       ");
        if (transactionHistory.isEmpty())
         {
            System.out.println("No transactions yet.");
        } 
        else
         {
            for (Transaction Transaction : transactionHistory) 
            {
               System.out.println(Transaction);
            
        }
        menu();
    }}
    public static void main(String[] args) 
    {
        ATM obj = new ATM();
        obj.checkpin();
    }
 }