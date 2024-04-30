import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class HRClient {
    public static void main(String args[]){
        try{
            String msg;
            HRInterface hr = (HRInterface) Naming.lookup("rmi://localhost/HRService");
            
            switch (args.length){
                case 1:
                    if(args[0].equals("list")){
                        msg = hr.list();
                        System.out.println(msg);
                    }
                    if(args[0].equals("guests")){
                        msg = hr.guests();
                        if(msg.isEmpty())
                            System.out.println("No current reservations");
                        else
                        System.out.println(msg);
                    }
                    break;
                
                case 4:
                    if(args[0].equals("book")){
                        int out = hr.book(args[1].charAt(0), Integer.parseInt(args[2]), args[3]);
                        if((out > 0) && (out < 60)){
                            System.out.println("There are "+out+" rooms available");
                            System.out.println("Would you like to book them? (y/n)");
                            Scanner sc = new Scanner(System.in);
                            sc.reset();
                            if(sc.next().equals("y") || sc.next().equals("Y"))
                                out = hr.book(args[1].charAt(0), out, args[3]);
                            sc.close();
                        }
                        if(out >= 60)
                            System.out.println("Reservation success. Total amount: "+out+"€");
                        else if(out == -2){
                            System.out.println("No rooms of this category available");
                            System.out.println("Would you like to subscribe to waiting list?(y/n)");
                            Scanner sc = new Scanner(System.in);
                            String opt = sc.next();
                            if(opt.equals("y") || opt.equals("Y")){
                                HRClientInterface ci = new HRClientImpl();
                                hr.registerForCallback(ci, args[1].charAt(0), Integer.parseInt(args[2]), args[3]);
                                System.out.println("Waiting for availability...");
                            }
                        }
                        else
                            System.out.println("Not a valid room choice");
                    }
                    if(args[0].equals("cancel")){
                        msg = hr.cancel(args[1].charAt(0), Integer.parseInt(args[2]), args[3]);
                        System.out.println(msg);
                    }
                    break;
                
                default:
                    System.out.println("Run Error. Available options are:");
                    System.out.println("java HRClient list");
                    System.out.println("java HRClient book <type> <number> <name>");
                    System.out.println("java HRClient guests");
                    System.out.println("java HRClient cancel <type> <number> <name>");
                    System.out.println("Hostname: rmi://localhost/HRService");
                    break;
            }   
        }catch(NumberFormatException | MalformedURLException | NotBoundException | RemoteException e){
            e.printStackTrace();
        }
    }
}