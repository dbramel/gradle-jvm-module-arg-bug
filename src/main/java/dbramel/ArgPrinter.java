package dbramel;

public class ArgPrinter
{
    public static void main(String[] args) 
    {
        System.out.println("Arguments to main():" + args.length);
        for (int i = 0; i < args.length; i++)
        {
            System.out.println(i + ": " + args[i]);
        }
    }
}
