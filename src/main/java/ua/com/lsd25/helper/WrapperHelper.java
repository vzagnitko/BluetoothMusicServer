package ua.com.lsd25.helper;

import ua.com.lsd25.domain.DomainBuildWrapper;
import ua.com.lsd25.domain.Wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vzagnitko
 */
public class WrapperHelper {

    public static <K extends Wrapper, T extends DomainBuildWrapper> List<K> createWrapperList(List<T> list) {
        List<K> wrappers = new ArrayList<>();
        for (T el : list) {
            wrappers.add((K) el.buildWrapper());
        }
        return wrappers;
    }

}
