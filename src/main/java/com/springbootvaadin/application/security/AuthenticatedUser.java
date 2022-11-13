package com.springbootvaadin.application.security;

import com.springbootvaadin.application.data.entity.User;
import com.springbootvaadin.application.data.service.CurrentSession;
import com.springbootvaadin.application.data.service.UserRepository;
import com.springbootvaadin.application.security.vaadin.LogoutUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    CurrentSession currentSession;
    LogoutUtil logoutUtil;

    @Autowired
    public AuthenticatedUser(UserRepository userRepository, CurrentSession currentSession, LogoutUtil logoutUtil) {
        this.userRepository = userRepository;
        this.currentSession = currentSession;
        this.logoutUtil = logoutUtil;
    }

    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    public Optional<User> get() {
        System.out.println("======== AU get() authentication.getName() ====== " + currentSession.currentUser().getUsername() + " ===== " + getAuthentication().filter(authentication -> Boolean.parseBoolean(authentication.getPrincipal().toString())));
//        return Optional.ofNullable(userRepository.findByUsername(currentSession.currentUser().getUsername()));
//        return Optional.ofNullable(userRepository.findByUsername(currentSession.getCurrentUser().map(UserInfo::getUsername).orElse("")));

//        return getAuthentication().map(
//                authentication -> userRepository.findByUsername(
//                        ((DefaultOAuth2User) authentication.getPrincipal()).getAttribute("preferred_username")
//                )
//        );
        //(currentSession.currentUser().getUsername()));

//        System.out.println("++++++++++++++++++   "+ getU().getUsername());

//        return getAuthentication().map(authentication -> userRepository.findByUsername(authentication.getName()));
        return getAuthentication().map(authentication -> userRepository.findByUsername(
        (
                ((DefaultOidcUser) authentication.getPrincipal())
                        .getClaims().get("preferred_username")
        ).toString()
        ));
    }


    Authentication getAuth(){
        SecurityContext context = SecurityContextHolder.getContext();
        if(!(context.getAuthentication() instanceof AnonymousAuthenticationToken)){
            return context.getAuthentication();
        }else{  return null; }
    }

    User getU(){
        Authentication authOb= getAuth();

        System.out.println("!!!!!!!!!!!!!!!!  "+(
                ((DefaultOidcUser) authOb.getPrincipal())
                .getClaims().get("preferred_username")
                ).toString()
        );

//        return userRepository.findByUsername(authOb.getName());
        return userRepository.findByUsername(
                (
                        ((DefaultOidcUser) authOb.getPrincipal())
                                .getClaims().get("preferred_username")
                ).toString()
        );
    }


    public void logout() {
        logoutUtil.logout();
//        UI.getCurrent().getPage().setLocation("/logout");
//        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
//        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
