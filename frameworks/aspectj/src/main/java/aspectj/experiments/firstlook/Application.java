package aspectj.experiments.firstlook;

import aspectj.experiments.firstlook.service.MyFirstServiceImpl;
import aspectj.experiments.firstlook.service.MySecondServiceImpl;

public class Application {
    public static void main(String[] args) {
        MyFirstServiceImpl myFirstService = new MyFirstServiceImpl();
        MySecondServiceImpl mySecondService = new MySecondServiceImpl(myFirstService);

        myFirstService.helloWorld();
        mySecondService.helloWorld();
    }
}
