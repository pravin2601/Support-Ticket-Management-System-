package com.example.supportticket.exception;
import com.example.supportticket.dto.ApiError; import jakarta.servlet.http.HttpServletRequest; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import org.springframework.http.converter.HttpMessageNotReadableException; import java.time.Instant; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
 @ExceptionHandler(NotFoundException.class) ApiError notFound(NotFoundException e,HttpServletRequest r){return err(HttpStatus.NOT_FOUND,e.getMessage(),r,Map.of());}
 @ExceptionHandler(InvalidStatusTransitionException.class) ApiError conflict(InvalidStatusTransitionException e,HttpServletRequest r){return err(HttpStatus.CONFLICT,e.getMessage(),r,Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ApiError validation(MethodArgumentNotValidException e,HttpServletRequest r){Map<String,String> m=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->m.putIfAbsent(x.getField(),x.getDefaultMessage()));return err(HttpStatus.BAD_REQUEST,"Request validation failed",r,m);}
 @ExceptionHandler(HttpMessageNotReadableException.class) ApiError malformed(HttpMessageNotReadableException e,HttpServletRequest r){return err(HttpStatus.BAD_REQUEST,"Malformed request or invalid enum value",r,Map.of());}
 private ApiError err(HttpStatus s,String msg,HttpServletRequest r,Map<String,String> f){return new ApiError(Instant.now(),s.value(),s.getReasonPhrase(),msg,r.getRequestURI(),f);}
}
