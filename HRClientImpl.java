import java.rmi.server.*;
import java.rmi.*;
import java.util.Scanner;

public class HRClientImpl extends UnicastRemoteObject implements HRClientInterface{
    public HRClientImpl() throws RemoteException{
        super();
    }
    
    public String notifyMe(String msg) throws RemoteException{
        System.out.println(msg);
        Scanner sc = new Scanner(System.in);
        String ans = sc.next();
        return ans;
    }
}
