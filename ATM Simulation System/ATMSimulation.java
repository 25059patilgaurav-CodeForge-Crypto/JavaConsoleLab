/*
ACCOUNT NO:-- 1234567890
PIN:-- 1234
BALANCE:-- 5000.0
*/



import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Transaction {
    String type;
    double amount;
    String dateTime;

    Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}

class ATMAccount {
    String accountNumber;
    int pin;
    double balance;
    int pinAttempts = 0;
    boolean isLocked = false;
    ArrayList<Transaction> miniStatement = new ArrayList<>();

    ATMAccount(String accountNumber, int pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }
}

public class ATMSimulation {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        ATMAccount account = new ATMAccount("1234567890", 1234, 5000);

        System.out.print("Enter Account Number: ");
        String accNo = sc.next();

        if (!accNo.equals(account.accountNumber)) {
            System.out.println("Invalid Account Number");
            return;
        }

        while (account.pinAttempts < 3 && !account.isLocked) {
            System.out.print("Enter PIN: ");
            int enteredPin = sc.nextInt();

            if (enteredPin == account.pin) {
                showMenu(account);
                return;
            } else {
                account.pinAttempts++;
                System.out.println("Wrong PIN");
            }
        }

        account.isLocked = true;
        System.out.println("Account Locked due to 3 wrong PIN attempts");
    }

    static void showMenu(ATMAccount account) {
        int choice;

        do {
            System.out.println("\n--- ATM MENU ---");
            System.out.println("1. Balance Check");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Mini Statement");
            System.out.println("5. Change PIN");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: ₹" + account.balance);
                    break;

                case 2:
                    withdraw(account);
                    break;

                case 3:
                    deposit(account);
                    break;

                case 4:
                    showMiniStatement(account);
                    break;

                case 5:
                    changePin(account);
                    break;

                case 6:
                    System.out.println("Thank you for using ATM");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 6);
    }

    static void withdraw(ATMAccount account) {
        System.out.print("Enter amount to withdraw: ");
        double amt = sc.nextDouble();

        if (amt > account.balance) {
            System.out.println("Insufficient Balance");
        } else {
            account.balance -= amt;
            account.miniStatement.add(new Transaction("Withdraw", amt));
            System.out.println("Withdrawal Successful");
        }
    }

    static void deposit(ATMAccount account) {
        System.out.print("Enter amount to deposit: ");
        double amt = sc.nextDouble();

        account.balance += amt;
        account.miniStatement.add(new Transaction("Deposit", amt));
        System.out.println("Deposit Successful");
    }

    static void showMiniStatement(ATMAccount account) {
        if (account.miniStatement.isEmpty()) {
            System.out.println("No transactions yet");
            return;
        }

        System.out.println("\n--- MINI STATEMENT ---");
        for (Transaction t : account.miniStatement) {
            System.out.println(t.type + " | ₹" + t.amount + " | " + t.dateTime);
        }
    }

    static void changePin(ATMAccount account) {
        System.out.print("Enter old PIN: ");
        int oldPin = sc.nextInt();

        if (oldPin == account.pin) {
            System.out.print("Enter new PIN: ");
            account.pin = sc.nextInt();
            System.out.println("PIN Changed Successfully");
        } else {
            System.out.println("Incorrect old PIN");
        }
    }
}
