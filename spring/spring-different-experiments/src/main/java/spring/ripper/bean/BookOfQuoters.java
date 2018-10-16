package spring.ripper.bean;

import spring.ripper.Quoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookOfQuoters {
    private List<Quoter> book = new ArrayList<>();

    @Autowired
    public BookOfQuoters(Quoter springQuoter,
                         Quoter terminatorQuoter) {
        book.add(springQuoter);
        book.add(terminatorQuoter);
    }

    public void sayQuotes() {
        book.forEach(Quoter::sayQuote);
    }
}
