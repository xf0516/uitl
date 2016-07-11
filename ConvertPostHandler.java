public interface ConvertPostHandler<O,T> {
    void postProcess(O o,T t);
}
