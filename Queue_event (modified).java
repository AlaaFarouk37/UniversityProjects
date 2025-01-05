/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.queue_event;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Alaa Farouk
 */
public class Queue_event {
 static int cust_num;
    static int measure;
    static int[] IAT_times;
    static int[] service_times;
    static int[] arrival_times;
    static int[] begin_times;
    static int[] depart_times;
    static char[] event_type;
    static int[] clock;
    static int[] Lq;
    static int[] Ls;
    static int[] B;
    static int[] Mq;

    
    static Random rand = new Random();
    
    public static void calc_BT_DT(){
        
        begin_times = new int[cust_num];
        depart_times = new int[cust_num];
       
        begin_times[0]=0;
        depart_times[0] = service_times[0];
        
        for(int i=1 ; i<cust_num ; i++){
                begin_times[i] = Math.max(depart_times[i-1], arrival_times[i]);
                depart_times[i] = begin_times[i] + service_times[i];
        }
    }
    
    public static void fill_clock() {
    int i = 1; // Pointer for arrivals
    int j = 0; // Pointer for departures
    int k = 1; // Pointer for clock/events
    
    Lq = new int[2*cust_num];
    Ls = new int[2*cust_num];
    clock = new int[2*cust_num];
    event_type = new char[2*cust_num];
    
    // Handling the first arrival case
    Lq[0] = 0; // Initial queue size
    Ls[0] = 1; // Initial server state (busy)
    clock[0] = 0; // Initial clock time
    event_type[0] = 'A';
    
    
    while (i < arrival_times.length || j < depart_times.length) {
        if (i < arrival_times.length && (j >= depart_times.length || arrival_times[i] < depart_times[j])) {
            // Arrival event
            clock[k] = arrival_times[i];
            event_type[k] = 'A';
            Ls[k] = 1;
            if (Ls[k - 1] == 1) {
                Lq[k] = Lq[k - 1] + 1; // Increment queue size if server is busy
            } else {
                Lq[k] = Lq[k - 1];
            }
            i++; // Move to next arrival
        } else if (j < depart_times.length && (i >= arrival_times.length || depart_times[j] <= arrival_times[i])) {
            // Departure event
            clock[k] = depart_times[j];
            event_type[k] = 'D';
            if (Lq[k - 1] == 0) {
                Lq[k] = 0;
                Ls[k] = 0; // Server becomes idle
            } else {
                Lq[k] = Lq[k - 1] - 1;
                Ls[k] = 1; // Server remains busy
            }
            j++; // Move to next departure
        }
        k++; // Increment event counter
        }
}
        
    public static void calc_B_Mq(){
        B = new int[2*cust_num];
        Mq = new int[2*cust_num];
        
        //handling first arrival 
        B[0] = 0;
        Mq[0] = 0;
        
        //iterating over Lq and Ls
        for(int i=1 ; i< Lq.length ; i++){
            
            //Handling Mq
            if(Lq[i] > Mq[i-1]){
                Mq[i] = Lq[i];
            }else {
                Mq[i] = Mq[i-1];
            }
            
            //Handling 
            if(Ls[i-1] == 1){
                
                B[i] = B[i-1] + (clock[i] - clock[i-1]);
            }else {
                
                B[i] = B[i-1];
            }
        }
    }
    

    
    
    public static void print_table(){
        
        String line = new String();
        int a = 0;
        
        System.out.println("______________________________________________________________________");
        System.out.println("Clock  Event type         Lq      Ls      B      Mq     FEL        Checkout_Line");
        System.out.println("______________________________________________________________________");
        
        int i=0;
        while (i< clock.length){
            
            System.out.print(clock[i] + "         " + event_type[i] +"              "  + Lq[i] +  "      " + Ls[i]+"      "+ B[i]+ "       " + Mq[i]+"     ");
            
            
            //printing FUL
            if(i < clock.length - 2 )
            System.out.print("(" + event_type[i+1] + "," + clock[i+1] + "),(" +  event_type[i+2] + "," + clock[i+2] +")       ");
            
            if( i == clock.length - 2)
                System.out.print("(" + event_type[i+1] + "," + clock[i+1] +")              " );
            
            //printing checkoutline
            if (event_type[i] == 'A'){
                line = line.concat("C" + (a+1)+",");
                a++;
            }else {
                //regex to delete any c followed by a 1 or 2-digit number and the ","
                line = line.replaceFirst("C\\d+,", "");
            }
            System.out.println(line);
        i++;

        }
    }
    
    
    public static void take_user_input(){
        
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter cust_num (Recommended 20): ");
        cust_num = scan.nextInt();
        
        System.out.print("Calulate number of customers who spent x minutes, x= ");
        measure = scan.nextInt();
        
        IAT_times = new int[cust_num];
        service_times = new int[cust_num];
        arrival_times = new int[cust_num];
        
        arrival_times[0]=0;
        IAT_times[0]=0;
        
        for(int i=0 ; i< cust_num ; i++){
            
            if(i>0){
            System.out.print("Enter IAT for customer " +(i+1) + ":");
            IAT_times[i] = scan.nextInt();
            arrival_times[i] = IAT_times[i] + arrival_times[i-1];
            //System.out.println("");
            }
            
            System.out.print("Enter Service Time for customer " +(i+1) + ":");
            service_times[i] = scan.nextInt();
        }
    }
    
    
    public static void analysis_measure(){
                        
        System.out.println("******************* Analysis measures *******************");
        System.out.println("Server utilization= " + (double) B[B.length-1]/clock[clock.length-1]);
        
        int num = 0;
        for(int i=0 ; i< arrival_times.length ; i++){
            
            if(depart_times[i] - arrival_times[i] >= measure)
                num++;
        }
        System.out.println(num + " customers spent " + measure +" or more in System.");
        System.out.println("***********************************************************");
    }
    
    
    public static void main(String[] args) {

        take_user_input();
        calc_BT_DT();
        fill_clock();
        calc_B_Mq();
        print_table();
        analysis_measure();
    }

}
