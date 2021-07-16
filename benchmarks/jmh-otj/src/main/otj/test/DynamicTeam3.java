package test;

public team class DynamicTeam3 {
    public class DynamicRole playedBy BaseType {

        Object beforeRoleMethod(Object arg) {
            Object result = new Object();
            return result;
        }

        Object afterRoleMethod(Object arg) {
            Object result = new Object();
            return result;
        }

        callin Object replaceRoleMethod(Object arg) {
            Object result = new Object();
            return base.replaceRoleMethod(result);
        }

        beforeRoleMethod <- before retParam;
        replaceRoleMethod <- replace retParam;
        afterRoleMethod <- after retParam;
    } 
}
