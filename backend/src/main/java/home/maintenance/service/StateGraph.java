package home.maintenance.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Buibi on 29.01.2017.
 */
public abstract class StateGraph<S, C> {
    final private Map<Node<S, C>, S> nodes = new HashMap<>();

    final public S next(S state, C condition) {
        S next = nodes.get(new Node(state, condition));
        return next;
    }

    final protected StateGraph add(S state1, S state2, C condition) {
        nodes.put(new Node(state1, condition), state2);
        return this;
    }
}

class Node<S, C> {
    private S state;
    private C condition;
    public Node(S state, C condition) {
        this.state = state;
        this.condition = condition;
    }

    public S getState() {
        return state;
    }

    public C getCondition() {
        return condition;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) return false;
        Node that = (Node) obj;
        return Objects.equals(state, that.state) && Objects.equals(condition, that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, condition);
    }
}