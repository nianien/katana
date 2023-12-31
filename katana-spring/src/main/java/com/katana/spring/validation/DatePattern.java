package com.katana.spring.validation;

import com.cudrania.core.date.DateFormatter;
import com.cudrania.core.utils.StringUtils;
import com.katana.spring.validation.DatePattern.DatePatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Date;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验日期格式
 *
 * @author skyfalling
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DatePatternValidator.class)
public @interface DatePattern {
    String message() default "{com.cudrania.side.validation.DatePattern.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 允许的日期格式
     *
     * @return
     */
    String[] value();

    /**
     * 不进行格式校验的例外情况
     *
     * @return
     */
    String[] except() default {};

    /**
     * 是否允许为空,如果为空,则不进行校验
     *
     * @return
     */
    boolean allowEmpty() default false;


    class DatePatternValidator implements ConstraintValidator<DatePattern, String> {

        private DatePattern constraint;

        @Override
        public void initialize(DatePattern constraintAnnotation) {
            this.constraint = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            //是否为空
            if (StringUtils.isEmpty(value)) {
                return constraint.allowEmpty();
            }
            //是否为例外
            for (String except : constraint.except()) {
                if (except.equals(value))
                    return true;
            }
            try {
                //双重校验
                Date date = DateFormatter.parseDate(value, constraint.value());
                for (String pattern : constraint.value()) {
                    if (DateFormatter.format(date, pattern).equals(value))
                        return true;
                }
            } catch (Exception e) {
                //ignore
                e.printStackTrace();
            }
            return false;
        }
    }
}


