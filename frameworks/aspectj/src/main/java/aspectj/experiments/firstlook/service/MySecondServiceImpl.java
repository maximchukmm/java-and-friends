package aspectj.experiments.firstlook.service;

import aspectj.experiments.firstlook.aspect.AdditionalInfoAboutMethod;

public class MySecondServiceImpl implements MySecondService {
    private MyFirstService myFirstService;

    public MySecondServiceImpl(MyFirstService myFirstService) {
        this.myFirstService = myFirstService;
    }

    @AdditionalInfoAboutMethod("secondService")
    @Override
    public void helloWorld() {
        System.out.println("Hello from Second service! And also...");
        myFirstService.helloWorld();
    }
}
