package ua.com.lsd25.domain;

/**
 * @author vzagnitko
 */
public interface DomainBuildWrapperMarker<T extends WrapperMarker> extends DomainMarker {

    <T> T buildWrapper();

}
