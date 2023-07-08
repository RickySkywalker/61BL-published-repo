import org.junit.Test;

import static org.junit.Assert.*;

public class CodingChallengesTest {

    @Test
    public void test1() {
        String s1 = "cat", s2 = "cate";
        assertFalse(CodingChallenges.isPermutation(s1, s2));



    }
}