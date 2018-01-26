public class SimpleTypeConvert {
    public static <T> T convert(String textAsValue, Class<T> targetClass) {
        if (String.class.isAssignableFrom(targetClass)) {
            return (T) textAsValue;
        } else if (Date.class.isAssignableFrom(targetClass)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return (T) simpleDateFormat.parse(textAsValue);
            } catch (ParseException e) {
                throw new IllegalArgumentException("convert date error" + e.getMessage());
            }
        }else if(java.sql.Date.class.isAssignableFrom(targetClass)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date= simpleDateFormat.parse(textAsValue);
                return (T) new java.sql.Date(date.getTime());
            } catch (ParseException e) {
                throw new IllegalArgumentException("convert date error" + e.getMessage());
            }
        }else if (Number.class.isAssignableFrom(targetClass)) {
            return (T) NumberUtils.parseNumber(textAsValue, (Class) targetClass);
        } else {
            throw new IllegalArgumentException("Cannot convert String [" + textAsValue + "] to target class [" + targetClass.getName() + "]");
        }
    }
}
