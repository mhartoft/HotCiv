package hotciv.variants.deltaciv;

import hotciv.common.*;
import hotciv.variants.alphaciv.*;

/**
 * Created by mha2908 on 20/11/14.
 */
public class DeltaCivFactory extends AlphaCivFactory {
    private String[] s;
    public DeltaCivFactory(String[] s) {
        this.s = s;
    }
    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new DeltaCivWorldLayoutStrategy(s);
    }
}
