package aspectj.experiments.firstlook.service;

import aspectj.experiments.firstlook.aspect.AdditionalInfoAboutMethod;

public class MyFirstServiceImpl implements MyFirstService {
    @AdditionalInfoAboutMethod("firstService")
    @Override
    public void helloWorld() {
        System.out.println("Hello from First service!");
    }
}
