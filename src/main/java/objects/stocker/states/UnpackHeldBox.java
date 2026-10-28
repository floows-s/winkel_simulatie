package objects.stocker.states;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.path.Path;
import objects.stocker.Stocker;

public class UnpackHeldBox implements StockerState {
    Logger log = new TagLogger(getClass().getSimpleName());

    @Override
    public void update(Stocker st, double delta, long now, Simulation s) {
        if(!st.isHoldingBox()){
            log.logWarning("Attempted to unpack box while not holding one!");

            st.setState(new DecideNextTask());
            return;
        }

        System.out.println("TODO: UNPACK HELD BOX");
    }
}
