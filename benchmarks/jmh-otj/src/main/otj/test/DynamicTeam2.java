package test;

public team class DynamicTeam2 {
    public class DynamicRole playedBy BaseType {

        private int beforeValue = 0;
        private int afterValue = 0;

        void beforeRoleMethod(int arg) {
            beforeValue += arg;
        }

        void afterRoleMethod(int arg) {
            afterValue += arg;
        }

        callin int replaceRoleMethod(int arg) {
            int value = arg + 2;
            int result = base.replaceRoleMethod(value);
            return result;
        }

        beforeRoleMethod <- before retParam;
        replaceRoleMethod <- replace retParam;
        afterRoleMethod <- after retParam;
    }
}
