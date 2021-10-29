package com.rbkmoney.dominant.cache.servlet;

import com.rbkmoney.damsel.dominant.cache.DominantCacheSrv;
import com.rbkmoney.woody.thrift.impl.http.THServiceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

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
