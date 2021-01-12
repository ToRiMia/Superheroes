//package torimia.superheroes.arena.annotations;
//
//
//import org.springframework.beans.PropertyEditorRegistry;
//import org.springframework.core.MethodParameter;
//import org.springframework.validation.*;
//import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import java.beans.PropertyEditor;
//import java.lang.reflect.Method;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//public class CollectionValidation implements ConstraintValidator<NotContainsRepeatableElement, Collection> {
//
//    @Override
//    public boolean isValid(Collection collection, ConstraintValidatorContext context) {
//
//        BeanPropertyBindingResult result = new BeanPropertyBindingResult(collection, "collection");
//        SpringValidatorAdapter adapter = new SpringValidatorAdapter(context.);
//        adapter.validate(collection, result);
//
//        if (collection.stream().distinct().count() < collection.size()) {
//            throw new MethodArgumentNotValidException(null, new BindingResult() {
//                @Override
//                public String getObjectName() {
//                    return null;
//                }
//
//                @Override
//                public void setNestedPath(String s) {
//
//                }
//
//                @Override
//                public String getNestedPath() {
//                    return null;
//                }
//
//                @Override
//                public void pushNestedPath(String s) {
//
//                }
//
//                @Override
//                public void popNestedPath() throws IllegalStateException {
//
//                }
//
//                @Override
//                public void reject(String s) {
//
//                }
//
//                @Override
//                public void reject(String s, String s1) {
//
//                }
//
//                @Override
//                public void reject(String s, Object[] objects, String s1) {
//
//                }
//
//                @Override
//                public void rejectValue(String s, String s1) {
//
//                }
//
//                @Override
//                public void rejectValue(String s, String s1, String s2) {
//
//                }
//
//                @Override
//                public void rejectValue(String s, String s1, Object[] objects, String s2) {
//
//                }
//
//                @Override
//                public void addAllErrors(Errors errors) {
//
//                }
//
//                @Override
//                public boolean hasErrors() {
//                    return false;
//                }
//
//                @Override
//                public int getErrorCount() {
//                    return 0;
//                }
//
//                @Override
//                public List<ObjectError> getAllErrors() {
//                    return null;
//                }
//
//                @Override
//                public boolean hasGlobalErrors() {
//                    return false;
//                }
//
//                @Override
//                public int getGlobalErrorCount() {
//                    return 0;
//                }
//
//                @Override
//                public List<ObjectError> getGlobalErrors() {
//                    return null;
//                }
//
//                @Override
//                public ObjectError getGlobalError() {
//                    return null;
//                }
//
//                @Override
//                public boolean hasFieldErrors() {
//                    return false;
//                }
//
//                @Override
//                public int getFieldErrorCount() {
//                    return 0;
//                }
//
//                @Override
//                public List<FieldError> getFieldErrors() {
//                    return null;
//                }
//
//                @Override
//                public FieldError getFieldError() {
//                    return null;
//                }
//
//                @Override
//                public boolean hasFieldErrors(String s) {
//                    return false;
//                }
//
//                @Override
//                public int getFieldErrorCount(String s) {
//                    return 0;
//                }
//
//                @Override
//                public List<FieldError> getFieldErrors(String s) {
//                    return null;
//                }
//
//                @Override
//                public FieldError getFieldError(String s) {
//                    return null;
//                }
//
//                @Override
//                public Object getFieldValue(String s) {
//                    return null;
//                }
//
//                @Override
//                public Class<?> getFieldType(String s) {
//                    return null;
//                }
//
//                @Override
//                public Object getTarget() {
//                    return null;
//                }
//
//                @Override
//                public Map<String, Object> getModel() {
//                    return null;
//                }
//
//                @Override
//                public Object getRawFieldValue(String s) {
//                    return null;
//                }
//
//                @Override
//                public PropertyEditor findEditor(String s, Class<?> aClass) {
//                    return null;
//                }
//
//                @Override
//                public PropertyEditorRegistry getPropertyEditorRegistry() {
//                    return null;
//                }
//
//                @Override
//                public String[] resolveMessageCodes(String s) {
//                    return new String[0];
//                }
//
//                @Override
//                public String[] resolveMessageCodes(String s, String s1) {
//                    return new String[0];
//                }
//
//                @Override
//                public void addError(ObjectError objectError) {
//
//                }
//            });
//            return false;
//        }
//
////        for (Object item : collection) {
////            if (collection.containsKey(str)) {
////                counts.put(str, counts.get(str) + 1);
////            } else {
////                counts.put(str, 1);
////            }
////        }
//        return true;
//    }
//}
