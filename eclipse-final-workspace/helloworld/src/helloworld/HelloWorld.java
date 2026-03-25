package helloworld;

public class HelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("HELLO NOW.");
		System.out.println("Hello to you too.");

		
		
		System.out.println("hello from the alt aaron branch");
		System.out.println("Hello from the aaron branch.");
		
		System.out.println(add(5, 10));
		System.out.println(subtract(5, 10));
		
		try {
			System.out.println(5/0);
		} 
		catch (ArithmeticException e) {
			System.out.println("Exception: " + e);
		}
		
		System.out.println("Hello from Ohwen. hWk mo NG BEta");
	}

	static int add(int x, int y) {
		return x + y;
	}

	static int subtract(int x, int y) {
		return x - y;
	}
}
