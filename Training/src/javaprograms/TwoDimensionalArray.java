package javaprograms;

public class TwoDimensionalArray {

	public static void main(String[] args) {
		
		int i[][]= new int[2][2];
		
		i[0][0]=1;i[0][1]=2;i[1][0]=3;i[1][1]=4;
		
		for(int j=0;j<i.length;j++)
		{
			for(int h=0;h<i[j].length;h++)
			{
				System.out.print(i[j][h]+" ");
			}
			System.out.println();
				
			}
	
		}
		
	}

