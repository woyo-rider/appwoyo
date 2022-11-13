package com.springbootvaadin.application.views.about;

import com.springbootvaadin.application.views.MainLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.IOException;

@PageTitle("About")
@Route(value = "wallet-card", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView_v2 extends VerticalLayout {

    public AboutView_v2() throws IOException {

        Div div =new Div();

        Anchor anchor = new Anchor("/wallet-card");
        


        add(div);

        setVisible(true);


    }
}
