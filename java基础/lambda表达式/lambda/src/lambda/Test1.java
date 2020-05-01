package lambda;

public class Test1 {
	public static void main(String[] args) {
		System.out.println(test(-10, Math::abs));
	}
	
	private static int test(int x, MyFunctionInterface functionalInterface) {
		return functionalInterface.operate(x);
	}
}

@FunctionalInterface
interface MyFunctionInterface {
	int operate(int x);
}