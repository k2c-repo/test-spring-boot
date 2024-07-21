package com.ryon.helloboot;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {
    public static void run(Class<?> applicationClass, String... args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

//				ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
//				dispatcherServlet.setApplicationContext(this);

                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*");
                });

//				WebServer webServer = serverFactory.getWebServer(servletContext -> {
//					servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this)).addMapping("/*");
//				});

                webServer.start();
            }
        };

//		applicationContext.registerBean(HelloController.class);
//		applicationContext.registerBean(SimpleHelloService.class);
//		applicationContext.register(HellobootApplication.class);
        applicationContext.register(applicationClass);
        applicationContext.refresh();


//		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//		WebServer webServer = serverFactory.getWebServer(servletContext -> {
//			servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext)).addMapping("/*");
//		});
//
//		webServer.start();

//		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//		WebServer webServer = serverFactory.getWebServer(servletContext -> {
//			servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext)).addMapping("/*");
//			servletContext.addServlet("frontController", new HttpServlet() {
//				@Override
//				protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//					// 인증, 보안, 다국어, 공통 기능
//					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
//						String name = req.getParameter("name");
//
//						HelloController helloController = applicationContext.getBean(HelloController.class);
//						String ret = helloController.hello(name);
//
//						resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
//						resp.getWriter().println(ret);
//					} else {
//						resp.setStatus(HttpStatus.NOT_FOUND.value());
//					}
//				}
//			}).addMapping("/*");
//		});
//		webServer.start();
//        ServletContextInitializer
    }
}
