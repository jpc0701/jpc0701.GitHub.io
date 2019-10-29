package InheritableThreLocal1;

public class Run {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; i ++) {
                System.out.println("在Main线程中取值=" + Tools.t1.get());
                Thread.sleep(100);
            }
            Thread.sleep(5000);
            Thread a = new ThreadA();
            a.start();
            Thread.sleep(5000);
            Thread b = new ThreadB();
            b.start();
            Thread.sleep(5000);
            for (int i = 0; i < 10; i ++) {
                System.out.println("在Main线程中取值=" + Tools.t1.get());
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
