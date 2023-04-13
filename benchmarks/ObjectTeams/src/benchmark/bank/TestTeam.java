package benchmark.bank;


public team class TestTeam {

    public class TestRole playedBy Account {

        callin float testReplace(float value) {
            System.out.println("test replace");
            float f = base.testReplace(value);
            return f;
        }

        callin float testReplace2(float value) {
            System.out.println("test replace");
            float f = base.testReplace2(value);
            return f;
        }

        public void after() {
            System.out.println("test after");
        }

        public void before() {
            System.out.println("test before");
        }

        one: float testReplace2(float value) <- replace float decrease(float amount);
        two: float testReplace(float value) <- replace float decrease(float amount);

        precedence one, two;

        void after() <- after float decrease(float amount);
        void before() <- before float decrease(float amount);

    }
}
