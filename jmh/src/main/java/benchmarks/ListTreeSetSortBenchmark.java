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
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;


@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode({Mode.AverageTime})
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
public class ListTreeSetSortBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListTreeSetSortBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

//        @Param({"10", "100", "1000", "10000"})
        @Param({"10", "100", "1000", "10000", "100000", "1000000"})
        public int size;

        public Random random;

        @Setup(Level.Iteration)
        public void setUp() {
            random = new Random(size);
        }

        public int randomInt() {
            return random.nextInt();
        }
    }

    @Benchmark
    public void list(ExecutionPlan plan, Blackhole blackhole) {
        List<Integer> list = new ArrayList<>(plan.size);
//        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < plan.size; i++) {
            list.add(plan.randomInt());
        }

        list.sort(Comparator.naturalOrder());

        blackhole.consume(list);
    }

    @Benchmark
    public void treeSet(ExecutionPlan plan, Blackhole blackhole) {
        Set<Integer> set = new TreeSet<>();

        for (int i = 0; i < plan.size; i++) {
            set.add(plan.randomInt());
        }

        List<Integer> list = new ArrayList<>(set);

        blackhole.consume(list);
    }
}
