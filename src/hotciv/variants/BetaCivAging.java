package hotciv.variants;

import hotciv.common.AgingStrategy;

/**
 * Created by mha2908 on 11/11/14.
 */
public class BetaCivAging implements AgingStrategy {
    @Override
    public int age(int oldAge) {
        if (oldAge < -100) return oldAge+100;
        if (oldAge == -100) return -1;
        if (oldAge == -1) return 1;
        if (oldAge == 1) return 50;
        if (oldAge < 1750) return oldAge+50;
        if (oldAge < 1900) return oldAge+25;
        if (oldAge < 1970) return oldAge+5;
        return oldAge+1;
    }
}
