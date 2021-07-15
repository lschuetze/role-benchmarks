package test;

import test.BaseType;
import test.DynamicTeam;

import java.util.concurrent.TimeUnit;

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
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DynamicBenchmark8 {

    final int N = 4;

    private DynamicTeam[] teams;
    private BaseType b;   
    private Object arg; 

    @Setup(Level.Trial)
    public void setupTeam() {
        teams = new DynamicTeam[N];
        for(int i = 0; i < N; i++) {
            teams[i] = new DynamicTeam();
        }
    }

    @Setup(Level.Iteration)
    public void setup() {
        b = new BaseType();
        arg = new Object();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        for(int i = 0; i < N; i++) {
            teams[i].deactivate();
        }
    }

    @Benchmark
    public Object bench() {
        teams[0].activate();
        Object result = b.retParam(arg);

        teams[1].activate();
        result = b.retParam(result);

        teams[2].activate();
        result = b.retParam(result);

        teams[0].deactivate();
        result = b.retParam(result);

        teams[1].deactivate();
        result = b.retParam(result);

        teams[0].activate();
        result = b.retParam(result);

        teams[3].activate();
        result = b.retParam(result);

        teams[2].deactivate();
        return b.retParam(result);
    }
}