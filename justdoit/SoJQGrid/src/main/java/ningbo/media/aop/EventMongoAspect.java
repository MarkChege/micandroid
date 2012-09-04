package ningbo.media.aop;

import java.util.Date;

import ningbo.media.domain.ErrorLog;
import ningbo.media.domain.Event;
import ningbo.media.dto.ResponseDto;
import ningbo.media.repository.mongo.IErrorLogRepository;
import ningbo.media.util.ErrorUtil;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(2)
@Component
public class EventMongoAspect {

	protected Logger logger = Logger.getLogger("aop");
	
	@Autowired
	private IErrorLogRepository errorLogRepository;
	
	@Around("execution(* ningbo.media.service.EventService.*(..))")
	public Object interceptService(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug(String.format("Processing %s", pjp.getSignature()));
		
		try {
			
			return pjp.proceed();
			
		} catch (Exception e) {
			logger.error("Unable to process method", e);
			
			logger.debug("Persisting error to MongoDB");
			StringBuilder arguments = new StringBuilder();
			for (Object arg: pjp.getArgs()) {
				arguments.append(arg);
			}
			
			errorLogRepository.save(new ErrorLog(ErrorUtil.getErrorType(e), 
					e.getMessage(), 
					new Date(), 
					pjp.getSignature().toLongString(), 
					arguments.toString()));
			
			return pjp.proceed();
		}
	}
	
	/**
	 * When a controller method encounters an exception by default the exception is 
	 * propagated to the browser. It's an ugly sight, specially when dealing with AJAX.
	 * To avoid this side-effect, we catch all controller exception and return an Event
	 * containing a false value
	 */
	@Around("execution(* ningbo.media.controller.EventController.*(..))")
	public Object interceptController(ProceedingJoinPoint pjp) throws Throwable {
		try {
			return pjp.proceed();
		} catch (Exception e) {
			return new ResponseDto<Event>(false);
		}
	}
}
