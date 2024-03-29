package test;

import test.BaseType;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.List;

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
public class ManyReplaceCallinsBenchmark {

    @Param({"1"})
    public int cnt;

    private List<BaseCallTeam> myTeams;
    private BaseType b;   
    private int arg; 

    @Setup(Level.Trial)
    public void setupTeam() {
        myTeams = new ArrayList<>(cnt);
        for(int i = 0; i < cnt; i++) {
            BaseCallTeam myTeam = new BaseCallTeam();
            myTeam.activate();
            myTeams.add(myTeam);
        }
    }

    @Setup(Level.Iteration)
    public void setup() {
        b = new BaseType();
        arg = 0;
    }

    @TearDown(Level.Trial)
    public void teardown() {
        for(int i = 0; i < cnt; i++) {
            myTeams.get(i).deactivate();
        }
    }

    @Benchmark
    public int methodCall() {
        return b.retParam(arg);
    }
}
