import java.rmi.Naming;

public class HRServer{
    public HRServer(){
        try{
            HRInterface hr = new HRImpl();
            Naming.rebind("rmi://localhost/HRService", hr);
        }catch(Exception e){
            System.out.println("Exception on Server "+e.getMessage());
        }
    }
    
    public static void main(String args[]){
        new HRServer();
    }
}