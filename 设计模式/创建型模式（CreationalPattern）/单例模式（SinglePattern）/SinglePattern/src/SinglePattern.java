public class SinglePattern {
//    private static SinglePattern singlePattern = new SinglePattern();
//
//    // 利用静态块创建对象
////    private static SinglePattern singlePattern;
////
////    static {
////        singlePattern = new SinglePattern();
////    }
//
//    public SinglePattern getInstance() {
//        return SinglePattern.singlePattern;
//    }

//    private static SinglePattern singlePattern;
//
//    public SinglePattern getInstance() {
//        //第一种 线程不安全
//        if (singlePattern != null) {
//            synchronized (SinglePattern.class) {
//                singlePattern = new SinglePattern();
//            }
//        }
//        // 第二种 效率低
////        synchronized (SinglePattern.class) {
////             if (singlePattern != null) {
////                singlePattern = new SinglePattern();
////            }
////        }
//        return singlePattern;
//    }

//    private static SinglePattern singlePattern;
//
//    public SinglePattern getInstance() {
//        if (singlePattern != null) {
//            synchronized (SinglePattern.class) {
//                if (singlePattern != null)
//                    singlePattern = new SinglePattern();
//            }
//        }
//        return singlePattern;
//    }

    private static class InnerSinglePattern {
        private static SinglePattern singlePattern = new SinglePattern();
    }

    public SinglePattern getInstance() {
        return InnerSinglePattern.singlePattern;
    }

    private SinglePattern() {}

}
