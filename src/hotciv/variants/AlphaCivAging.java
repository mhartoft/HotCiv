package hotciv.variants;

import hotciv.common.AgingStrategy;

/**
 * Created by mha2908 on 11/11/14.
 */
public class AlphaCivAging implements AgingStrategy {

    @Override
    public int age(int oldAge) {
        return oldAge+100;
    }
}
