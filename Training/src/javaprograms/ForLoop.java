package javaprograms;

public class ForLoop {

	public static void main(String[] args) {

		/*	int i=100;
		for(i=100;i>=93;i--)
		{
			System.out.println("*");

		} 

		 */	

		/*   for(int i=1; i<=10; i++)
			    {

			         System.out.println(i);
			    }

		 */
		//for (int k=9;k>=0;k--){
		int i=0;
		int j=0;

		for( j=0; j<=5; j++){


			System.out.println();	

			for(i=0;i<=j;i++){

				System.out.print("*");      
			}
		}
		for(j=5;j>=0;j--)
		{

			System.out.println();
			for(i=0;i<=j;i++){
				System.out.print("*");

			}

		}




	}     
}




