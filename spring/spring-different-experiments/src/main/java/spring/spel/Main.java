package spring.spel;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(SpelConfiguration.class);
        ValueAnnotationDemoBean bean = appContext.getBean(ValueAnnotationDemoBean.class);
        System.out.println(bean);

        System.out.println();

        Map<String, Object> data = new HashMap<>();
        data.put("property", 123);
        String message = "#data['property']";
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("data", data);
        System.out.println(parser.parseExpression(message).getValue(evaluationContext, String.class));

        System.out.println();

        String template1 = "'Начало ' + '\"' + getWorkActivityName() + '\"' + ' через ' + convertMinutesToString()";
        System.out.println("template1 = " + template1);
        TemplateDemo templateDemo = new TemplateDemo("Рабочее время", 75);
        StandardEvaluationContext template1Context = new StandardEvaluationContext(templateDemo);
        System.out.println("parse = " + parser.parseExpression(template1).getValue(template1Context, String.class));

        System.out.println();

        MessageData messageData = new MessageData(80);
        messageData.setWorkActivityName("Перерыв");
        String template2 = "Начало #{getWorkActivityName()} через #{getTime()}";
        System.out.println("template2 = " + template2);
        StandardEvaluationContext template2Context = new StandardEvaluationContext(messageData);
        System.out.println("parse = " + parser.parseExpression(template2, new TemplateParserContext()).getValue(template2Context, String.class));

    }

    private static class MessageData {
        private static final int MINUTES_IN_HOUR = 60;

        private String workActivityName;
        /**
         * Минуты, которые нужно перевести в строковое представление
         */
        private int minutes;

        MessageData(String workActivityName, int minutes) {
            this.workActivityName = workActivityName;
            this.minutes = minutes;
        }

        MessageData(int minutes) {
            this.minutes = minutes;
            this.workActivityName = "";
        }

        /**
         * @return название активности WFM в кавычках
         */
        public String getWorkActivityName() {
            return "\"" + workActivityName + "\"";
        }

        public String getTime() {
            StringBuilder messageBuilder = new StringBuilder();
            if (minutes >= MINUTES_IN_HOUR) {
                int hours = minutes / MINUTES_IN_HOUR;
                minutes %= MINUTES_IN_HOUR;
                messageBuilder
                    .append(String.valueOf(hours))
                    .append(" ")
                    .append("час");
            }
            if (minutes > 0)
                messageBuilder
                    .append(" ")
                    .append(String.valueOf(minutes))
                    .append(" ")
                    .append("минут");
            return messageBuilder.toString();
        }

        void setWorkActivityName(String workActivityName) {
            this.workActivityName = workActivityName;
        }
    }
}
