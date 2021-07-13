package test;

import test.BaseType;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
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
public class NoopBenchmark {

    private BaseCallTeam myTeam;
    private BaseType b;   
    private Object arg; 

    @Setup(Level.Trial)
    public void setupTeam() {
        myTeam = new BaseCallTeam();
        myTeam.activate();
        myTeam.deactivate();
    }

    @Setup(Level.Iteration)
    public void setup() {
        b = new BaseType();
        arg = new Object();
    }

    @Benchmark
    public Object methodCall() {
        return b.retParam(arg);
    }

}
