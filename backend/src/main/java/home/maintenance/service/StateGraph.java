package home.maintenance.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Buibi on 29.01.2017.
 */
public abstract class StateGraph<S extends Enum, C extends Enum> {
    final private Map<Node, S> nodes = new HashMap<>();

    final public S next(S state, C condition) {
        return nodes.get(new Node(state, condition));
    }

    final void add(S state1, S state2, C condition) {
        nodes.put(new Node(state1, condition), state2);
    }
    private class Node {
        private S state;
        private C condition;
        Node(S state, C condition) {
            this.state = state;
            this.condition = condition;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != StateGraph.Node.class) return false;
            StateGraph.Node that = (StateGraph.Node) obj;
            return Objects.equals(state, that.state) && Objects.equals(condition, that.condition);
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, condition);
        }
    }
}

