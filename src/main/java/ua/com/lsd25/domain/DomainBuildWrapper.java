package ua.com.lsd25.domain;

/**
 * @author vzagnitko
 */
public interface DomainBuildWrapper<T extends Wrapper> extends Domain {

    <T> T buildWrapper();

}