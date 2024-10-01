package com.cs341;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CalcInterest {

    public static double computeLoanInterest(double loanAmount, int yearLoan, int loanType) {
        if (loanAmount <= 0 || yearLoan <= 0 || yearLoan > 30 || (loanType != 1 && loanType != 2)) {
            return -1;  // Error check for invalid inputs
        }

        double interestRate = determineInterestRate(loanAmount, yearLoan, loanType);
        if (interestRate == -1) {
            return -1;  // Return -1 if no valid rate found
        }

        // Calculate simple interest
        double interest = loanAmount * interestRate * yearLoan;

        // Round to two decimal places
        BigDecimal bd = new BigDecimal(interest);
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private static double determineInterestRate(double loanAmount, int yearLoan, int loanType) {
        if (loanType == 1) {  // Home loan
            if (loanAmount < 100000) {
                if (yearLoan <= 5) {
                    return 0.08; // 8% for amounts less than 100k for up to 5 years
                }
                return 0.065;  // 6.5% for amounts less than 100k for more than 5 years
            } else if (loanAmount < 500000) {
                return 0.065;  // 6.5% for amounts 100k to <500k
            } else {
                return 0.055;  // 5.5% for amounts 500k or more
            }
        } else if (loanType == 2) {  // Property loan
            if (loanAmount < 100000) {
                if (yearLoan <= 5) {
                    return 0.12; // 12% for amounts less than 100k for up to 5 years
                }
                return 0.085;  // 8.5% for amounts less than 100k for more than 5 years
            } else if (loanAmount < 500000) {
                return 0.085;  // 8.5% for amounts 100k to <500k
            } else {
                return 0.07;   // 7% for amounts 500k or more
            }
        }
        return -1;  // Invalid loan type
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueLoop = true;
    
            while (continueLoop) {
                try {
                    // Input section
                    System.out.println("Enter loan type (1 for Home, 2 for Property): ");
                    int loanType = scanner.nextInt();
    
                    System.out.println("Enter loan amount: ");
                    double loanAmount = scanner.nextDouble();
    
                    System.out.println("Enter loan year: ");
                    int yearLoan = scanner.nextInt();
    
                    // Process the interest rate and loan
                    double interest = computeLoanInterest(loanAmount, yearLoan, loanType);
                    double interestRate = determineInterestRate(loanAmount, yearLoan, loanType);
    
                    if (interest == -1) {
                        System.out.println("Invalid input. Please check the loan type, amount, or year.");
                    } else {
                        System.out.printf("Interest rate used: %.2f%%\n", interestRate * 100);
                        System.out.printf("Total interest: $%.2f\n", interest);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input type. Please enter numbers only.");
                    scanner.next(); // Clear the invalid input
                }
    
                // Check if the user wants to continue
                System.out.println("Do you want to calculate again? (yes/no): ");
                String userResponse = scanner.next();
    
                if (!userResponse.equalsIgnoreCase("yes")) {
                    continueLoop = false;
                }
            }
        }
        System.out.println("Thank you for using the interest calculator!");
    }    
}