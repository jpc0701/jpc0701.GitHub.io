package InheritableThreLocal1;

public class ThreadB extends Thread {
    @Override
    public void run() {
        try {
            Tools.t1.set("ThreadB改变值！");
            System.out.println("在ThreadB中改变值！");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
