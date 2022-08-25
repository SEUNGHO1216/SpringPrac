package com.prac.springprac.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//aop 사용하려면 붙여야하는 annotation
@Aspect
@Component
public class TimeTraceAop {

    //매뉴얼 대로 기본 형식처럼 씀
    @Around("execution(* com.prac.springprac..*(..))") //프로젝트 폴더 내 지정 하위 디렉토리에 모두 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{

        long start = System.currentTimeMillis();
        System.out.println("START : "+joinPoint.toString());
        try{
            //joinPoint
            return joinPoint.proceed();
        }finally {
            long end = System.currentTimeMillis();
            long timeMs = end - start;
            System.out.println("END : "+joinPoint.toString()+" "+timeMs+"ms");
        }
    }
}
