package com.ryon.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionalTest {
    @Test
    void conditional() {
        // true
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        contextRunner.withUserConfiguration(Config1.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(MyBean.class);
                    assertThat(context).hasSingleBean(Config1.class);
                });

        // false
        new ApplicationContextRunner();
        contextRunner.withUserConfiguration(Config2.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(MyBean.class);
                    assertThat(context).doesNotHaveBean(Config1.class);
                });

//        // true
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(Config1.class);
//        context.refresh();
//        MyBean myBean = context.getBean(MyBean.class);
//
//        // False
//        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext();
//        context2.register(Config2.class);
//        context2.refresh();
//        MyBean myBean2 = context2.getBean(MyBean.class);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface BooleanConditional {
        boolean value();
    }


    @Configuration
    @BooleanConditional(true)
//    @Conditional(TrueCondition.class)
    static class Config1 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }


//    @Retention(RetentionPolicy.RUNTIME)
//    @Target(ElementType.TYPE)
//    @Conditional(FalseCondition.class)
//    @interface FalseConditional {}


    @Configuration
    @BooleanConditional(false)
//    @Conditional(FalseCondition.class)
    static class Config2 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }


    static class MyBean {}

    static class BooleanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            assert annotationAttributes != null;
            return (Boolean) annotationAttributes.get("value");
        }
    }


//    static class TrueCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            return true;
//        }
//    }
//
//    static class FalseCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            return false;
//        }
//    }
}
