import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class CustomServerAppConfigProvider implements ServerApplicationConfig {
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
        Set<ServerEndpointConfig> result = new HashSet<>();
        for (Class epClass : endpointClasses) {
            //need to ignore Client endpoint class
            if (epClass.equals(EndpointMensajeGrupal.class)) {
                ServerEndpointConfig sec = ServerEndpointConfig.Builder.create(EndpointMensajeGrupal.class, "/mensaje-grupal").build();
                result.add(sec);
            }
        }
        return result;
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
        return Collections.emptySet();
    }
}
