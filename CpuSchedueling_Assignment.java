
package com.mycompany.cpuschedueling_assignment;


import java.util.Scanner;

public class CpuSchedueling_Assignment {

    public static void main(String[] args) {
        SJF();
        SRT();
    }
    
    
    static class Process {
    int arrival_time;
    int burst_time;
    int end_time;
    int start_time;
    int RT;
    String name;
    int TAT;
    int WT;
    
    Process(String name){
        
        this.name=name;
    }
    
    Process(){};

}

    static class Node {
    Process process;
    Node next;

    Node(Process process) {
        this.process = process;
        this.next = null;
    }
}

    static class ProcessLinkedList {
    private Node head;

    public void addProcess(Process process) {
        Node newNode = new Node(process);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void displayProcesses() {
        Node current = head;
        int i = 0;
        while (current != null) {
            System.out.println(current.process.name+"              "+current.process.arrival_time +"                   "+current.process.burst_time + "                   "+ current.process.end_time +"           " + current.process.TAT +"           "+  current.process.WT);
            current = current.next;
            i++;
        }
    }
    
    public void deleteProcess(String name) {
        if (head == null) {
            System.out.println("List is empty. No process to delete.");
            return;
        }

        if (head.process.name.equals(name)) {
            head = head.next;
            return;
        }
        
        Node current = head;
        while (current.next != null && !current.next.process.name.equals(name)) {
            current = current.next;
        }

        if (current.next == null){ 
            System.out.println("Process " + name + " not found.");
           return;
        }
        
        current.next = current.next.next;
 
    }
}
    
    static void SJF(){
        ProcessLinkedList p_list = new ProcessLinkedList();

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int num = scan.nextInt();
        int total=0;
        Process[] executed = new Process[num];
        
        for(int i=0 ; i<num ; i++){
            Process p = new Process("P" + i);
            System.out.print("Enter p" +i+ " Arrival time: ");
            p.arrival_time = scan.nextInt();
            System.out.print("Enter p" +i+ " Burst time: ");
            p.burst_time = scan.nextInt();
            total += p.burst_time;
            p_list.addProcess(p);
            executed[i] = new Process();
        }

        //shortest job first
        //first process arrived executed
        int i=0;
        int clock = p_list.head.process.burst_time ;
        executed[i] = p_list.head.process;
        executed[i].TAT = executed[i].burst_time;
        executed[i].WT = 0;
        executed[i].end_time = executed[i].burst_time;
        i++;
        p_list.deleteProcess(p_list.head.process.name);
        

        //iterating over the list untill it's empty
        while(p_list.head != null &&  clock<total){
            
            Node ptr = p_list.head;
            Node current_shortest = ptr;
            int shortest = current_shortest.process.burst_time;
            
            while(ptr != null){
                if(ptr.process.burst_time < shortest && ptr.process.arrival_time <= clock){
                    current_shortest = ptr;
                    shortest = current_shortest.process.burst_time;
                }
                ptr=ptr.next;
            }
            executed[i] = current_shortest.process;
            
            //last process only case  
            if(executed[i].arrival_time > clock){
                clock = executed[i].arrival_time;
            }
            
            clock+= current_shortest.process.burst_time;
            executed[i].end_time = clock;
            executed[i].TAT = executed[i].end_time - executed[i].arrival_time;
            executed[i].WT = executed[i].TAT - executed[i].burst_time;
            p_list.deleteProcess(current_shortest.process.name);
            i++;
        }
        
        System.out.print("Order of execution: ");
        for(int j=0 ; j<num ; j++){
            System.out.print(executed[j].name +"  ");
        }
        System.out.println("");
        
        //displaying processes info
        System.out.println("____________________________________________________________________________________");
        System.out.println("Process_number     arrival_time      burst_time      end_time     TAT     WT");
        System.out.println("____________________________________________________________________________________");
        for(int x=0 ; x< num ; x++){
            
            Process c = executed[x];
            System.out.println(c.name +"                    "+ c.arrival_time + "                    "+c.burst_time+"              "+c.end_time+"       "+ c.TAT+"      "+ c.WT);
            
        }
        System.out.println("____________________________________________________________________________________");
        
    }
    
    static void SRT(){
    ProcessLinkedList p_list = new ProcessLinkedList();
    
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int num = scan.nextInt();
        
        for(int i=0 ; i<num ; i++){
            Process p = new Process("P" + i);
            System.out.print("Enter p" +i+ " Arrival time: ");
            p.arrival_time = scan.nextInt();
            System.out.print("Enter p" +i+ " Burst time: ");
            p.burst_time = scan.nextInt();
            p_list.addProcess(p);
            p.RT = p.burst_time;
        }
    
        int clock = 0;
        int completed = 0;
        String running_order = "";
        
        while (completed < num) {
        Process current = null;
        int minRT = Integer.MAX_VALUE;

        Node itr = p_list.head;
        while (itr != null) {
            if (itr.process.arrival_time <= clock && itr.process.RT < minRT && itr.process.RT > 0) {
                current = itr.process;
                minRT = itr.process.RT;
            }
            itr = itr.next;
        }

        if (current != null) {
            running_order = running_order.concat("," + current.name);
            current.RT--;
            clock++;

            if (current.RT == 0) {
                current.end_time = clock;
                current.TAT = current.end_time - current.arrival_time;
                current.WT = current.TAT - current.burst_time;
                completed++;
            }
        } else {
            clock++;
        }
    }
        
        //to format the running_order string
        String[] parts = running_order.split(","); // Split the string by commas
        running_order = parts[0];         // Start with the first element

        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].equals(parts[i - 1])) {
                running_order += "," + parts[i]; // Append only if the current element is not equal to the previous
            }
        }
        System.out.println(running_order);
        
        //displaying table
        
        System.out.println("___________________________________________________________________________________");
        System.out.println("Process_num  Arrival_time       Burst_Time         End_Time       TAT       WT  ");
        System.out.println("___________________________________________________________________________________");
        p_list.displayProcesses();
        System.out.println("___________________________________________________________________________________");
        
    }
   
}
    
    