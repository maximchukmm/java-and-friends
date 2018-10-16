package spring.ripper;

import javax.annotation.PostConstruct;

public class SpringQuoter implements Quoter {
    @InjectRandomInt(min = 2, max = 5)
    private int repeat;
    private String message;

    public SpringQuoter() {
        System.out.println("Phase 1");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @PostConstruct
    void init() {
        System.out.println("Phase 2");
    }

    @Override
    public void sayQuote() {
        for (int i = 0; i < repeat; i++) {
            System.out.println(message);
        }
    }
}
