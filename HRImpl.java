import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HRImpl extends java.rmi.server.UnicastRemoteObject implements HRInterface{
    private int[] rooms;
    private int[] price;
    ArrayList<Customer> clients;
    
    public HRImpl() throws java.rmi.RemoteException{
        super();
        readFile();
        price = new int[5];
        price[0] = 60;
        price[1] = 80;
        price[2] = 90;
        price[3] = 115;
        price[4] = 140;
        System.out.println("Server Running...");
        clients = new ArrayList<>();
    }
    
    private void readFile(){
        rooms = new int[5];
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File("RoomData/rooms.txt")));
            for(int i=0;i<5;i++)
                rooms[i] = Integer.parseInt(br.readLine());
            br.close();
        }catch(FileNotFoundException e){
            System.out.println("Exception in HRImpl: "+e.getMessage());
        }catch(IOException e){
            System.out.println("Exception in HRImpl: "+e.getMessage());
        }
    }
    
    private void updateRoomFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("RoomData/rooms.txt"));
            for(int i=0;i<5;i++)
                bw.write(rooms[i]+"\n");
            bw.close();
        }catch(IOException e){
            System.out.println("Exception in HRImpl updateRoomFile: "+e.getMessage());
        }
    }
    
    private void saveReservation(char type, int number, String name, int total_price)   {
        try{
            File res = new File("RoomData/reservations.txt");
            File tmp = new File("RoomData/reservations_tmp.txt");
            Scanner sc = new Scanner(res);
            PrintWriter pw = new PrintWriter (new BufferedWriter(new FileWriter(tmp)));
            String t_type = null;
            String n_name;
            boolean done = false;
            int resvs, cost;
            
            while(sc.hasNext()){
                n_name = sc.next();
                t_type = sc.next();
                resvs = sc.nextInt();
                cost = sc.nextInt();
                
                if(n_name.equals(name) && t_type.equals(Character.toString(type))){
                        done = true;
                        pw.println(name+" "+type+" "+ (resvs+number) +" "+(cost+total_price));   
                }else
                    pw.println(n_name+" "+t_type+" "+resvs+ " "+cost+" ");
            }
            if(!done)
                pw.println(name+" "+type+" "+number+ " "+total_price);
            res.delete();
            tmp.renameTo(res);
            pw.close();
        }catch(IOException e){
            System.out.println("Exception in HRImpl saveReservation: "+e.getMessage());
        }        
    }
      
    public String list() throws java.rmi.RemoteException{
        String msg = (new StringBuilder())
                    .append(rooms[0]).append(" Rooms of Type A - Price: 60€ per night\n")
                    .append(rooms[1]).append(" Rooms of Type B - Price: 80€ per night\n")
                    .append(rooms[2]).append(" Rooms of Type C - Price: 90€ per night\n")
                    .append(rooms[3]).append(" Rooms of Type D - Price: 115€ per night\n")
                    .append(rooms[4]).append(" Rooms of Type E - Price: 140€ per night\n")
                    .toString();
        return msg;
    }
    
    public int book(char type, int number, String name) throws java.rmi.RemoteException{
        int available = rooms[type-65];
        int total_price;
        
        if((type < 65) || (type > 69))
            return -1;
        
        if(available == 0 )
            return -2;
        else if((available < number) && (available > 0))
            return available; // Εδώ ο Client θα εκτυπώσει τον αριθμό και θα ξαναστείλει book
        else{
            rooms[type-65] -= number;
            updateRoomFile();
            total_price = number * price[type-65];
            saveReservation(type,number,name,total_price);
            return total_price;
        }
    }
    
    public String guests() throws java.rmi.RemoteException{
        String msg = new String();
        try{
            Scanner sc = new Scanner(new File("RoomData/reservations.txt"));
            while(sc.hasNext()){
                msg += "Client:" + sc.next();
                msg += " Room_Type:" + sc.next();
                msg += " Reservations:" + sc.nextInt();
                msg += " Total_Amount:" + sc.nextInt() + "€\n";
            }
            sc.close();
        }catch(FileNotFoundException e){
                System.out.println("Exception in guests() "+e.getMessage());
        }
        return msg;
    }
    
    public String cancel(char type, int number, String name) throws java.rmi.RemoteException{
        String msg = null;
        String rest = null;
        boolean has_res_flag = false;
        
        try{
            String n_name;
            String t_type;
            int cost;
            
            int resvs;
            Integer diff = null;
            // Τα ορίζουμε έτσι ώστε να διαγράψουμε το ένα και να μετονομάσουμε το άλλο στην συνέχεια
            File res = new File("RoomData/reservations.txt");
            File tmp = new File("RoomData/reservations_tmp.txt");
            Scanner sc = new Scanner(res);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tmp)));

            // Αντιγραφή απο reservations σε tmp_reservations και επεξεργασία πληροφοριών 
            while(sc.hasNext()){
                n_name = sc.next();
                t_type = sc.next();
                resvs = sc.nextInt();
                cost = sc.nextInt();
                
                // Αν βρείς ίδιο όνομα και type αντέγραψε το με μειωμένο αριθμό κρατήσεων
                if(n_name.equals(name) && t_type.equals(Character.toString(type))){
                    has_res_flag = true;
                    diff = resvs - number;
                    if(diff < 0)
                        return "Reservations are less than number specified";
                    else if(diff > 0){
                        pw.println(n_name+" "+t_type+" "+diff+" "+(diff*price[type-65]));
                        rest = "Client:"+n_name+" Room_Type:"+t_type+" Reservations:"+diff+" Total_Amount "+(diff*price[type-65])+ "€\n";
                        rooms[type-65] += number;
                    }        
                }else{
                    pw.println(n_name+" "+t_type+" "+resvs+" "+cost);
                    if(n_name.equals(name))
                        rest += "Client:"+n_name+" Room_Type:"+t_type+" Reservations:"+resvs+" Total_Amount "+cost+"€\n";                    
                }
            }
            
                
            res.delete();
            tmp.renameTo(res);
            pw.close();
            sc.close();
            updateRoomFile();
            makeAvailable(type);
        }catch(IOException e){
            e.getMessage();
        }
        if(!has_res_flag)
            msg = "Client has no reservations";
        else
            msg = "Client's remaining reservations:\n"+rest;
        return msg;
    }
    
    public void registerForCallback(HRClientInterface obj, char type, int number, String name) throws java.rmi.RemoteException{
        boolean flag = true;
        for(int i=0;i<clients.size();i++)
            if (clients.get(i).getClientInterface() == obj)
                flag = false;
        if(flag)
            clients.add(new Customer(obj,type,number,name));
    }
    
    private void unregister(HRClientInterface obj){
        for(int i=0;i<clients.size();i++)
            if (clients.get(i).getClientInterface() == obj)
                clients.remove(i);
    }
    
    void makeAvailable(char type) throws java.rmi.RemoteException{
        String opt;
        String tmpName;
        char tmpType;
        int tmpNumber;
        
        for(int i=0;i<clients.size();i++){
            tmpName = clients.get(i).getName();
            tmpType = clients.get(i).getType();
            tmpNumber = clients.get(i).getNumber();
            if((tmpType == type) && (tmpNumber <= rooms[type-65])){
                opt = clients.get(i).getClientInterface().notifyMe(tmpNumber+" rooms of type "+tmpType+" available\nBook them? (y/n)");
                if(opt.equals("Y") || opt.equals("y")){
                    book(type,tmpNumber,tmpName);
                    unregister(clients.get(i).getClientInterface());
                    break;
                }
            }
        }
    }
}

class Customer{
    private HRClientInterface obj;
    private char type;
    private int number;
    private String name;
    public Customer(HRClientInterface obj,char type,int number,String name){
        this.type = type;
        this.number = number;
        this.obj = obj;
        this.name = name;
    }
    public char getType(){
        return type;
    }
    public int getNumber(){
        return number;
    }
    public String getName(){
        return name;
    }
    public HRClientInterface getClientInterface(){
        return obj;
    }
}