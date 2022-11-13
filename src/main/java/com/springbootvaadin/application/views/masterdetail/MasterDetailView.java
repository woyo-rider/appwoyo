package com.springbootvaadin.application.views.masterdetail;

import com.springbootvaadin.application.data.*;
import com.springbootvaadin.application.util.UIUtils;
import com.springbootvaadin.application.util.css.BorderRadius;
import com.springbootvaadin.application.util.css.Shadow;
import com.springbootvaadin.application.views.MainLayout;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Person Details")
@Route(value = "master-detail/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class MasterDetailView extends Div {

    private static final String PAYMENT = "Payment";
    private static final String BILLING_ADDRESS = "Billing address";
    private static final String CUSTOMER_DETAILS = "Customer details";

   /* public MasterDetailView() {
        Accordion accordion = new Accordion();

        Binder<Person> personBinder = new Binder<>(Person.class);
        personBinder.setBean(new Person());

        Binder<Card> cardBinder = new Binder<>(Card.class);
        cardBinder.setBean(new Card());

        FormLayout customerDetailsFormLayout = createFormLayout();
        // tag::snippet[]
        AccordionPanel customDetailsPanel = accordion.add(CUSTOMER_DETAILS,  customerDetailsFormLayout);
        // end::snippet[]


        FormLayout billingAddressFormLayout = createFormLayout();
        AccordionPanel billingAddressPanel = accordion.add(BILLING_ADDRESS,  billingAddressFormLayout);

        FormLayout paymentFormLayout = createFormLayout();
        AccordionPanel paymentPanel = accordion.add(PAYMENT,  paymentFormLayout);

        // Customer details fields

        TextField firstName = new TextField("First name");
        personBinder.forField(firstName).bind("firstName");

        TextField lastName = new TextField("Last name");
        personBinder.forField(lastName).bind("lastName");

        EmailField email = new EmailField("Email address");
        personBinder.forField(email).bind("email");

        TextField phone = new TextField("Phone number");
        personBinder.forField(phone).bind(person -> {
            if  (person.getAddress() != null) {
                return person.getAddress().getPhone();
            }
            return "";
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setPhone(value);
        });

        customerDetailsFormLayout.add(firstName, lastName);
        customerDetailsFormLayout.add(email, 2);
        customerDetailsFormLayout.add(phone, 2);

        // tag::snippet[]
        customDetailsPanel.addOpenedChangeListener(e -> {
            if(e.isOpened()) {
                customDetailsPanel.setSummaryText(CUSTOMER_DETAILS);
            } else if(personBinder.getBean() != null) {
                Person personValues = personBinder.getBean();
                customDetailsPanel.setSummary(createSummary(CUSTOMER_DETAILS,
                        personValues.getFirstName() + " " + personValues.getLastName(),
                        personValues.getEmail(),
                        personValues.getAddress() != null ? personValues.getAddress().getPhone() : ""
                ));
            }
        });
        // end::snippet[]

        Button customDetailsButton = new Button("Continue", (e) -> billingAddressPanel.setOpened(true));
        customDetailsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        customDetailsPanel.addContent(customDetailsButton);

        // Billing address fields

        TextField address = new TextField("Address");
        personBinder.forField(address).bind(person -> {
            if  (person.getAddress() != null) {
                return person.getAddress().getStreet();
            }
            return "";
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setStreet(value);
        });

        TextField zipCode = new TextField("ZIP code");
        personBinder.forField(zipCode).bind(person -> {
            if  (person.getAddress() != null) {
                return person.getAddress().getZip();
            }
            return "";
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setZip(value);
        });

        TextField city = new TextField("City");
        personBinder.forField(city).bind(person -> {
            if  (person.getAddress() != null) {
                return person.getAddress().getCity();
            }
            return "";
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setCity(value);
        });

        ComboBox<Country> countries = new ComboBox<>("Country");
        countries.setItems(DataService.getCountries());
        countries.setItemLabelGenerator(Country::getName);
        personBinder.forField(countries).bind(person -> {
            if  (person.getAddress() != null) {
                Country country = new Country();
                country.setName(person.getAddress().getCountry());
                return country;
            }
            return null;
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setCountry(value.getName());
        });

        billingAddressFormLayout.add(address, 2);
        billingAddressFormLayout.add(zipCode, city, countries);

        billingAddressPanel.addOpenedChangeListener(e -> {
            if(e.isOpened()) {
                billingAddressPanel.setSummaryText(BILLING_ADDRESS);
            } else if(personBinder.getBean().getAddress() != null) {
                Address addressValues = personBinder.getBean().getAddress();
                billingAddressPanel.setSummary(createSummary(BILLING_ADDRESS,
                        addressValues.getStreet(),
                        addressValues.getZip() + " " + addressValues.getCity(),
                        addressValues.getCountry()
                ));
            }
        });

        Button billingAddressButton = new Button("Continue", (e) -> paymentPanel.setOpened(true));
        billingAddressButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        billingAddressPanel.addContent(billingAddressButton);

        // Payment fields
        TextField accountNumber = new TextField("Card number");
        cardBinder.forField(accountNumber).bind("accountNumber");

        TextField expiryDate = new TextField("Expiry date");
        cardBinder.forField(expiryDate).bind("expiryDate");

        TextField cvv = new TextField("CVV");
        cardBinder.forField(cvv).bind("cvv");

        paymentFormLayout.add(accountNumber, 2);
        paymentFormLayout.add(expiryDate, cvv);

        paymentPanel.addOpenedChangeListener(e -> {
            if(e.isOpened()) {
                paymentPanel.setSummaryText(PAYMENT);
            } else if(cardBinder.getBean() != null) {
                Card cardValues = cardBinder.getBean();
                paymentPanel.setSummary(createSummary(PAYMENT,
                        cardValues.getAccountNumber(),
                        cardValues.getExpiryDate()
                ));
            }
        });

        Button paymentButton = new Button("Finish", (e) -> paymentPanel.setOpened(false));
        paymentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        paymentPanel.addContent(paymentButton);

//        add(accordion);



        Row subContent = new Row(accordion);
        subContent.addClassName(LumoStyles.Margin.Top.L);
//        UIUtils.setMaxWidth(MAX_WIDTH,subContent);
        subContent.setWidthFull();

        Div card = new Div(accordion);
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
//        UIUtils.setBoxSizing(BoxSizing.BORDER_BOX);
        UIUtils.setBorderRadius(BorderRadius.M, card);
//        card.addClassName("rounded_border");
        UIUtils.setShadow(Shadow.XS, card);
        card.addClassName(LumoUtility.Padding.LARGE);
        card.setWidth("90%");


        FlexBoxLayout box =new FlexBoxLayout(card);
        box.setAlignItems(FlexComponent.Alignment.CENTER);
        box.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        box.setPadding(Uniform.M);
        box.setBoxSizing(BoxSizing.BORDER_BOX);
        box.setBorderRadius(BorderRadius.L);
        box.setWidth("900px");


//        add(box);
//        add(subContent);
        setViewContent(box);


    }*/
    public MasterDetailView() {
        addClassNames("master-detail-view");
        Accordion accordion = new Accordion();

        Binder<Person> personBinder = new Binder<>(Person.class);
        personBinder.setBean(new Person());

        Binder<Card> cardBinder = new Binder<>(Card.class);
        cardBinder.setBean(new Card());

        FormLayout customerDetailsFormLayout = createFormLayout();
        AccordionPanel customDetailsPanel = accordion.add(CUSTOMER_DETAILS,  customerDetailsFormLayout);
        FormLayout billingAddressFormLayout = createFormLayout();
        AccordionPanel billingAddressPanel = accordion.add(BILLING_ADDRESS,  billingAddressFormLayout);
        FormLayout paymentFormLayout = createFormLayout();
        AccordionPanel paymentPanel = accordion.add(PAYMENT,  paymentFormLayout);

        UIUtils.setBorderRadius(BorderRadius.M, customDetailsPanel);
        UIUtils.setShadow(Shadow.XS, customDetailsPanel);

        UIUtils.setBorderRadius(BorderRadius.M, billingAddressPanel);
        UIUtils.setShadow(Shadow.XS, billingAddressPanel);

        UIUtils.setBorderRadius(BorderRadius.M, paymentPanel);
        UIUtils.setShadow(Shadow.XS, paymentPanel);

        // Customer details fields
        TextField firstName = new TextField("First name");
        personBinder.forField(firstName).bind("firstName");

        TextField lastName = new TextField("Last name");
        personBinder.forField(lastName).bind("lastName");

        EmailField email = new EmailField("Email address");
        personBinder.forField(email).bind("email");

        TextField phone = new TextField("Phone number");
        personBinder.forField(phone).bind(person -> {
            if  (person.getAddress() != null) {
                return person.getAddress().getPhone();
            }
            return "";
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setPhone(value);
        });

        customerDetailsFormLayout.add(firstName, lastName);
        customerDetailsFormLayout.add(email, 2);
        customerDetailsFormLayout.add(phone, 2);

        customDetailsPanel.addOpenedChangeListener(e -> {
            if(e.isOpened()) {
                customDetailsPanel.setSummaryText(CUSTOMER_DETAILS);
            } else if(personBinder.getBean() != null) {
                Person personValues = personBinder.getBean();
                customDetailsPanel.setSummary(createSummary(CUSTOMER_DETAILS,
                        personValues.getFirstName() + " " + personValues.getLastName(),
                        personValues.getEmail(),
                        personValues.getAddress() != null ? personValues.getAddress().getPhone() : ""
                ));
            }
        });


        Button customDetailsButton = new Button("Continue", (e) -> billingAddressPanel.setOpened(true));
        customDetailsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        customDetailsPanel.addContent(customDetailsButton);

        // Billing address fields

        TextField address = new TextField("Address");
        personBinder.forField(address).bind(person -> {
            if  (person.getAddress() != null) {
                return person.getAddress().getStreet();
            }
            return "";
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setStreet(value);
        });

        TextField zipCode = new TextField("ZIP code");
        personBinder.forField(zipCode).bind(person -> {
            if  (person.getAddress() != null) {
                return person.getAddress().getZip();
            }
            return "";
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setZip(value);
        });

        TextField city = new TextField("City");
        personBinder.forField(city).bind(person -> {
            if  (person.getAddress() != null) {
                return person.getAddress().getCity();
            }
            return "";
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setCity(value);
        });

        ComboBox<Country> countries = new ComboBox<>("Country");
        countries.setItems(DataService.getCountries());
        countries.setItemLabelGenerator(Country::getName);
        personBinder.forField(countries).bind(person -> {
            if  (person.getAddress() != null) {
                Country country = new Country();
                country.setName(person.getAddress().getCountry());
                return country;
            }
            return null;
        }, (person, value) -> {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setCountry(value.getName());
        });

        billingAddressFormLayout.add(address, 2);
        billingAddressFormLayout.add(zipCode, city, countries);

        billingAddressPanel.addOpenedChangeListener(e -> {
            if(e.isOpened()) {
                billingAddressPanel.setSummaryText(BILLING_ADDRESS);
            } else if(personBinder.getBean().getAddress() != null) {
                Address addressValues = personBinder.getBean().getAddress();
                billingAddressPanel.setSummary(createSummary(BILLING_ADDRESS,
                        addressValues.getStreet(),
                        addressValues.getZip() + " " + addressValues.getCity(),
                        addressValues.getCountry()
                ));
            }
        });

        Button billingAddressButton = new Button("Continue", (e) -> paymentPanel.setOpened(true));
        billingAddressButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        billingAddressPanel.addContent(billingAddressButton);

        // Payment fields
        TextField accountNumber = new TextField("Card number");
        cardBinder.forField(accountNumber).bind("accountNumber");

        TextField expiryDate = new TextField("Expiry date");
        cardBinder.forField(expiryDate).bind("expiryDate");

        TextField cvv = new TextField("CVV");
        cardBinder.forField(cvv).bind("cvv");

        paymentFormLayout.add(accountNumber, 2);
        paymentFormLayout.add(expiryDate, cvv);

        paymentPanel.addOpenedChangeListener(e -> {
            if(e.isOpened()) {
                paymentPanel.setSummaryText(PAYMENT);
            } else if(cardBinder.getBean() != null) {
                Card cardValues = cardBinder.getBean();
                paymentPanel.setSummary(createSummary(PAYMENT,
                        cardValues.getAccountNumber(),
                        cardValues.getExpiryDate()
                ));
            }
        });

        Button paymentButton = new Button("Finish", (e) -> paymentPanel.setOpened(false));
        paymentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        paymentPanel.addContent(paymentButton);


        H2 heading= new H2("Personal Details");
        heading.addClassNames("flex", "items-start", "justify-center");

        add(heading);
        add(accordion);
    }

    private FormLayout createFormLayout() {
        FormLayout billingAddressFormLayout = new FormLayout();
        billingAddressFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("20em", 2)
        );
        return billingAddressFormLayout;
    }

    private VerticalLayout createSummary(String title, String... details) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setPadding(false);

//        H3 h3Title = new H3(title);
        layout.add(title);

        if (details.length > 0) {
            VerticalLayout detailsLayout = new VerticalLayout();
            detailsLayout.setSpacing(false);
            detailsLayout.setPadding(false);
            detailsLayout.getStyle().set("font-size", "var(--lumo-font-size-s)");

            for (String detail: details) {
                if (detail != null && !detail.isEmpty()) {
                    detailsLayout.add(new Span(detail));
                }
            }

            layout.add(detailsLayout);
        }

        return layout;
    }

}