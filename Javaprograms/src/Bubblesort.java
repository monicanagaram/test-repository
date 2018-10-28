
public class Bubblesort
{

    public static void main(String[] args)
    {
           
         
            int intArray[] = new int[]{9,55,25,13,159,35};
           
          
            System.out.println("Array Before Bubble Sort");
            for(int i=0; i < intArray.length; i++)
            {
                System.out.print(intArray[i] + " ");
            }
           
       
            bubblesort(intArray);
           
            System.out.println("");
           
          
            System.out.println("Array After Bubble Sort");
            for(int i=0; i < intArray.length; i++)
            {
                    System.out.print(intArray[i] + " ");
            }

    }

    private static void bubblesort(int[] intArray)
    {
           
            int n = intArray.length;
            int temp = 0;
           
            for(int i=0; i < n; i++)
            {
                    for(int j=1; j < (n-i); j++)
                    {
                           
                            if(intArray[j-1] > intArray[j])
                            {
                                 
                                    temp = intArray[j-1];
                                    intArray[j-1] = intArray[j];
                                    intArray[j] = temp;
                            }
                           
                    }
            }
   
    }
}
