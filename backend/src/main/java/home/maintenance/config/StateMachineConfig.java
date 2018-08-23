package home.maintenance.config;

import home.maintenance.model.AdminAction;
import home.maintenance.model.UserState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<UserState, AdminAction> {

    @Override
    public void configure(StateMachineStateConfigurer<UserState, AdminAction> states) throws Exception {
        states.withStates()
                .initial(UserState.DRAFT)
                .states(EnumSet.allOf(UserState.class));

        StateMachine<UserState, AdminAction> stateMachine = null;
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UserState, AdminAction> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(UserState.DRAFT).target(UserState.ACTIVE)
                    .event(AdminAction.APPROVE)
                    .and()
                .withExternal()
                    .source(UserState.ACTIVE).target(UserState.BLOCKED)
                    .event(AdminAction.BLOCK)
                    .and()
                .withExternal()
                    .source(UserState.BLOCKED).target(UserState.ACTIVE)
                    .event(AdminAction.UNBLOCK);
    }
}
