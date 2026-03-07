package com.calendario.app.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Logged 
@Priority(2020) 
@Interceptor
public class LoggingInterceptor {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @AroundInvoke 
    Object logInvocation(InvocationContext context) throws Exception {
      // ...log before
    Object proceed = null;
    try {
        proceed = context.proceed();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        logger.error("Error occurred while invoking method: {}", context.getMethod().getName(), e);
        // Optionally, rethrow the exception if you want to propagate it
        throw e;
    }
      String name = context.getMethod().getDeclaringClass().getName();

      logger.info("....-> {}",name);
      // ...log after
      return proceed;
   }
}
