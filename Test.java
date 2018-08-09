import java.util.Scanner;
class ArrayList <E> {
	@SuppressWarnings("unchecked")
	private E[] list = (E[])new Object[5];
	private int size = 0;
	
	//add an element
	
	public void add(E o) {
		if(list.length-size == 0) {
			list = ChangeSize(list,5);			
			//list = Arrays.copyOf(list,list.length+5);
			System.out.println("new size"+list.length);
		}
		list[size++]=o;
	}
	
	// change size increment or decrement
	
	public E[] ChangeSize(E[] list,int growthFactor) {
		@SuppressWarnings("unchecked")
			E[] copy = (E[]) new Object[list.length+growthFactor];
				for(int i=0;i<size;i++) {
					
					copy[i]=list[i];
					
				}
				for(int i=size;i<copy.length;i++) {
					
					copy[i]=null;
					
				}
		return copy;
	}
	
	//display
	
	public void display() {
		for(int i=0;i<this.size;i++) {
			
			System.out.print(list[i] +" ");
		}
	}
	
	//current size
	
	public int currentSize() {
		return size;
	}
	// search and return the index of the given element
	public int search(E o) {
		int flag =0,i=-1;
        int front = 0, back = size - 1;
        while (front <= back)
        {
            if (list[front] == o || list[back] == o) {
            	flag=1;
            	i=(list[back]==o)? (back) : (front);
            	break;
            }
            front++;
            back--;
        }
         return ((flag==0)? (-1):(i));    
         
	}
	
	// remove the given element
	
	public void remove(E o) {
		int index=search(o);
			if (index==-1){
				System.out.println("Element not found");
			}
		else {
				removeAtIndex(index);
		}
	}
	
	// add given element at index
	
	public void add(E o,int index) {
		if(list.length-size == 0) {
			list = ChangeSize(list,5);
			//System.out.println("New capacity" + list.length);
		}
		if(index<size) {
			E temp1= list[index],temp2;
			list[index]=o;
			for(int i=index+1;i<size;i++) {
				temp2 = list[i];
				list[i] = temp1;
				temp1 = temp2;
			}
			add(temp1);
		}
		else if(index==size) {
			add(o);
		}
		else {
			System.out.println("invalid index");
		}
	}
	
	// remove element at index
	
	
	public void removeAtIndex(int index) {
		if (index<size) {
			list[index]=null;
			for(int i=index;i<size-1;i++) {
				list[i]=list[i+1];
				list[i+1]=null;
			}
			size--;
			if((list.length - size)>5) {
				list = ChangeSize(list,-2);
				System.out.println("after reducing size:"+list.length);
			}
		}
		
		else {
			System.out.println("invalid array index");
		}
	}
	// sorting 
	public void quickSort(E[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;
		if (low >= high)
			return;

		
			E pivot = arr[low];
		
		int i = low, j = high;
		while (i <= j) {
			while ((int)arr[i] < (int)pivot) {
					i++;
			}
			while ((int)arr[j] > (int)pivot) {
					j--;
			}
			if (i <= j) {
				E temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
					i++;
					j--;
				}
			}
		
		if (low < j)
			quickSort(arr, low, j);
		if (high > i)
			quickSort(arr, i, high);
		}

		public void Sort()
	    {
			int low =0;
			int high = size-1;
			quickSort(this.list,low,high);
	    }
	
	//find character at given index
	
	public Object charAt(int index) {
		if(index<size) {
			return list[index];
			//System.out.println("");
		}
		else {
			throw new ArrayIndexOutOfBoundsException();
		}
		
	}
}


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean b = true;
		int choice=1,passEle=0,passIndex=0;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList <Integer> a =new ArrayList();
		Scanner in = new Scanner(System.in);
		while(b) {
			System.out.println("\n1.add 2.display 3.search 4.remove 5.addAtIndex 6.removeAtIndex \n 7.charAt 8.Sort 9.Current Size 10.stop");
			//Scanner in = new Scanner(System.in);
			choice = in.nextInt();
			switch(choice) {
			case 1:
				System.out.println("enter the element");
				passEle = in.nextInt();
				a.add(passEle);
				break;
			case 2:
				a.display();
				break;
			case 3:
				System.out.println("enter the element");
				passEle = in.nextInt();
				System.out.println("Element found at" +a.search(passEle));
				break;
			case 4:
				System.out.println("enter the element to be removed");
				passEle = in.nextInt();
				a.remove(passEle);
				break;
			case 5:
				System.out.println("enter the element");
				passEle = in.nextInt();
				System.out.println("enter the index");
				passIndex = in.nextInt();
				a.add(passEle,passIndex);
				break;
			case 6:
				System.out.println("enter the index");
				passIndex = in.nextInt();
				a.removeAtIndex(passIndex);
				break;
			case 7:
				System.out.println("enter the index");
				passIndex = in.nextInt();
				System.out.println(a.charAt(passIndex));
				break;
			case 8:
				a.Sort();
				a.display();
				break;
			case 9:
				System.out.println("current size is :"+a.currentSize());
				break;
			case 10:
				b=false;
		
			}
		
		}
		in.close();
	}

}
