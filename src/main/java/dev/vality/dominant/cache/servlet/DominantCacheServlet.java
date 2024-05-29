package dev.vality.dominant.cache.servlet;

import dev.vality.damsel.dominant.cache.DominantCacheSrv;
import dev.vality.woody.thrift.impl.http.THServiceBuilder;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/v1/dominant/cache")
public class DominantCacheServlet extends GenericServlet {

    private Servlet thriftServlet;

    @Autowired
    private DominantCacheSrv.Iface requestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        thriftServlet = new THServiceBuilder()
                .build(DominantCacheSrv.Iface.class, requestHandler);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        thriftServlet.service(req, res);
    }

}
