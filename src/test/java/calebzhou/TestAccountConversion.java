package calebzhou;

import calebzhou.rdicloudrest.module.account.AccountUtils;

public class TestAccountConversion {
    public static void main(String[] args) {
        String s = AccountUtils.qq2rdiAccount(1037414277L);
        long s1 = AccountUtils.rdi2qqAccount("dwwfzrkf");
        System.out.println(s);
        System.out.println(s1);
    }
}
