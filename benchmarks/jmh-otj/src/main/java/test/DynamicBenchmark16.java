package test;

import test.BaseType;
import test.DynamicTeam1;
import test.DynamicTeam2;
import test.DynamicTeam3;
import test.DynamicTeam4;

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
public class DynamicBenchmark16 {

    private DynamicTeam1 team1;
    private DynamicTeam2 team2;
    private DynamicTeam3 team3;
    private DynamicTeam4 team4;

    private BaseType b;   
    private Object arg; 

    @Setup(Level.Trial)
    public void setupTeam() {
        team1 = new DynamicTeam1();
        team2 = new DynamicTeam2();
        team3 = new DynamicTeam3();
        team4 = new DynamicTeam4();
    }

    @Setup(Level.Iteration)
    public void setup() {
        b = new BaseType();
        arg = new Object();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        team1.deactivate();
        team2.deactivate();
        team3.deactivate();
        team4.deactivate();
    }

    @Benchmark
    public Object bench() {
        //1
        team1.activate();
        Object result = b.retParam(arg);

        //21
        team2.activate();
        result = b.retParam(arg);

        //321
        team3.activate();
        result = b.retParam(arg);

        //4321
        team4.activate();
        result = b.retParam(arg);

        //432
        team1.deactivate();
        result = b.retParam(arg);

        //43
        team2.deactivate();
        result = b.retParam(arg);

        //3
        team4.deactivate();
        result = b.retParam(arg);

        //2
        team3.deactivate();
        team2.activate();
        result = b.retParam(arg);

        //-
        team2.deactivate();
        result = b.retParam(arg);

        //4
        team4.activate();
        result = b.retParam(arg);

        //14
        team1.activate();
        result = b.retParam(arg);

        //214
        team2.activate();
        result = b.retParam(arg);

        //314
        team2.deactivate();
        team3.activate();
        result = b.retParam(arg);

        //234
        team1.deactivate();
        team2.activate();
        return b.retParam(result);
    }
}