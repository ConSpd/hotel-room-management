public interface HRInterface extends java.rmi.Remote{
    public String list()
            throws java.rmi.RemoteException;
    
    public int book(char type, int number, String name)
            throws java.rmi.RemoteException;
    
    public String guests()
            throws java.rmi.RemoteException;
    
    public String cancel(char type, int number, String name)
            throws java.rmi.RemoteException;
    
    public void registerForCallback(HRClientInterface obj, char type, int number, String name)
            throws java.rmi.RemoteException;
}