package edu.tasks.erlangc;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Based on article http://www.mitan.co.uk/erlang/elgcmath.htm
 */
public class ErlangCFormula {
    public static void main(String[] args) {
        BigDecimal targetAnswerTime = new BigDecimal("15");
        print(targetAnswerTime, "targetAnswerTime");

        BigDecimal callsPerHalfHour = new BigDecimal("360"); //calls per half hour
        print(callsPerHalfHour, "callsPerHalfHour");

        //1) Specify call arrival rate
        BigDecimal averageArrivalRate = callsPerHalfHour.divide(BigDecimal.valueOf(30 * 60L), MathContext.DECIMAL128); //calls per second
        print(averageArrivalRate, "averageArrivalRate");

        //2) Specify call duration
        BigDecimal averageCallDuration = new BigDecimal("240"); //in seconds per call
        print(averageCallDuration, "averageCallDuration");

        //3) Specify number of agents
        BigDecimal numberOfAgents = new BigDecimal("55");
        print(numberOfAgents, "numberOfAgents");

        //4) Calculate traffic intensity
        BigDecimal trafficIntensity = averageArrivalRate.multiply(averageCallDuration);
        print(trafficIntensity, "trafficIntensity");

        //5) Calculate agent occupancy
        BigDecimal agentOccupancy = trafficIntensity.divide(numberOfAgents, MathContext.DECIMAL128);
        print(agentOccupancy, "agentOccupancy");

        //6) Calculate the Erlang-C formula
        BigDecimal erlangC = calculateErlangC(numberOfAgents, trafficIntensity);
        print(erlangC, "erlangC");

        //7) Calculate probability of waiting
        BigDecimal probabilityOfWaiting = erlangC;
        print(probabilityOfWaiting, "probabilityOfWaiting");

        //8) Calculate average speed of answer (ASA) - average waiting time
        BigDecimal averageWaitingTime = erlangC
            .multiply(averageCallDuration)
            .divide(numberOfAgents.multiply(BigDecimal.ONE.subtract(agentOccupancy)), MathContext.DECIMAL128);
        print(averageWaitingTime, "averageWaitingTime");

        //9) Calculate service level
        double powerForExponent = BigDecimal.valueOf(-1).multiply(
            numberOfAgents.subtract(trafficIntensity).multiply(targetAnswerTime.divide(averageCallDuration, MathContext.DECIMAL128))
        ).doubleValue();
        double exponentInPower = Math.pow(Math.E, powerForExponent);
        BigDecimal serviceLevel = BigDecimal.ONE.subtract(erlangC.multiply(BigDecimal.valueOf(exponentInPower)));
        print(serviceLevel, "serviceLevel");
    }

    private static void print(BigDecimal value, String description) {
        System.out.println(String.format("%-20s = %s", description, value.toString()));
    }

    private static BigDecimal calculateErlangC(BigDecimal numberOfAgents, BigDecimal trafficIntensity) {
        BigDecimal a = trafficIntensity
            .pow(numberOfAgents.toBigInteger().intValue())
            .divide(factorial(numberOfAgents.toBigInteger().intValue()), MathContext.DECIMAL128);

        BigDecimal b = BigDecimal.ONE.subtract(trafficIntensity.divide(numberOfAgents, MathContext.DECIMAL128));

        BigDecimal c = BigDecimal.ZERO;

        for (int i = 0; i <= numberOfAgents.toBigInteger().intValue() - 1; i++) {
            c = c.add(trafficIntensity
                .pow(i)
                .divide(factorial(i), MathContext.DECIMAL128));
        }


        BigDecimal erlangC = a.divide(a.add(b.multiply(c)), MathContext.DECIMAL128);

        return erlangC;
    }

    private static BigDecimal factorial(int n) {
        BigDecimal f = BigDecimal.ONE;

        for (int i = 2; i <= n; i++) {
            f = f.multiply(BigDecimal.valueOf(i));
        }

        return f;
    }
}
