package aspectj.experiments.firstlook.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AdditionalInfoAboutMethodAspect {
    @Pointcut("@annotation(additionalInfoAboutMethod)")
    public void callAt(AdditionalInfoAboutMethod additionalInfoAboutMethod) {
    }

    @Around("callAt(additionalInfoAboutMethod)")
    public Object around(ProceedingJoinPoint pjp, AdditionalInfoAboutMethod additionalInfoAboutMethod) throws Throwable {
        System.out.println("FROM ANNOTATION: " + additionalInfoAboutMethod.value());
        return pjp.proceed();
    }
}
