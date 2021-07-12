package benchmark.team;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public team class Team1 {

	// static Logger logger = LoggerFactory.getLogger(Team1.class);

	public class T1_Role1 playedBy Base {

		public void before(float n) {
			// logger.info("before T1_Role1 operation1");
			n = n / 2;
		}

		callin float replace(float n) {
			// logger.info("replace T1_Role1 operation1 BEGIN");
			float f = base.replace(n / 2);
			// logger.info("replace T1_Role1 operation1 END");
			return f;
		}

		public void after(float n) {
			// logger.info("after T1_Role1 operation1");
			n = n * 2;
		}

		void before(float n) <- before float operation1(float n);

		float replace(float n) <- replace float operation1(float n);

		void after(float n) <- after float operation1(float n);

	}

	public class T1_Role2 playedBy Base {

		public void before(float n) {
			// logger.info("before T1_Role2 operation2");
			n = n * 2;
		}

		callin float replace(float n) {
			// logger.info("replace T1_Role2 operation2 BEGIN");
			float f = base.replace(n * 2);
			// logger.info("replace T1_Role2 operation2 END");
			return f;
		}

		public void after(float n) {
			// logger.info("after T1_Role2 operation2");
			n = n / 2;
		}

		void before(float n) <- before float operation2(float n);

		float replace(float n) <- replace float operation2(float n);

		void after(float n) <- after float operation2(float n);

	}

}