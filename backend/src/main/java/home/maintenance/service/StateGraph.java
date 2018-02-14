package home.maintenance.service;

import home.maintenance.model.Persistable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * Created by Buibi on 29.01.2017.
 */
public abstract class StateGraph<S extends Enum, C extends Enum, M extends Persistable> {
    final private Map<Node, StateAndAction> nodes = new HashMap<>();
    final private BiConsumer<S, M> noAction = (state, model) -> {};

    final public S next(S state, C condition) {
        StateAndAction stateAndAction = nodes.get(new Node(state, condition));
        return stateAndAction == null ? null : stateAndAction.state;
    }

    final public S next(S state, C condition, M model) {
        StateAndAction sa = nodes.get(new Node(state, condition));
        if (sa == null) return null;
        sa.consumer.accept(sa.state, model);
        return sa.state;
    }

    final public S next(S state, C condition, M model, BiConsumer<S, M> action) {
        StateAndAction sa = nodes.get(new Node(state, condition));
        if (sa == null) return null;
        action.accept(sa.state, model);
        return sa.state;
    }

    final public S next(S state, C condition, Predicate<M> extraConditions, M model, BiConsumer<S, M> action) {
        StateAndAction sa = nodes.get(new Node(state, condition));
        if (extraConditions.test(model)) action.accept(sa.state, model);
        return sa.state;
    }

    final void add(S state1, S state2, C condition) {
        add(state1, state2, condition, defaultAction());
    }

    final void add(S state1, S state2, C condition, BiConsumer<S, M> action) {
        nodes.put(new Node(state1, condition), new StateAndAction(state2, action));
    }

    protected BiConsumer<S, M> defaultAction() {
        return noAction;
    }

    @EqualsAndHashCode
    @ToString
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    private class Node {
        private final S state;
        private final C condition;
    }

    @EqualsAndHashCode
    @ToString(exclude = "consumer")
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    private class StateAndAction {
        private final S state;
        private final BiConsumer<S, M> consumer;
    }
}

