/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment_4_newspaper;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Alaa Farouk
 */
public class Assignment_4_newspaper {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Random r = new Random();

        final double SalePrice = 0.5;
        final double Costpernewspaper = 0.33;
        final double LostProfit = 0.17;
        final double Salvage = 0.05;

        
        int BestNewspaperCount = 0;
        double MaxAverageProfit = Double.MIN_VALUE;

        // Display the probability table
        System.out.println("Newspaper Probability Table:");
        System.out.println("_________________________________________________");
        System.out.println("Type | Probability | Cumulative Probability | Random-Digit Assignment");
        System.out.println("_________________________________________________");
        displayProbabilityTable();


        System.out.print("Enter the number of iterations: ");
        int iterations = s.nextInt();
        for (int i = 0; i < iterations; i++) {
            System.out.print("Enter the number of newspapers to order: ");
            int noOfNewspaper = s.nextInt(); 

            System.out.print("Enter the number of days: ");
            int n = s.nextInt();

            System.out.println("| Day | Random for Type | Type   | Random for Demand | Demand | Revenue  | Lost Profit | Salvage  | Profit |");
            System.out.println("__________________________________________________________________________________________________________");

            double totalProfit = 0.0;
        //profit per day
            for (int j = 0; j < n; j++) {
                int randomType = r.nextInt(100) + 1;
                char newsType;
                if (randomType <= 35) {
                    newsType = 'G'; // Good
                } else if (randomType <= 80) {
                    newsType = 'F'; // Fair
                } else {
                    newsType = 'P'; // Poor
                }

                int randomDemand = r.nextInt(100) + 1;
                int demand = getDemand(newsType, randomDemand); 

                // Calculations
                double revenue = Math.min(demand, noOfNewspaper) * SalePrice;
                double cost = noOfNewspaper * Costpernewspaper;
                double lostProfit = demand > noOfNewspaper ? (demand - noOfNewspaper) * LostProfit : 0;
                double salvage = demand < noOfNewspaper ? (noOfNewspaper - demand) * Salvage : 0;
                double dailyProfit = revenue + salvage - (cost + lostProfit);
                totalProfit += dailyProfit;

                // Print daily details
                String typeString = newsType == 'G' ? "Good" : newsType == 'F' ? "Fair" : "Poor";
                System.out.printf("| %3d | %14d | %5s | %17d | %6d | %8.2f | %11.2f | %8.2f | %7.2f |%n",
                        j + 1, randomType, typeString, randomDemand, demand, revenue, lostProfit, salvage, dailyProfit);
            }

            double averageProfit = totalProfit / n;
            System.out.printf("Average Daily Profit for %d newspapers: %.2f%n", noOfNewspaper, averageProfit);

            if (averageProfit > MaxAverageProfit) {
                MaxAverageProfit = averageProfit;
                BestNewspaperCount = noOfNewspaper;
            }
        }

      
        

        s.close();
    }

   
    private static int getDemand(char newsType, int randomDigit) { 
        int demand = 0;

        switch (newsType) {
            case 'G': // Good
                if (randomDigit <= 3) demand = 40;
                else if (randomDigit <= 8) demand = 50;
                else if (randomDigit <= 23) demand = 60;
                else if (randomDigit <= 43) demand = 70;
                else if (randomDigit <= 78) demand = 80;
                else if (randomDigit <= 93) demand = 90;
                else demand = 100;
                break;

            case 'F': // Fair
                if (randomDigit <= 10) demand = 40;
                else if (randomDigit <= 28) demand = 50;
                else if (randomDigit <= 68) demand = 60;
                else if (randomDigit <= 88) demand = 70;
                else if (randomDigit <= 96) demand = 80;
                else demand = 100;
                break;

            case 'P': // Poor
                if (randomDigit <= 44) demand = 40;
                else if (randomDigit <= 66) demand = 50;
                else if (randomDigit <= 82) demand = 60;
                else if (randomDigit <= 94) demand = 70;
                else demand = 80;
                break;
        }

        return demand;
    }

    // Method to display the probability table
    private static void displayProbabilityTable() {
        System.out.println("Good |   0.35      | 0.35                  | 1-35");
        System.out.println("Fair |   0.45      | 0.80                  | 36-80");
        System.out.println("Poor |   0.20      | 1.00                  | 81-100");
    }
    }

