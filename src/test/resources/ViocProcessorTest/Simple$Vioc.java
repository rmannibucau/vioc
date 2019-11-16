import com.github.rmannibucau.vioc.internal.Bean;

public class Simple$Vioc implements Bean<Simple> {
    @Override
    public T create(final Container container) {
        return null;
    }

    @Override
    public void destroy(final T instance) {
        // no-op
    }
}