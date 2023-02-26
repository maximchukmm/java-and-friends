package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode({Mode.AverageTime})
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
public class ListSetContains {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListSetContains.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({"10", "100", "1000", "10000", "100000", "1000000"})
        public int size;

        public List<Integer> list;
        public Set<Integer> set;

        public Integer nonExistingElement;
        public Integer firstElement;
        public Integer middleElement;
        public Integer lastElement;

        @Setup(Level.Iteration)
        public void setUp() {
            nonExistingElement = -1;
            firstElement = 0;
            lastElement = Integer.MAX_VALUE;

            Random random = new Random();
            list = new ArrayList<>();
            set = new HashSet<>();

            list.add(firstElement);
            set.add(firstElement);

            for (int i = 1; i < size - 1; i++) {
                int v = random.nextInt(1, Integer.MAX_VALUE);
                list.add(v);
                set.add(v);

                if (size / i == 2) {
                    middleElement = v;
                }
            }

            list.add(lastElement);
            set.add(lastElement);
        }
    }

    @Benchmark
    public void listFirstExistingElement(ExecutionPlan plan, Blackhole blackhole) {
        boolean b = plan.list.contains(plan.firstElement);
        blackhole.consume(b);
    }
//
//    @Benchmark
//    public void setFirstExistingElement(ExecutionPlan plan, Blackhole blackhole) {
//        boolean b = plan.set.contains(plan.firstElement);
//        blackhole.consume(b);
//    }
//
//    @Benchmark
//    public void listMiddleExistingElement(ExecutionPlan plan, Blackhole blackhole) {
//        boolean b = plan.list.contains(plan.middleElement);
//        blackhole.consume(b);
//    }
//
//    @Benchmark
//    public void setMiddleExistingElement(ExecutionPlan plan, Blackhole blackhole) {
//        boolean b = plan.set.contains(plan.middleElement);
//        blackhole.consume(b);
//    }
//
//    @Benchmark
//    public void listLastExistingElement(ExecutionPlan plan, Blackhole blackhole) {
//        boolean b = plan.list.contains(plan.lastElement);
//        blackhole.consume(b);
//    }
//
//    @Benchmark
//    public void setLastExistingElement(ExecutionPlan plan, Blackhole blackhole) {
//        boolean b = plan.set.contains(plan.lastElement);
//        blackhole.consume(b);
//    }
//
//    @Benchmark
//    public void listNonExistingElement(ExecutionPlan plan, Blackhole blackhole) {
//        boolean b = plan.list.contains(plan.nonExistingElement);
//        blackhole.consume(b);
//    }
//
//    @Benchmark
//    public void setNonExistingElement(ExecutionPlan plan, Blackhole blackhole) {
//        boolean b = plan.set.contains(plan.nonExistingElement);
//        blackhole.consume(b);
//    }
}
