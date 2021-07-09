package benchmark;

import benchmark.team.Base;
import benchmark.team.Team1;
import benchmark.team.Team2;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class TeamBenchmark5 extends Benchmark {

	// static Logger logger = LoggerFactory.getLogger(TeamBenchmark4.class);

	private Team1 team11;
	private Team1 team12;
	private Team2 team21;
	private Team2 team22;
	private Base a;
	private Base b;

	@Override
	public boolean innerBenchmarkLoop(final int innerIterations) {
		int iterations = innerIterations * innerIterations;
		float n = 100.0f;
		for (int i = 0; i < iterations; i++) {
			// logger.info("-------- Context 1: Team1 --------");						// TeamBenchmark4: 1, Team1: 1, Team2: 0
			team11.activate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 2: Team2, Team1 --------");				// TeamBenchmark4: 2, Team1: 1, Team2: 1
			team21.activate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 3: Team2, Team2, Team1 --------");			// TeamBenchmark4: 3, Team1: 1, Team2: 2
			team22.activate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 4: Team2, Team2 --------");				// TeamBenchmark4: 4, Team1: 1, Team2: 4
			team11.deactivate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 5: Team2 --------");						// TeamBenchmark4: *5*, Team1: 1, Team2: 4
			// Unstable in TeamBenchmarks4
			team21.deactivate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 6: Team1, Team2 --------");				// TeamBenchmark4: *6*, Team1: 2, Team2: 4
			team11.activate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 7: Team1, Team1, Team2 --------");			// TeamBenchmark4: *7*, Team1: 3, Team2: 4
			team12.activate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 8: Team1, Team1 --------");				// TeamBenchmark4: *8*, Team1: 4, Team2: 4
			team22.deactivate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 9: Team2, Team1, Team1 --------");			// TeamBenchmark4: *9*, Team1: 4, Team2: *5*
			team21.activate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 10: Team2, Team2, Team1, Team1 --------");	// TeamBenchmark4: *10*, Team1: 4, Team2: *6*
			team22.activate();
			a.operation1(n);
			b.operation2(n);

			// logger.info("-------- Context 11: No active teams --------");			// TeamBenchmark4: *11*, Team1: 4, Team2: *6*
			team22.deactivate();
			team21.deactivate();
			team12.deactivate();
			team11.deactivate();
			a.operation1(n);
			b.operation2(n);
		}

		return true;
	}

	public boolean setUp(final int innerIterations) {
        team11 = new Team1();
        team12 = new Team1();
        team21 = new Team2();
        team22 = new Team2();
        a = new Base(1);
        b = new Base(2);

        return true;
    }

    @Override
    public Object benchmark() {
        throw new RuntimeException("Should never be reached");
    }

    @Override
    public boolean verifyResult(Object result) {
        throw new RuntimeException("Should never be reached");
    }
}