package benchmark.bank;

public team class CallinTransactionVerbose {

	public class Source playedBy Account {

		public void before(float a) {
		    //logger.info("Source before");
        }

		callin float withDraw(float amount) {
        System.out.println("Source replace BEGIN");
			float f = base.withDraw(amount);
      System.out.println("Source replace END");
			return f;
		}

		public void after() {
		    // logger.info("Source after");
		}

    	void before(float a) <- before float decrease(float amount);

		float withDraw(float amount) <- replace float decrease(float amount);
        
        //void after() <- after void decrease(float amount);
	}

	public class Target playedBy Account {

		callin float deposit(float amount) {
			System.out.println("Target replace BEGIN");
			float f = base.deposit(amount);
			System.out.println("Target replace END");
			return f;
		}

		float deposit(float amount) <- replace float increase(float amount);
	}

	// @ImplicitTeamActivation
	public boolean execute(Account f, Account t, float a) {
		f.decrease(a);

		t.increase(a);
		return true;
	}
}
