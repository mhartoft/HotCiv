package hotciv.standard;

import hotciv.common.WorldLayoutStrategy;
import hotciv.variants.alphaciv.AlphaCivFactory;

/**
 * Created by mha2908 on 04/12/14.
 */
public class FractalMapFactoryStub extends AlphaCivFactory {

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new FractalMapAdapter();
    }
}
