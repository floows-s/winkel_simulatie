package objects.stocker.states;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.box.Box;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.shelf.Shelf;
import objects.stocker.Stocker;

public class GrabBoxFromShelf implements StockerState {
    private VertexID shelfAccessPoint;
    private Path pathToShelf = null;

    Logger log = new TagLogger(this.getClass().getSimpleName());

    public GrabBoxFromShelf(VertexID shelfAccessPoint){
        this.shelfAccessPoint = shelfAccessPoint;
    }

    @Override
    public void update(Stocker st, double delta, long now, Simulation s) {
        if(st.getCurrentVertexID() == shelfAccessPoint){
            Shelf shelf = s.getShelfManager().get(shelfAccessPoint);

            // Only grab a box that can actually be unpacked
            Box box = shelf.grabBoxMatching(s.getShelfManager()::canBeUnpacked);
            if(box == null){
                log.logWarning("Attempted to grab a box from shelf which doesnt contain a usable box!");
                st.setState(new DecideNextTask());
                return;     // <-- this return was missing before
            }

            st.holdBox(box);
            st.setState(new UnpackHeldBox());

            return;
        }

        if(pathToShelf == null){
            pathToShelf = s.getGraph().getShortestPath(st.getCurrentVertexID(), shelfAccessPoint);
        }

        st.followPath(pathToShelf, s.getGraph(), delta, now);
    }

}
