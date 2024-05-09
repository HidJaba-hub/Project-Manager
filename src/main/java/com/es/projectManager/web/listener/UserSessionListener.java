package com.es.projectManager.web.listener;

import com.es.projectManager.model.User;
import com.es.projectManager.util.SyncObjectPool;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class UserSessionListener implements HttpSessionListener {

    private static final String USER_SESSION_ATTRIBUTE = "user";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSessionListener.super.sessionCreated(sessionEvent);
        HttpSession session = sessionEvent.getSession();

        User user = new User();
        session.setAttribute(USER_SESSION_ATTRIBUTE, user);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        String sessionId = sessionEvent.getSession().getId();
        SyncObjectPool.cleanPool(sessionId);
        HttpSessionListener.super.sessionDestroyed(sessionEvent);
    }

}
