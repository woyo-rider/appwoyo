package com.springbootvaadin.application.views;

import com.springbootvaadin.application.components.FlexBoxLayout;
import com.springbootvaadin.application.components.appnav.AppNav;
import com.springbootvaadin.application.components.appnav.AppNavItem;
import com.springbootvaadin.application.components.navigation.bar.AppBar;
import com.springbootvaadin.application.components.navigation.drawer.NaviDrawer;
import com.springbootvaadin.application.data.Role;
import com.springbootvaadin.application.data.entity.User;
import com.springbootvaadin.application.data.service.CurrentSession;
import com.springbootvaadin.application.data.service.UserInfo;
import com.springbootvaadin.application.data.service.UserRepository;
import com.springbootvaadin.application.security.AuthenticatedUser;
import com.springbootvaadin.application.security.Roles;
import com.springbootvaadin.application.security.vaadin.LogoutUtil;
import com.springbootvaadin.application.views.about.AboutView_v1;
import com.springbootvaadin.application.views.collaborativemasterdetail.CollaborativeMasterDetailView;
import com.springbootvaadin.application.views.creditcardform.CreditCardFormView;
import com.springbootvaadin.application.views.map.MapView;
import com.springbootvaadin.application.views.masterdetail.MasterDetailView;
import com.springbootvaadin.application.views.masterdetail.MasterDetailView_v0;
import com.springbootvaadin.application.views.woyo.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * The main view is a top-level placeholder for other views.
 */

//@JavaScript("https://code.jquery.com/jquery-3.5.1.slim.min.js")
//@JavaScript("https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js")
//@CssImport("./themes/appwoyo/views/bootstrap.min.css")
//@JavaScript("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js")

//    @Theme(value = "com.vaadin.flow.theme.lumo.Lumo.class", variant = Lumo.LIGHT)

public class MainLayout extends AppLayout implements RouterLayout {

    @Value("${keycloak.admin-console-uri}")
    String consoleUri;
    private H1 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    UserRepository userRepository;
    CurrentSession currentSession;
    LogoutUtil logoutUtil;
    private AppBar appBar;
    private NaviDrawer naviDrawer;

    Logger logger = LoggerFactory.getLogger(getClass());


    public MainLayout(CurrentSession currentSession, UserRepository userRepository, LogoutUtil logoutUtil, AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {

        this.currentSession = currentSession;
        this.userRepository = userRepository;
        this.logoutUtil = logoutUtil;

        updateAndSaveRepo(currentSession, userRepository);

        AuthenticatedUser authenticatedUser1 =new AuthenticatedUser(userRepository, currentSession, logoutUtil);

        authenticatedUser = authenticatedUser1;
        // reassigning object at above line

        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        appBar = new AppBar("");

        logger.info("==============in Main Layout - Main Layout()=================");


        setPrimarySection(Section.DRAWER);
        setDrawerOpened(false);
//        setVisible();DrawerOpened();
//        setDrawerOpened();
        if(isDrawerOpened()){
//            setVisible(false);
            this.getElement().setAttribute("overlay", true);
        }

//        this.isOverlay();
//        this.getElement().setAttribute("overlay", true);
//        System.out.println("===========================   overlay "+this.getElement().hasAttribute("overlay"));
//        System.out.println("===========================   tag "+this.getElement().getTag());

        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());

    }

    @Transactional
    public void updateAndSaveRepo(CurrentSession currentSession, UserRepository userRepository){
        if (fetchUser().isPresent()){
            System.out.println(" --------------- username already present -----------");
        }else if (fetchUser().isEmpty()){
            if(currentSession.hasRole(Roles.USER) ){
                System.out.println("========= inside -if(currentSession.hasRole(Roles.USER)) =========================");
                User user = new User();
                user.setFirstName(currentSession.getCurrentUser().map(UserInfo::getFirstName).orElse(""));
                user.setLastName(currentSession.getCurrentUser().map(UserInfo::getLastName).orElse(""));
                user.setUsername(currentSession.getCurrentUser().map(UserInfo::getUsername).orElse(""));
                //            user.setHashedPassword(passwordEncoder.encode("user"));
                user.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
                user.setRoles(Collections.singleton(Role.USER));
                userRepository.save(user);

                System.out.println("====== user == "+ user.getUsername());
                return;
            }


            if(currentSession.hasRole(Roles.ADMIN) ){
                System.out.println("========= inside +if(currentSession.hasRole(Roles.ADMIN)) =========================");

                User admin = new User();
                admin.setFirstName(currentSession.getCurrentUser().map(UserInfo::getFirstName).orElse(""));
                admin.setLastName(currentSession.getCurrentUser().map(UserInfo::getLastName).orElse(""));
                admin.setUsername(currentSession.getCurrentUser().map(UserInfo::getUsername).orElse(""));
                //            admin.setHashedPassword(passwordEncoder.encode("admin"));
                admin.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
                admin.setRoles(Set.of(Role.USER, Role.ADMIN));

                System.out.println("====== admin == "+ admin.getUsername());

                User u = userRepository.save(admin);

                System.out.println("====== admin == "+ u.getUsername());

            }
        }
    }
    public Optional<User> fetchUser(){

        /*** clean duplicate conflict with h2

        userRepository.deleteAll();

         **/

        return Optional.ofNullable(userRepository.findByUsername(currentSession.getCurrentUser().map(UserInfo::getUsername).orElse("")));
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassNames("view-toggle");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");
//        toggle.setVisible(false);

        viewTitle = new H1();
        viewTitle.addClassNames("view-title");

        Image img = new Image("images/WOYO_LOGO_PURPLE_CMYK (1).png", "woyo");
        img.setWidth("100px");

        HorizontalLayout header = new HorizontalLayout(toggle, img, viewTitle, divAccessProfile()); //Header header = new Header(toggle, viewTitle);
        header.addClassNames("view-header");
        header.expand(viewTitle);
//        header.setSpacing(true);
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2();
        Image img = new Image("images/WOYO_LOGO_GREEN_CMYK (1).png", "woyo");
        img.setWidth("100px");
        appName.add(img);
        appName.addClassNames("app-name");

        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
        closeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        closeButton.getElement().setAttribute("aria-label", "Close");
//        appName.add(closeButton);

        HorizontalLayout horiLay =new HorizontalLayout(appName, closeButton);
        horiLay.expand(appName);

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(
                horiLay,
                createNavigation()
        );
        section.addClassNames("drawer-section");


        closeButton.addClickListener(event -> MainLayout.get().setDrawerOpened(false));

/*
        FlexBoxLayout column =new FlexBoxLayout(section);
        column.setFlexGrow(1, section);
        column.setOverflow(Overflow.HIDDEN);

 */

        return section;
    }

    private AppNavItem createNavigation2() {


        AppNavItem nav2 = new AppNavItem("list", ListView.class, "la la-user");
//        nav2.addClassNames("app-nav2");

//        nav2.addItem(new AppNavItem("list", ListView.class, "la la-user"));

        return nav2;
    }

    private Component createNavigation() {
        AppNav nav = new AppNav();
        nav.addClassNames("app-nav");

        Icon money = new Icon(VaadinIcon.MONEY);
        money.addClassNames("drawer-menu-icon");

        if (accessChecker.hasAccess(WoyoView.class)) {
            nav.addItem(new AppNavItem("DashBoard", WoyoView.class, "la la-dashboard"));

        }
        if (accessChecker.hasAccess(CreditWoyoView.class)) {
            nav.addItem(new AppNavItem("Credit Statistics", CreditWoyoView.class, "la la-money"));

        }
        if (accessChecker.hasAccess(MasterDetailView.class)) { // @RolesAllowed("ADMIN")
            nav.addItem(new AppNavItem("PersonDetails", MasterDetailView.class, "la la-user"));

        }
        if (accessChecker.hasAccess(MasterDetailView_v0.class)) {
            nav.addItem(new AppNavItem("User Management", MasterDetailView_v0.class, "la la-file"));

        }
        if (accessChecker.hasAccess(CreditCardFormView.class)) {
            nav.addItem(new AppNavItem("Accounting & Settlement", CreditCardFormView.class, "la la-credit-card"));

        }
        if (accessChecker.hasAccess(CollaborativeMasterDetailView.class)) {
            nav.addItem(new AppNavItem("Statistics", CollaborativeMasterDetailView.class,
                    "la la-columns"));

        }
//        if (accessChecker.hasAccess(SignupView.class)) {
//            nav.addItem(new AppNavItem("Signup", SignupView.class, "la la-user"));
//
//        }
//        if (accessChecker.hasAccess(MainlayView.class)) {
//            nav.addItem(new AppNavItem("Mainlay", MainlayView.class, "la la-globe"));
//
//        }
//        if (accessChecker.hasAccess(PersonFormView.class)) {
//            nav.addItem(new AppNavItem("Menu", AccordionBasic.class, "la la-user"));
//
//        }

        Icon car = VaadinIcon.CAR.create();
        car.addClassNames("drawer-menu-icon");
        Icon map_marker =new Icon(VaadinIcon.MAP_MARKER);
        map_marker.addClassNames("drawer-menu-icon");


        AppNavItem navItem2 = new AppNavItem("Rides");
        if (accessChecker.hasAccess(ListViewOngoingRides.class)) {
            navItem2.addItem(new AppNavItem("Ongoing Rides", ListViewOngoingRides.class, car));
        }
        if (accessChecker.hasAccess(ListViewCompletedRides.class)) {
            navItem2.addItem(new AppNavItem("Completed Rides", ListViewCompletedRides.class, car));
        }
        if (accessChecker.hasAccess(ListViewCancelledRides.class)) {
            navItem2.addItem(new AppNavItem("Cancelled Rides", ListViewCancelledRides.class, car));
        }
        if (accessChecker.hasAccess(ListViewFailedRides.class)) {
            navItem2.addItem(new AppNavItem("Failed Rides", ListViewFailedRides.class, car));
        }
        if (accessChecker.hasAccess(ListViewAutoCancelledRides.class)) {
            navItem2.addItem(new AppNavItem("Auto Cancelled Rides", ListViewAutoCancelledRides.class, map_marker));
        }
        if (accessChecker.hasAccess(ListViewAllRides.class)) {
            navItem2.addItem(new AppNavItem("All Rides", ListViewAllRides.class, car));
        }

        nav.addItem(navItem2);


        AppNavItem navItem3 = new AppNavItem("Driver Management");
//        nav.addItem(createNavigation2());
        navItem3.addItem(new AppNavItem("Vehicles", ListViewVehicles.class, car));


        navItem3.addItem(new AppNavItem("Map", MapView.class, map_marker));

        nav.addItem(navItem3);

        /*
        navItem2.addItem(new AppNavItem("Ongoing Rides", ListViewOngoingRides.class, car));
        navItem2.addItem(new AppNavItem("Completed Rides", ListViewCompletedRides.class, car));
        navItem2.addItem(new AppNavItem("Cancelled Rides", ListViewCancelledRides.class, car));
        navItem2.addItem(new AppNavItem("Failed Rides", ListViewFailedRides.class, car));
        navItem2.addItem(new AppNavItem("Auto Cancelled Rides", ListViewAutoCancelledRides.class, map_marker));
        navItem2.addItem(new AppNavItem("All Rides", ListViewAllRides.class, car));

        nav.addItem(navItem2);


        AppNavItem navItem3 = new AppNavItem("Driver Management");
//        nav.addItem(createNavigation2());
        navItem3.addItem(new AppNavItem("Vehicles", ListViewVehicles.class, car));
         */


//        if (accessChecker.hasAccess(BoardBasic.class)) {
//            nav.addItem(new AppNavItem("Board", BoardBasic.class, "la la-map-marker"));
//
//        }


        if (accessChecker.hasAccess(AboutView_v1.class)) {
            nav.addItem(new AppNavItem("About1", AboutView_v1.class, "la la-file"));
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("app-nav-footer");

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            logger.info("==============in Main Layout - createFooter()=================");
            Avatar avatar = new Avatar(user.getFirstName(), user.getProfilePictureUrl());
            avatar.addClassNames("me-xs");

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.setOpenOnClick(true);
            userMenu.addItem("Logout", e -> {
                authenticatedUser.logout();
            });

            Span name = new Span(user.getFirstName());
            name.addClassNames("font-medium", "text-s", "text-secondary");

            layout.add(avatar, name);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }
    private FlexLayout divAccessProfile(){
        Div div = new Div();
        div.addClassNames("app-nav-profile");

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            logger.info("==============in Main Layout - divAccessProfile()=================");
            Avatar avatar = new Avatar(user.getFirstName(), user.getProfilePictureUrl());
            avatar.addClassNames("me-xs");

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.addClassName("avatarMenu");
            userMenu.setOpenOnClick(true);

            if (false){ // not recommended  (accessChecker.hasAccess(MasterDetailView.class)) {
                userMenu.addItem("Admin Console",
                        e->{
                    if(UI.getCurrent().getUI().isPresent())
                    {
                        UI.getCurrent().getPage().setLocation(URI.create(consoleUri));
                    }
                }
                );
            }
            userMenu.addItem("Logout", e -> {
                authenticatedUser.logout();
            });

            Span name = new Span(user.getFirstName());
//            Div name = new Div();
//            name.add(user.getFirstName());
            name.addClassNames("font-medium", "text-l", "text-secondary",
                    LumoUtility.Padding.Horizontal.XSMALL,
                    LumoUtility.FlexDirection.COLUMN
            );

            div.add(avatar, name);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            div.add(loginLink);
        }

        FlexLayout layout = new FlexBoxLayout(div);
        layout.addClassName("logoutButton");
        layout.setAlignContent(FlexLayout.ContentAlignment.STRETCH);

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    public static MainLayout get() {
        return (MainLayout) UI.getCurrent().getChildren()
                .filter(component -> component.getClass() == MainLayout.class)
                .findFirst().get();
    }

    public AppBar getAppBar() {
        return appBar;
    }
    public NaviDrawer getNaviDrawer() {
        return naviDrawer;
    }

}
